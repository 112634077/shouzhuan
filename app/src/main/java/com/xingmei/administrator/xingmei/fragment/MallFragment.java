package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.Mall_ItemAdapter;

public class MallFragment extends Fragment {
    protected GridView mGridView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mall, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = getActivity().findViewById(R.id.mall_gridView);
        mGridView.setAdapter(new Mall_ItemAdapter());
    }
}
