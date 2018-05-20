package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.Image_ItemAdapter;
import com.xingmei.administrator.xingmei.onclick.ListClick;

public class VideoFragment extends Fragment {
    protected GridView mGridView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = getActivity().findViewById(R.id.video_gridView);
        mGridView.setAdapter(new Image_ItemAdapter());
        mGridView.setOnItemClickListener(new ListClick(getActivity(),0));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
