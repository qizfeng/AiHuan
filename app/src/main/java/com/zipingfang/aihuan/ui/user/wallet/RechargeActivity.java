package com.zipingfang.aihuan.ui.user.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pingplusplus.android.Pingpp;
import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.pingxx.PaymentRequest;
import com.zipingfang.aihuan.pingxx.PingxxUtils;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;

import java.util.UUID;

import static com.zipingfang.aihuan.pingxx.PingxxUtils.postJson;

/**
 * Created by zipingfang on 16/10/20.
 */

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_recharge_money;
    private ImageView iv_wechat, iv_alipay, iv_jd;
    private Button btn_next;
    private String channel = PingxxUtils.CHANNEL_WECHAT;
    private LinearLayout ll_wechat,ll_alipay,ll_jd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_recharge);
        initView();
        initActionBar();

    }

    private void initView() {
        et_recharge_money = (EditText) findViewById(R.id.et_recharge_money);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        iv_wechat =(ImageView)findViewById(R.id.iv_wechat);
        iv_alipay =(ImageView)findViewById(R.id.iv_alipay);
        iv_jd =(ImageView)findViewById(R.id.iv_jd);
        iv_wechat.setImageResource(R.mipmap.btn_circle_s);
        ll_wechat =(LinearLayout)findViewById(R.id.ll_wechat);
        ll_alipay =(LinearLayout)findViewById(R.id.ll_alipay);
        ll_jd =(LinearLayout)findViewById(R.id.ll_jd);
        ll_wechat.setOnClickListener(this);
        ll_alipay.setOnClickListener(this);
        ll_jd.setOnClickListener(this);

    }





    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("账户充值");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_next:
                String money = et_recharge_money.getText().toString();
                if (StringUtils.isEmpty(money)) {
                    ToastUtils.show(RechargeActivity.this, "请输入充值金额");
                    return;
                }
                new PaymentTask().execute(new PaymentRequest(channel, Float.parseFloat(money)*100+"", System.currentTimeMillis()+ "-" + getUserId(RechargeActivity.this)));
                break;
            case R.id.ll_wechat:
                iv_wechat.setImageResource(R.mipmap.btn_circle_s);
                iv_alipay.setImageResource(R.mipmap.btn_circle_n);
                iv_jd.setImageResource(R.mipmap.btn_circle_n);
                channel=PingxxUtils.CHANNEL_WECHAT;
                break;
            case R.id.ll_alipay:
                iv_wechat.setImageResource(R.mipmap.btn_circle_n);
                iv_alipay.setImageResource(R.mipmap.btn_circle_s);
                iv_jd.setImageResource(R.mipmap.btn_circle_n);
                channel = PingxxUtils.CHANNEL_ALIPAY;
                break;
            case R.id.ll_jd:
                iv_wechat.setImageResource(R.mipmap.btn_circle_n);
                iv_alipay.setImageResource(R.mipmap.btn_circle_n);
                iv_jd.setImageResource(R.mipmap.btn_circle_s);
                channel = PingxxUtils.CHANNEL_JDPAY_WAP;
                break;
        }
    }

    class PaymentTask extends AsyncTask<PaymentRequest, Void, String> {

        @Override
        protected void onPreExecute() {
            //按键点击之后的禁用，防止重复点击
            btn_next.setOnClickListener(null);
        }

        @Override
        protected String doInBackground(PaymentRequest... pr) {

            PaymentRequest paymentRequest = pr[0];
            String data = null;
            String json = new Gson().toJson(paymentRequest);
            try {
                //向Your Ping++ Server SDK请求数据
                data = postJson(Constants.URL_PAY_PINGXX, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        /**
         * 获得服务端的charge，调用ping++ sdk。
         */
        @Override
        protected void onPostExecute(String data) {
            if (null == data) {
//                showMsg("请求出错", "请检查URL", "URL无法获取charge");
                ToastUtils.show(RechargeActivity.this, "支付内部出错，请通知程序员");
                return;
            }
            Log.d("charge", data);
            Pingpp.createPayment(RechargeActivity.this, data);
            //QQ钱包调起支付方式  “qwalletXXXXXXX”需与AndroidManifest.xml中的data值一致
            //建议填写规则:qwallet + APP_ID
//            Pingpp.createPayment(RechargeActivity.this, data, "qwalletXXXXXXX");
        }

    }

    /**
     * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。
     * 最终支付成功根据异步通知为准
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        btn_next.setOnClickListener(this);

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                showMsg(result, errorMsg, extraMsg);

            }
        }
    }

    public void showMsg(String title, String msg1, String msg2) {
        String str = title;
        if (null != msg1 && msg1.length() != 0) {
            str += "\n" + msg1;
        }
        if (null != msg2 && msg2.length() != 0) {
            str += "\n" + msg2;
        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(str);
//        builder.setTitle("提示");
//        builder.setPositiveButton("OK", null);
//        builder.create().show();
        ToastUtils.show(this, str);
    }

}
