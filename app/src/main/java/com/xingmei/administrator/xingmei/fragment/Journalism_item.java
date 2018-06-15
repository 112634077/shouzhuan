package com.xingmei.administrator.xingmei.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.xingmei.administrator.xingmei.activity.MyWebViewActivity;
import com.xingmei.administrator.xingmei.adapter.RecyclerViewAdapter;
import com.xingmei.administrator.xingmei.hanler.NotLeakHandler;
import com.xingmei.administrator.xingmei.networktools.NetPicture;
import com.xingmei.administrator.xingmei.onclick.ThreadPool;
import com.xingmei.administrator.xingmei.recycler.RecyclerViewDivider;
import com.xingmei.administrator.xingmei.utils.JsonThredadPool;
import com.xingmei.administrator.xingmei.utils.MoreTypeBean;
import com.xingmei.administrator.xingmei.utils.MyCachedThreadPool;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Journalism_item extends Fragment implements ThreadPool,MyCachedThreadPool.OnThreadTask,RecyclerViewAdapter.OnMyItemClickListener{
    private static final String ARG_PARAM1 = "journalismSoucre";

    private MyCachedThreadPool myCachedThreadPool;
    private List<MoreTypeBean> moreTypeBeans;
    private List<String> pyteList;
    private String soucres;

    private RecyclerView journalism_recycler;
    private RecyclerViewAdapter recyclerViewAdapter = null;
    private JournalismHandler journalismHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pyteList = new ArrayList<>();
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

        if (getArguments() != null){
            String source = getArguments().getString("soucre");
            if (source.equals("top")){
                soucres = source;
                getData(source);
                init();
            }
        }

    }

    private void init(){
        journalism_recycler = getView().findViewById(R.id.journalism_recycler);
        journalism_recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        journalism_recycler.addItemDecoration(new RecyclerViewDivider(getView().getContext(),LinearLayoutManager.VERTICAL));
        moreTypeBeans = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), moreTypeBeans);
        journalism_recycler.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnMyItemClickListener(this);
    }

    public void setJournalismHandler(String soucre){
        journalismHandler = new JournalismHandler();
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("soucre",soucre);
        message.setData(bundle);
        journalismHandler.sendMessage(message);
    }

    private void getData(String soucre){

        Map<String,Object> params = new HashMap<>();
        params.put("type",soucre);

        NotLeakHandler notLeakHandler = new NotLeakHandler(this);
        notLeakHandler.setThreadPool(this);
        myCachedThreadPool = new MyCachedThreadPool(getActivity(),NetPicture.XINURL,params,notLeakHandler);
        myCachedThreadPool.setOnThreadTask(this);
        myCachedThreadPool.cachedThredPool();
    }

    @Override
    public void onExecute() {
        Toast.makeText(getActivity(),"开始执行",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInBackground(String result) {
        Toast.makeText(getActivity(),"数据解析完成",Toast.LENGTH_LONG).show();

        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCliable() {
        if (getActivity() != null)
        Toast.makeText(getActivity(),"数据获取失败",Toast.LENGTH_LONG).show();
    }

    private List<MoreTypeBean> getJsonData(JSONArray jsonArray) throws Exception{

        List<MoreTypeBean> moreTypeBeanList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++ ){
            MoreTypeBean moreTypeBean = new MoreTypeBean();
            moreTypeBean.setTitleString(jsonArray.getJSONObject(i).getString("title"));
            moreTypeBean.setSource(jsonArray.getJSONObject(i).getString("author_name"));
            moreTypeBean.setContentURL(jsonArray.getJSONObject(i).getString("url"));

            if(i == 0){
                System.out.println(jsonArray.getJSONObject(i).getString("title"));
                System.out.println(jsonArray.getJSONObject(i).getString("url"));
            }

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

            ArrayList<String> icon = new ArrayList<>();
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
            moreTypeBeanList.add(moreTypeBean);
        }

        return moreTypeBeanList;
    }

    //fragment方法  解除activi绑定
    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public int onTask(String result) {
        if (TextUtils.isEmpty(result) || result.contains("错误码")){
            return 1;
        }
        List<MoreTypeBean> moreTypeBeanList = new ArrayList<>();
        moreTypeBeanList.clear();
        try {
            JSONObject jsonObject = JsonThredadPool.analysisJsonObject(result);

            JSONArray jsonArray = JsonThredadPool.analysisJsonArray(jsonObject,"data");

            if (pyteList != null && pyteList.size() != 0){
                for (int i = 0; i < pyteList.size(); i ++){
                    String s = pyteList.get(i);
                    if(s.equals(soucres)){
                        soucres = "";
                        break;
                    }
                }
            }

            if (TextUtils.isEmpty(soucres)){
                moreTypeBeanList.addAll(getJsonData(jsonArray));
                moreTypeBeans.addAll(moreTypeBeanList);
                return 0;
            }

            pyteList.add(soucres);
            moreTypeBeanList.addAll(getJsonData(jsonArray));

            moreTypeBeans.clear();
            moreTypeBeans.addAll(moreTypeBeanList);
            return 0;
        }catch (Exception e){e.printStackTrace();System.out.println("e==================="+e.toString()); }
        return 1;
    }

    @Override
    public void myClick(View v) {
        int num = journalism_recycler.getChildAdapterPosition(v);
        MoreTypeBean moreTypeBean1 = moreTypeBeans.get(num);
        Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
        if (moreTypeBean1 != null){
            intent.putExtra("url", moreTypeBean1.getContentURL());
            startActivity(intent);
        }
    }

    @Override
    public void mLongClick(View v) {

    }

    class JournalismHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            String soucre = msg.getData().getString("soucre");
            if (moreTypeBeans != null) {
                return;
            }
            init();
            if (soucre != null){
                switch (soucre){
                    case "top":soucres = soucre;getData(soucre);
                        break;
                    case "shehui": soucres = soucre;getData(soucre);
                        break;
                    case "guonei": soucres = soucre;getData(soucre);
                        break;
                    case "guoji": soucres = soucre;getData(soucre);
                        break;
                    case "yule": soucres = soucre;getData(soucre);
                        break;
                    case "tiyu": soucres = soucre;getData(soucre);
                        break;
                    case "junshi": soucres = soucre;getData(soucre);
                        break;
                    case "keji": soucres = soucre;getData(soucre);
                        break;
                }
            }
        }
    }
}
