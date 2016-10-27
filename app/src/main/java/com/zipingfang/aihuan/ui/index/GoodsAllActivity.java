package com.zipingfang.aihuan.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.bean.Category;
import com.zipingfang.aihuan.bean.IncomeDetail;
import com.zipingfang.aihuan.constants.Constants;
import com.zipingfang.aihuan.dao.BaseDao;
import com.zipingfang.aihuan.dao.CategoryServerDao;
import com.zipingfang.aihuan.ui.SearchActivity;
import com.zipingfang.aihuan.utils.DeviceUtil;
import com.zipingfang.aihuan.view.LazyViewPager;
import com.zipingfang.aihuan.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部商品
 * Created by 峰 on 2016/9/18.
 */
public class GoodsAllActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView tv_watch, tv_ring, tv_brooch;
    private List<Fragment> fragments = new ArrayList<>();
    private GoodsAllFragment goodsAllFragment = new GoodsAllFragment();
    public static LazyViewPager viewPager;

    public static List<Category> categoryList = new ArrayList<>();
    private PagerSlidingTabStrip title_tab;
    private PagerOnPageChangeListener pageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_all_goods);
        initView();
        initActionBar();
        loadCategory();
    }

    private void loadCategory() {
        final CategoryServerDao serverDao = new CategoryServerDao(this);
        serverDao.loadData(new BaseDao.IDaoCallback() {
            @Override
            public void exec(boolean success, Object obj) {
                if (serverDao.isSucc) {
                    categoryList = new ArrayList<Category>();
                    Category cateAll = new Category();
                    cateAll.setCname("全部");
                    cateAll.setId("");
                    cateAll.setSort("0");
                    categoryList.add(cateAll);
                    categoryList.addAll(serverDao.mData);
                    initFragment(categoryList);
                    FragmentAdapter adapter = new FragmentAdapter(
                            getSupportFragmentManager(), categoryList);
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(0);
                    Constants.category_index = 0;
                    pageChangeListener = new PagerOnPageChangeListener();
                    viewPager.setOnPageChangeListener(pageChangeListener);
                    title_tab.setViewPager(viewPager);
                    title_tab.setOnPageChangeListener(pageChangeListener);
                }
            }
        });
    }


    private void initFragment(List<Category> categoryList) {
        for (int i = 0; i < categoryList.size(); i++) {
            fragments.add(new GoodsAllFragment());
        }
    }


    // Fragment适配器
    class FragmentAdapter extends FragmentPagerAdapter {

        private List<Category> titleList;

        public FragmentAdapter(FragmentManager fm, List<Category> titleList) {
            super(fm);
            this.titleList = titleList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).getCname();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Constants.categoryId = categoryList.get(position).getId();
        viewPager.setCurrentItem(position);
        Constants.category_index = position;
//        categoryAdapter.notifyDataSetChanged();
    }

    /* ViewPager的PageChangeListener(页面改变的监听器)
        * 2012-5-24 下午3:14:27
                */
    private class PagerOnPageChangeListener implements LazyViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            type = categoryList.get(position).getId();
//            Bundle bundle = new Bundle();
//            bundle.putString("type", categoryList.get(position).getId());
//            fragments.get(position).setArguments(bundle);
            Constants.categoryId = categoryList.get(position).getId();
            Constants.category_index = position;

//            categoryAdapter.notifyDataSetChanged();
            //Log.i("zj", "position="+position);
        }

    }

    private void initView() {
        viewPager = (LazyViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(0);
//        title_tab = (android.support.design.widget.TabLayout) findViewById(R.id.main_tab);
        title_tab = (PagerSlidingTabStrip) findViewById(R.id.main_tab);
//        title_tab.setTabPaddingLeftRight(24);
    }


    private void initActionBar() {
        View actionBar = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) actionBar.findViewById(R.id.tv_title);
        ImageButton btn_back = (ImageButton) actionBar.findViewById(R.id.btn_back);
        ImageView iv_right = (ImageView) actionBar.findViewById(R.id.iv_title_right);
        iv_right.setImageResource(R.mipmap.btn_search_white);
        iv_right.setVisibility(View.VISIBLE);
        tv_title.setText("精品推荐");
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.iv_title_right:
                Intent intent = new Intent(GoodsAllActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
