package rocky.android.gadsleaderboard;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GadsAdapter extends FragmentPagerAdapter {
    Context mContext;
    int mTotalTabs;

    public GadsAdapter(Context c, FragmentManager fm, int total_tabs) {
        super(fm);
        mContext = c;
        mTotalTabs = total_tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LearningFragment mLearningFragment = new LearningFragment();
                return mLearningFragment;
            case 1:
                SkillsFragment mSkillsFragment = new SkillsFragment();
                return mSkillsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}
