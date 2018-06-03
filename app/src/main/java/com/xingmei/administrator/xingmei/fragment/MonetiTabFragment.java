package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.MonetRecyclerViewAdapter;


public class MonetiTabFragment extends Fragment {

    protected RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_tab, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        String url = getArguments().getString("url");
//        Toast.makeText(getActivity(),url+"",Toast.LENGTH_LONG).show();
    }

    private void initView(){
        mRecyclerView = getView().findViewById(R.id.moneti_tab_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));

        mRecyclerView.setAdapter(new MonetRecyclerViewAdapter());
//        mGridView.setAdapter(new Image_ItemAdapter());
    }
}
