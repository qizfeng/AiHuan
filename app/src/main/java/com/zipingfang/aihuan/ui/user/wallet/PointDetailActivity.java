package com.zipingfang.aihuan.ui.user.wallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Point;
import com.zipingfang.aihuan.bean.PointDetail;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.PointDetailServerDao;
import com.zipingfang.aihuan.ui.adapter.PointDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/21.
 */

public class PointDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_point;
    private TextView tv_level;
    private ListView lv_container;
    private View header;
    private Point point = new Point();
    private List<PointDetail> pointDetails = new ArrayList<>();
    private PointDetailAdapter mAdapter;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_my_score);
        lv_container = (ListView) findViewById(R.id.lv_container);
        header = LayoutInflater.from(this).inflate(R.layout.zpf_include_my_score_header, null);
        lv_container.addHeaderView(header);
        tv_level = (TextView) header.findViewById(R.id.tv_level);
        tv_point = (TextView) header.findViewById(R.id.tv_score);
        initActionBar();
        loadData();
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("我的积分");
        TextView tv_title_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.VISIBLE);
        tv_title_right.setText("规则");
        btn_back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title_right:
                break;
        }
    }

    private void loadData() {
        final PointDetailServerDao serverDao = new PointDetailServerDao(this);
        serverDao.mid = getUserId(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    point = serverDao.point;
                    tv_point.setText(point.getPoint());
                    String level = point.getLevel();

                    if ("1".equals(level))
                        tv_level.setText("普通会员");
                    else if ("2".equals(level))
                        tv_level.setText("银牌会员");
                    else if ("3".equals(level))
                        tv_level.setText("金牌会员");
                    else if ("4".equals(level))
                        tv_level.setText("钻石会员");
                    pointDetails = serverDao.points;
                    mAdapter = new PointDetailAdapter(PointDetailActivity.this, pointDetails);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

}
