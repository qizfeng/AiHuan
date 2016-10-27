package com.zipingfang.aihuan.ui.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zipingfang.aihuan.BaseActivity;
import com.zipingfang.aihuan.R;
import com.zipingfang.aihuan.ui.user.AttentionGoodsFragment;
import com.zipingfang.aihuan.ui.user.AttentionPartyFragment;
import com.zipingfang.aihuan.ui.user.MyAttentionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zipingfang on 16/10/22.
 */

public class SearchResultActivity extends BaseActivity implements View.OnClickListener {
    private SearchPartyFragment partyFragment = new SearchPartyFragment();
    private SearchGoodsFragment goodsFragment = new SearchGoodsFragment();
    private LinearLayout ll_party, ll_goods;
    private TextView tv_party, tv_goods;
    private View line_party, line_goods;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zpf_activity_my_attention);
        initView();
        initFragment();
        initActionBar();
        FragmentAdapter adapter = new FragmentAdapter(
                getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }

    private void initView() {
        ll_party = (LinearLayout) findViewById(R.id.ll_party);
        ll_goods = (LinearLayout) findViewById(R.id.ll_goods);
        tv_party = (TextView) findViewById(R.id.tv_party);
        tv_goods = (TextView) findViewById(R.id.tv_goods);
        line_party = (View) findViewById(R.id.line_party);
        line_goods = (View) findViewById(R.id.line_goods);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        ll_goods.setOnClickListener(this);
        ll_party.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    tv_party.setTextColor(getResources().getColor(R.color.black));
                    line_party.setBackgroundColor(getResources().getColor(R.color.black));

                    tv_goods.setTextColor(getResources().getColor(R.color.zpf_gray_line));
                    line_goods.setBackgroundColor(getResources().getColor(R.color.white));
                    viewPager.setCurrentItem(0);
                }else if(position==1){
                    tv_party.setTextColor(getResources().getColor(R.color.zpf_gray_line));
                    line_party.setBackgroundColor(getResources().getColor(R.color.white));

                    tv_goods.setTextColor(getResources().getColor(R.color.black));
                    line_goods.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void initActionBar() {
        View view = findViewById(R.id.il_topbar);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText(getIntent().getStringExtra("search"));
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.ll_party:
                tv_party.setTextColor(getResources().getColor(R.color.black));
                line_party.setBackgroundColor(getResources().getColor(R.color.black));

                tv_goods.setTextColor(getResources().getColor(R.color.zpf_txt_gray));
                line_goods.setBackgroundColor(getResources().getColor(R.color.white));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_goods:
                tv_party.setTextColor(getResources().getColor(R.color.zpf_txt_gray));
                line_party.setBackgroundColor(getResources().getColor(R.color.white));

                tv_goods.setTextColor(getResources().getColor(R.color.black));
                line_goods.setBackgroundColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
                break;
        }
    }
    private void initFragment(){
        fragments.add(new SearchGoodsFragment());
        fragments.add(new SearchPartyFragment());
    }

    // Fragment适配器
    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}

