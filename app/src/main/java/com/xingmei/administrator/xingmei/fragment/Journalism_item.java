package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.adapter.RecyclerViewAdapter;
import com.xingmei.administrator.xingmei.networktools.NetPicture;
import com.xingmei.administrator.xingmei.utils.JsonThredadPool;
import com.xingmei.administrator.xingmei.utils.MoreTypeBean;
import com.xingmei.administrator.xingmei.utils.MyCachedThreadPool;
import com.xingmei.administrator.xingmei.utils.MyTask;
import com.xingmei.administrator.xingmei.utils.ThreadPool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journalism_item extends Fragment implements ThreadPool{
    private MyTask myTask;
    private MyCachedThreadPool myCachedThreadPool;
    private List<MoreTypeBean> moreTypeBeans;

    private RecyclerViewAdapter recyclerViewAdapter;
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
        String source = getArguments().getString("soucre");
        if (source.equals("top"))
        init();
    }

    private void init(){
        RecyclerView journalism_recycler = getActivity().findViewById(R.id.journalism_recycler);
        journalism_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        moreTypeBeans = new ArrayList<>();
        MoreTypeBean moreTypeBean = new MoreTypeBean();
        moreTypeBeans.add(moreTypeBean);

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), moreTypeBeans);

        journalism_recycler.setAdapter(recyclerViewAdapter);

//        getData(recyclerViewAdapter,moreTypeBeans);
        getData();
    }

    private void getData(){
        String type = getArguments().getString("soucre");
        Map<String,Object> params = new HashMap();
        params.put("type",type);

        myCachedThreadPool = new MyCachedThreadPool(getActivity(),NetPicture.XINURL,params);
        myCachedThreadPool.setOnThreadPool(this);
        myCachedThreadPool.cachedThredPool();
    }

    private void getData(RecyclerViewAdapter recyclerViewAdapter,List<MoreTypeBean> moreTypeBeanList){
        String source = getArguments().getString("soucre");
        myTask = new MyTask(recyclerViewAdapter,source,moreTypeBeanList);
        myTask.execute(NetPicture.XINURL);

    }

    @Override
    public void onExecute() {
        System.out.println("onExecute====================");
//        Toast.makeText(getActivity(),"开始执行",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInBackground(String result) {
//        System.out.println("onInBackground===================="+result);
//        if (TextUtils.isEmpty(result) || result.contains("错误码")){
//            Toast.makeText(getActivity(),"数据获取失败"+result,Toast.LENGTH_LONG).show();
//            return;
//        }
//        moreTypeBeans.clear();
//        try {
//            JSONObject jsonObject = JsonThredadPool.analysisJsonObject(result);
//            JSONArray jsonArray = JsonThredadPool.analysisJsonArray(jsonObject,"data");
//            getJsonData(jsonArray);
//
//            recyclerViewAdapter.notifyDataSetChanged();
//        }catch (Exception e){
////            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
//            e.printStackTrace();}

    }

    @Override
    public void onCancelled() {
        System.out.println("onCancelled" +
                "====================");
//        Toast.makeText(getActivity(),"执行结束",Toast.LENGTH_LONG).show();
    }

    private void getJsonData(JSONArray jsonArray) throws Exception{
        for (int i = 0; i < jsonArray.length(); i++ ){
            MoreTypeBean moreTypeBean = new MoreTypeBean();
            moreTypeBean.setTitleString(jsonArray.getJSONObject(i).getString("title"));
            moreTypeBean.setSource(jsonArray.getJSONObject(i).getString("author_name"));
            moreTypeBean.setContentURL(jsonArray.getJSONObject(i).getString("url"));

            String pic = "";
            String pic2 = "";
            String pic3 = "";

            if (jsonArray.getJSONObject(i).has("thumbnail_pic_s")){
                pic = jsonArray.getJSONObject(i).getString("thumbnail_pic_s");
            }
            if (jsonArray.getJSONObject(i).has("thumbnail_pic_s02")){
                pic2 = jsonArray.getJSONObject(i).getString("thumbnail_pic_s02");
            }
            if (jsonArray.getJSONObject(i).has("thumbnail_pic_s03")){
                pic3 = jsonArray.getJSONObject(i).getString("thumbnail_pic_s03");
            }

            List<String> icon = new ArrayList<>();
            //一张图片都没有
            if(TextUtils.isEmpty(pic)){
                moreTypeBean.setType(0);
                moreTypeBean.setIconURL(icon);
            }else if (!TextUtils.isEmpty(pic) && TextUtils.isEmpty(pic3)){//有图片一没有图片二
                icon.add(pic);
                moreTypeBean.setType(1);
                moreTypeBean.setIconURL(icon);
            }else {
                icon.add(pic);
                icon.add(pic2);
                icon.add(pic3);
                moreTypeBean.setIconURL(icon);
                moreTypeBean.setType(2);
            }
            moreTypeBeans.add(moreTypeBean);
        }
    }


    //fragment方法  解除activi绑定
    @Override
    public void onDetach() {
        super.onDetach();
    }


}
