package com.xingmei.administrator.xingmei.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.Image_ItemAdapter;

/**
 * Created by Administrator o 2018/3/15.
 */

public class ImageTabFragment extends Fragment {
    protected GridView mGridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.image_tab_content, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        String url = getArguments().getString("url");
        Toast.makeText(getActivity(),url+"",Toast.LENGTH_LONG).show();
    }

    private void initView(){
        mGridView = getView().findViewById(R.id.image_tab_gridView);
        mGridView.setAdapter(new Image_ItemAdapter());
    }
}
