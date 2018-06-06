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
import com.xingmei.administrator.xingmei.adapter.RecyclerViewAdapter;
import com.xingmei.administrator.xingmei.networktools.NetPicture;
import com.xingmei.administrator.xingmei.utils.MoreTypeBean;
import com.xingmei.administrator.xingmei.utils.MyTask;

import java.util.ArrayList;
import java.util.List;

public class Journalism_item extends Fragment {
    private MyTask myTask;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journalism_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init(){
        RecyclerView journalism_recycler = getActivity().findViewById(R.id.journalism_recycler);
        journalism_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        List<MoreTypeBean> moreTypeBeans = new ArrayList<>();
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), moreTypeBeans);
        journalism_recycler.setAdapter(recyclerViewAdapter);

        getData(recyclerViewAdapter,moreTypeBeans);
    }

    private void getData(RecyclerViewAdapter recyclerViewAdapter,List<MoreTypeBean> moreTypeBeanList){
        String source = getArguments().getString("soucre");
        myTask = new MyTask(recyclerViewAdapter,source,moreTypeBeanList);
        myTask.execute(NetPicture.XINURL);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        myTask.cancel(true);
    }
}
