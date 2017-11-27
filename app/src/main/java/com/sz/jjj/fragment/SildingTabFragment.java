package com.sz.jjj.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sz.jjj.R;

/**
 * Created by jjj on 2017/10/30.
 *
 * @description:
 */

public class SildingTabFragment extends Fragment {
    private String mTitle;

    public static SildingTabFragment getInstance(String title) {
        SildingTabFragment sf = new SildingTabFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_silding_tab, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);

        return v;
    }
}
