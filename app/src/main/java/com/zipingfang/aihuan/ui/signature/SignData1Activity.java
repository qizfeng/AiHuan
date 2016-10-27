package com.zipingfang.aihuan.ui.signature;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.asynthttp.AsyncHttpClient;
import com.zipingfang.aihuan.asynthttp.AsyncHttpResponseHandler;
import com.zipingfang.aihuan.asynthttp.RequestParams;
import com.zipingfang.aihuan.bean.Sign;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.dao.UserInfoServerDao;
import com.zipingfang.aihuan.ui.user.TakePhotoActivity;
import com.zipingfang.aihuan.ui.user.UserInfoActivity;
import com.zipingfang.aihuan.utils.ImageLoaderConfig;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.ValidateUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by zipingfang on 16/10/24.
 */

public class SignData1Activity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_card_just, rl_card_back;
    private EditText et_com_name;
    private EditText et_real_name;
    private EditText et_card_no;
    private ImageView iv_card_just;
    private ImageView iv_card_back;
    private TextView tv_title_right;
    private TextView tv_title;
    private String type;
    private String comName;
    private String realName;
    private String idCardNo;
    private String card_front_img;
    private String card_back_img;
    private int imgFlag = 1;//1正面照,2反面照
    private AsyncHttpClient httpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_sign_data);
        type = getIntent().getStringExtra("type");
        initView();
        initActionBar();
    }

    private void initView() {
        et_com_name = (EditText) findViewById(R.id.et_com_name);
        et_real_name = (EditText) findViewById(R.id.et_real_name);
        et_card_no = (EditText) findViewById(R.id.et_card_no);
        iv_card_just = (ImageView) findViewById(R.id.iv_idCardJust);
        iv_card_back = (ImageView) findViewById(R.id.iv_idCardBack);
        rl_card_back = (RelativeLayout) findViewById(R.id.rl_card_back);
        rl_card_just = (RelativeLayout) findViewById(R.id.rl_card_just);
        rl_card_back.setOnClickListener(this);
        rl_card_just.setOnClickListener(this);
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        if ("1".equals(type))
            tv_title.setText("个人申请签约");
        else if ("2".equals(type))
            tv_title.setText("企业申请签约");
        TextView tv_title_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("下一步");
        btn_back.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title_right:

                comName = et_com_name.getText().toString().trim();
                realName = et_real_name.getText().toString().trim();
                idCardNo = et_card_no.getText().toString().trim();

                if (StringUtils.isEmpty(comName)) {
                    ToastUtils.show(SignData1Activity.this, "请输入商户名");
                    return;
                }
                if (StringUtils.isEmpty(idCardNo)) {
                    ToastUtils.show(SignData1Activity.this, "请输入身份证号");
                    return;
                }
                if (!ValidateUtil.isCardNo(idCardNo)) {
                    ToastUtils.show(SignData1Activity.this, "请输入正确的身份证号");
                    return;
                }

                if ("1".equals(type)) {
                    if (StringUtils.isEmpty(realName)) {
                        ToastUtils.show(SignData1Activity.this, "请输入真实姓名");
                        return;
                    }
                } else if ("2".equals(type)) {
                    if (StringUtils.isEmpty(realName)) {
                        ToastUtils.show(SignData1Activity.this, "请输入企业授权人姓名");
                        return;
                    }
                }

                Intent intent = new Intent(SignData1Activity.this, SignData2Activity.class);
                intent.putExtra("type", type);
                intent.putExtra("front", card_front_img);
                intent.putExtra("back", card_back_img);
                intent.putExtra("comName", comName);
                intent.putExtra("realName", realName);
                intent.putExtra("idcard_no",idCardNo);
                startActivity(intent);
                finish();
                break;
            case R.id.rl_card_just:
                imgFlag = 1;
                TakePhotoActivity.startActivityForSinglePhoto(this, 1, true);
                break;
            case R.id.rl_card_back:
                imgFlag = 2;
                TakePhotoActivity.startActivityForSinglePhoto(this, 1, true);
                break;
        }

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        // 打开相机或相册获得的图片路径
        String mTempPhotoUrl = TakePhotoActivity.getDataPath(arg2);
        // 判断是否是文件路径
        if (!StringUtils.isEmpty(mTempPhotoUrl)) {
            Log.e("qizfeng", "takephoto:" + mTempPhotoUrl);
            upfile(mTempPhotoUrl);
        } else {

        }
    }


    /**
     * 上传图片文件
     *
     * @param fileUrl
     */
    public void upfile(String fileUrl) {
        RequestParams params = new RequestParams();
//        params.put("mid", getUserId(this));
        File file = new File(fileUrl);
        if (file.isFile()) {
            try {
                params.put("img", file);
                Log.e("qizfeng", "upload:" + Constants.URL_UPLOAD_FILE + params.toString());
                httpClient.post(Constants.URL_UPLOAD_FILE,
                        params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String json = new String(responseBody);
                                Log.e("qizfeng", "upfile success:" + json);
                                try {
                                    JSONObject object = new JSONObject(json);
                                    JSONObject data = object.optJSONObject("data");
                                    String url = data.opt("url") + "";
                                    if (imgFlag == 1) {
                                        card_front_img = url;
                                        ImageLoader.getInstance().displayImage(Constants.BASE_URL + url, iv_card_just, ImageLoaderConfig.normal);
                                    } else if (imgFlag == 2) {
                                        card_back_img=url;
                                        ImageLoader.getInstance().displayImage(Constants.BASE_URL + url, iv_card_back, ImageLoaderConfig.normal);
                                    }
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.e("qizfeng", "upfile failure:" + new String(responseBody));
                            }
                        });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // 不是文件
                ToastUtils.show(SignData1Activity.this, "请选择一个正确的文件");
            }

        } else {
            // 不是文件
            ToastUtils.show(SignData1Activity.this, "请选择一个正确的文件");
        }
    }
}
