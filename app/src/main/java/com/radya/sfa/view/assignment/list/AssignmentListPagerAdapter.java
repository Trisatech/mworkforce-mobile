package com.radya.sfa.view.assignment.list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.radya.sfa.Constant;

/**
 * Created by aderifaldi on 14/09/2016.
 */
public class AssignmentListPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence Titles[];
    private int numOfTabs;
    private AssignmentListOnProgressFragment assignmentOnProgress;
    private AssignmentListOnCompleteFragment assignmentComplete;
    private String date;
//    private AssignmentAll assignmentOnProgressData, assignmentCompleteData;

    public AssignmentListPagerAdapter(FragmentManager fm, AssignmentListOnProgressFragment assignmentOnProgress,
                                      AssignmentListOnCompleteFragment assignmentComplete,
                                      CharSequence mTitles[], int numOfTabs, String date) { //, AssignmentAll assignmentOnProgressData, AssignmentAll assignmentCompleteData
        super(fm);
        this.assignmentOnProgress = assignmentOnProgress;
        this.assignmentComplete = assignmentComplete;
        this.numOfTabs = numOfTabs;
        this.Titles = mTitles;
        this.date = date;
//        this.assignmentOnProgressData = assignmentOnProgressData;
//        this.assignmentCompleteData = assignmentCompleteData;

    }

    @Override
    public Fragment getItem(int position) {

        Bundle bundle = new Bundle();
        Fragment fragment;

        if (position == 0) {
            bundle.putSerializable("date", date);

            fragment = assignmentOnProgress;
            fragment.setArguments(bundle);

            return fragment;
        } else {
            bundle.putSerializable("date", date);

            fragment = assignmentComplete;
            fragment.setArguments(bundle);

            return fragment;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
