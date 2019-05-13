package kr.insungjung.semifinalproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import kr.insungjung.semifinalproject.adapters.PagerAdapter;
import kr.insungjung.semifinalproject.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    PagerAdapter mPagerAdapter;

    private TabLayout mTabBar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        /**
         * 탭 레이아웃
         * (참고) https://re-build.tistory.com/25
         */
        mTabBar = act.tabBar;
        mTabBar.addTab(mTabBar.newTab().setText("은행목록"));
        mTabBar.addTab(mTabBar.newTab().setText("공지사항"));

        mViewPager = act.viewPager;

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabBar.getTabCount());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabBar));

        mTabBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /** 프래그먼트 */

        /* 1번 프래그먼트 버튼 클릭 *//*
        act.changeFragOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                act.viewPager.setCurrentItem(0);

                act.changeFragOneBtn.setText("현재 선택됨");
                act.changeFragTwoBtn.setText("2번 프래그먼트");
            }
        });

        *//* 2번 프래그먼트 버튼 클릭 *//*
        act.changeFragTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                act.viewPager.setCurrentItem(1);

                act.changeFragOneBtn.setText("1번 프래그먼트");
                act.changeFragTwoBtn.setText("현재 선택됨");
            }
        });

        *//* 뷰페이저 *//*
        act.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    act.changeFragOneBtn.setText("현재 선택됨");
                    act.changeFragTwoBtn.setText("2번 프래그먼트");
                } else if (i == 1) {
                    act.changeFragOneBtn.setText("1번 프래그먼트");
                    act.changeFragTwoBtn.setText("현재 선택됨");
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });*/
    }

    @Override
    public void setValues() {

        /**
         * 뷰페이저
         */
        act.viewPager.setOffscreenPageLimit(3); // 프래그먼의 갯수와 맞춰주자!

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);
        act.viewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
