package kr.insungjung.semifinalproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import kr.insungjung.semifinalproject.adapters.PagerAdapter;
import kr.insungjung.semifinalproject.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    private PagerAdapter mPagerAdapter;
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

    }

    @Override
    public void setValues() {
        /* 탭 레이아웃 (참고 - https://re-build.tistory.com/25) */
        mTabBar = act.tabBar;
        mTabBar.addTab(mTabBar.newTab().setText("은행목록"));
        mTabBar.addTab(mTabBar.newTab().setText("공지사항"));
        mTabBar.addTab(mTabBar.newTab().setText("회원정보"));

        /* 뷰페이저 */
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mTabBar.getTabCount());

        mViewPager = act.viewPager;
        mViewPager.setOffscreenPageLimit(4);
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
    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

}
