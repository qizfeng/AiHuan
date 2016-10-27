package com.zipingfang.aihuan.ui.trade;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Active;
import com.zipingfang.aihuan.bean.Category;
import com.zipingfang.aihuan.dao.ActiveListServerDao;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.CategoryServerDao;
import com.zipingfang.aihuan.pullToRefreshView.PullToRefreshLayout;
import com.zipingfang.aihuan.pullToRefreshView.PullableListView;
import com.zipingfang.aihuan.ui.adapter.ActiveAdapter;
import com.zipingfang.aihuan.ui.adapter.GridViewRadioAdapter;
import com.zipingfang.aihuan.utils.StringUtils;
import com.zipingfang.aihuan.utils.ToastUtils;
import com.zipingfang.aihuan.utils.XmlUtils;
import com.zipingfang.aihuan.view.MultipleRadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qizfeng on 16/10/18.
 */

public class ActiveListActivity extends BaseActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {
    private PullToRefreshLayout refreshLayout;
    private PullableListView lv_container;
    private DrawerLayout drawerLayout;
    private RelativeLayout rl_right_layout;
    private List<Active> mData = new ArrayList<>();
    private ActiveAdapter mAdapter;
    private int pageIndex = 1;
    private String party_type = "";
    private String status = "-1";
    private String category = "-1";
    private RadioButton rb_all, rb_starting, rb_un_start, rb_end;
    private MultipleRadioGroup radioGroup;
    private TextView tv_reset;
    private TextView tv_submit;
    public static String SCREEN_STATE = "screen_state";
    private List<Category> categories = new ArrayList<>();
    private GridViewRadioAdapter categoryAdapter;
    private GridView gv_category;
    private String category_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_active);
        party_type = getIntent().getStringExtra("party_type");
        status = XmlUtils.getFromXml(this, SCREEN_STATE, "-1");
//        category = XmlUtils.getFromXml(this,"category","0");
        status="-1";
        category="0";
        category_id=getIntent().getStringExtra("category_id");
        initView();
        loadCategory();
    }

    private void initView() {
        initActionBar();
        gv_category = (GridView) findViewById(R.id.gridview_radio);
        tv_reset = (TextView) findViewById(R.id.tv_reset);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
        lv_container = (PullableListView) findViewById(R.id.lv_container);
        refreshLayout.setOnRefreshListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        rl_right_layout = (RelativeLayout) findViewById(R.id.rl_layout_right);
        rb_all = (RadioButton) findViewById(R.id.rb_all);
        rb_starting = (RadioButton) findViewById(R.id.rb_starting);
        rb_un_start = (RadioButton) findViewById(R.id.rb_un_start);
        rb_end = (RadioButton) findViewById(R.id.rb_end);
        radioGroup = (MultipleRadioGroup) findViewById(R.id.radioGroup);

        rb_all.getLayoutParams().width = 220;
        rb_all.getLayoutParams().height = 100;
        rb_starting.getLayoutParams().width = 220;
        rb_starting.getLayoutParams().height = 100;
        rb_un_start.getLayoutParams().width = 220;
        rb_un_start.getLayoutParams().height = 100;
        rb_end.getLayoutParams().width = 220;
        rb_end.getLayoutParams().height = 100;
        if ("-1".equals(status)){
            rb_all.setChecked(true);
            rb_all.setTextColor(getResources().getColor(R.color.white));
            rb_starting.setTextColor(getResources().getColor(R.color.black));
            rb_un_start.setTextColor(getResources().getColor(R.color.black));
            rb_end.setTextColor(getResources().getColor(R.color.black));}
        else if ("4".equals(status)){
            rb_all.setTextColor(getResources().getColor(R.color.black));
            rb_starting.setTextColor(getResources().getColor(R.color.white));
            rb_un_start.setTextColor(getResources().getColor(R.color.black));
            rb_end.setTextColor(getResources().getColor(R.color.black));
            rb_starting.setChecked(true);
        }
        else if ("3".equals(status)){
            rb_all.setTextColor(getResources().getColor(R.color.black));
            rb_starting.setTextColor(getResources().getColor(R.color.black));
            rb_un_start.setTextColor(getResources().getColor(R.color.white));
            rb_end.setTextColor(getResources().getColor(R.color.black));
            rb_un_start.setChecked(true);
        }
        else if ("5".equals(status)){
            rb_all.setTextColor(getResources().getColor(R.color.black));
            rb_starting.setTextColor(getResources().getColor(R.color.black));
            rb_un_start.setTextColor(getResources().getColor(R.color.black));
            rb_end.setTextColor(getResources().getColor(R.color.white));
            rb_end.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new MultipleRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultipleRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all:
                        rb_all.setTextColor(getResources().getColor(R.color.white));
                        rb_starting.setTextColor(getResources().getColor(R.color.black));
                        rb_un_start.setTextColor(getResources().getColor(R.color.black));
                        rb_end.setTextColor(getResources().getColor(R.color.black));
                        status = "-1";
                        XmlUtils.saveToXml(ActiveListActivity.this, SCREEN_STATE, status);
                        break;
                    case R.id.rb_starting:
                        rb_all.setTextColor(getResources().getColor(R.color.black));
                        rb_starting.setTextColor(getResources().getColor(R.color.white));
                        rb_un_start.setTextColor(getResources().getColor(R.color.black));
                        rb_end.setTextColor(getResources().getColor(R.color.black));
                        status = "4";
                        XmlUtils.saveToXml(ActiveListActivity.this, SCREEN_STATE, status);
                        break;
                    case R.id.rb_un_start:
                        rb_all.setTextColor(getResources().getColor(R.color.black));
                        rb_starting.setTextColor(getResources().getColor(R.color.black));
                        rb_un_start.setTextColor(getResources().getColor(R.color.white));
                        rb_end.setTextColor(getResources().getColor(R.color.black));
                        status = "3";
                        XmlUtils.saveToXml(ActiveListActivity.this, SCREEN_STATE, status);
                        break;
                    case R.id.rb_end:
                        rb_all.setTextColor(getResources().getColor(R.color.black));
                        rb_starting.setTextColor(getResources().getColor(R.color.black));
                        rb_un_start.setTextColor(getResources().getColor(R.color.black));
                        rb_end.setTextColor(getResources().getColor(R.color.white));
                        status = "5";
                        XmlUtils.saveToXml(ActiveListActivity.this, SCREEN_STATE, status);
                        break;
                }
            }
        });
        tv_reset.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryAdapter.changeState(position);
                XmlUtils.saveToXml(ActiveListActivity.this, "category", position + "");
                category_id = categories.get(position).getId();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_title_right:
                if (drawerLayout.isDrawerOpen(rl_right_layout)) {
                    drawerLayout.closeDrawer(rl_right_layout);
                } else {
                    drawerLayout.openDrawer(rl_right_layout);
                }
                break;
            case R.id.tv_reset:
                status = "-1";

                category_id="";
                loadCategory();
                rb_all.setChecked(true);
