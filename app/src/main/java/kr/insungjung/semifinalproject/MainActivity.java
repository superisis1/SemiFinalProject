package kr.insungjung.semifinalproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import kr.insungjung.semifinalproject.adapters.PagerAdapter;
import kr.insungjung.semifinalproject.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        /** 프래그먼트 */

        /* 1번 프래그먼트 버튼 클릭 */
        act.changeFragOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                act.viewPager.setCurrentItem(0);

                act.changeFragOneBtn.setText("현재 선택됨");
                act.changeFragTwoBtn.setText("2번 프래그먼트");
            }
        });

        /* 2번 프래그먼트 버튼 클릭 */
        act.changeFragTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                2번 화면 버튼을 누름 => 2번째 페이지를 보여주자.
                act.viewPager.setCurrentItem(1);

                act.changeFragOneBtn.setText("1번 프래그먼트");
                act.changeFragTwoBtn.setText("현재 선택됨");
            }
        });

        /* 뷰페이저 */
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
        });
    }

    @Override
    public void setValues() {

        /** 프래그먼트 - 뷰페이저 */

        act.viewPager.setOffscreenPageLimit(3); // 프래그먼의 갯수와 맞춰주자!

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);
        act.viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
