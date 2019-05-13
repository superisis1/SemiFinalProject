package kr.insungjung.semifinalproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.insungjung.semifinalproject.fragments.FragmentOne;
import kr.insungjung.semifinalproject.fragments.FragmentThree;
import kr.insungjung.semifinalproject.fragments.FragmentTwo;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    Fragment frag1, frag2, frag3;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fr = null;

        if (position == 0) {
            if (frag1 == null) {
                frag1 = new FragmentOne();
            }

            fr = frag1;
        }
        else if (position == 1) {
            if (frag2 == null) {
                frag2 = new FragmentTwo();
            }

            fr = frag2;
        }
        else if (position == 2) {
            if (frag3 == null) {
                frag3 = new FragmentThree();
            }

            fr = frag3;
        }

        return fr;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}