//                radioGroup.notifyAll();
                break;
            case R.id.tv_submit:
                drawerLayout.closeDrawer(rl_right_layout);
                loadData();
        }
    }

    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        tv_title.setText("活动专场");
        TextView tv_right = (TextView) actionBar.findViewById(R.id.tv_title_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("筛选");
        tv_right.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void loadData() {
        final ActiveListServerDao serverDao = new ActiveListServerDao(this);
        serverDao.pageIndex = pageIndex + "";
        serverDao.party_type = party_type;
        serverDao.status = status;
        serverDao.category = category_id;
        XmlUtils.saveToXml(this, SCREEN_STATE, status);
        XmlUtils.saveToXml(this,"category",category);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (success) {
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    if (!StringUtils.isEmpty(serverDao.desc))
                        ToastUtils.show(ActiveListActivity.this, serverDao.desc);
                    if (pageIndex == 1)
                        mData = new ArrayList<>();
                    else {
                        if (serverDao.activeList.size() == 0) {
                            ToastUtils.show(ActiveListActivity.this, "没有更多数据");
                            return;
                        }
                    }

                    mData.addAll(serverDao.activeList);
                    mAdapter = new ActiveAdapter(ActiveListActivity.this, mData);
                    lv_container.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                }
            }
        });

    }

    private void loadCategory() {
        final CategoryServerDao serverDao = new CategoryServerDao(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    categories = new ArrayList<Category>();
                    Category categoryAll = new Category();
                    categoryAll.setCname("全部");
                    categoryAll.setId("");
                    categoryAll.setSort("");
                    categories.add(categoryAll);
                    categories.addAll(serverDao.mData);
                    int position = 0;
                    for (int i=0;i<categories.size();i++){
                        if(category_id.equals(categories.get(i).getId())){
                            position=i;
                        }
                    }
                    categoryAdapter = new GridViewRadioAdapter(ActiveListActivity.this, categories, position);
                    gv_category.setAdapter(categoryAdapter);
                    gv_category.deferNotifyDataSetChanged();
                    loadData();
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex = 1;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pageIndex++;
        loadData();

    }
}
