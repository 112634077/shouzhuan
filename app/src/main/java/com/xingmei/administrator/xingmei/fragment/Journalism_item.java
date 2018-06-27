package com.xingmei.administrator.xingmei.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.xingmei.administrator.xingmei.R;
import com.xingmei.administrator.xingmei.activity.MyWebViewActivity;
import com.xingmei.administrator.xingmei.adapter.RecyclerViewAdapter;
import com.xingmei.administrator.xingmei.hanler.NotLeakHandler;
import com.xingmei.administrator.xingmei.networktools.NetPicture;
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

public class Journalism_item extends MyLoadingFragment implements MyCachedThreadPool.OnThreadTask,RecyclerViewAdapter.OnMyItemClickListener{
    private static final String ARG_PARAM1 = "journalismSoucre";

    private MyCachedThreadPool myCachedThreadPool;
    private List<MoreTypeBean> moreTypeBeans;
    private List<String> pyteList;
    private String soucres;
    private int loadCode = 201;

    private View view = null;

    private RecyclerView journalism_recycler;
    private RecyclerViewAdapter recyclerViewAdapter = null;
    private JournalismHandler journalismHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pyteList = new ArrayList<>();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_journalism_item,null);
//        return view
//    }

    @Override
    protected View onSuccessView() {
        System.out.println("onSuccessView=========="+getContext());
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_journalism_item,null);
        init();
        initKongjian();
        return view;
    }

    @Override
    protected int onLoad() {
        System.out.println("onLoad====================="+loadCode);

        if (recyclerViewAdapter != null)
        recyclerViewAdapter.notifyDataSetChanged();
        return 200;
    }

    @Override
    protected void loadData() {
        System.out.println("loadData=====================");

        if (getArguments() != null){
//            String source = getArguments().getString("soucre");
            soucres = getArguments().getString("soucre");
            loadCode = getData(soucres);
        }
//        initKongjian();
    }

    @Override
    protected void loadInit(View view) {
//        init(view);
    }

    private void init(){
        journalism_recycler = view.findViewById(R.id.journalism_recycler);
    }

    private void initKongjian(){
        journalism_recycler.addItemDecoration(new RecyclerViewDivider(view.getContext(),LinearLayoutManager.VERTICAL));
        journalism_recycler.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL, false));

        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), moreTypeBeans);
        recyclerViewAdapter.setOnMyItemClickListener(this);

        moreTypeBeans = new ArrayList<>();
        journalism_recycler.setAdapter(recyclerViewAdapter);
        System.out.println("loadData================="+loadCode);

    }

    public void setJournalismHandler(String soucre){
        journalismHandler = new JournalismHandler();
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("soucre",soucre);
        message.setData(bundle);
        journalismHandler.sendMessage(message);
    }

    private int getData(String soucre){

        Map<String,Object> params = new HashMap<>();
        params.put("type",soucre);

        NotLeakHandler notLeakHandler = new NotLeakHandler(this);
        myCachedThreadPool = new MyCachedThreadPool(getActivity(),NetPicture.XINURL,params,notLeakHandler);
        myCachedThreadPool.setOnThreadTask(this);
        myCachedThreadPool.cachedThredPool();
       return notLeakHandler.getCode();
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
        System.out.println("onTask==================");
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
            if (moreTypeBeans != null){
                moreTypeBeans.clear();
                moreTypeBeans.addAll(moreTypeBeanList);
            }else
                moreTypeBeans = moreTypeBeanList;

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
                    case "top":soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "shehui": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "guonei": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "guoji": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "yule": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "tiyu": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "junshi": soucres = soucre;loadCode = getData(soucre);
                        break;
                    case "keji": soucres = soucre;loadCode = getData(soucre);
                        break;
                }
            }
        }
    }
}
