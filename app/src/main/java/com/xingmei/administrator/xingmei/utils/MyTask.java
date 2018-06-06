package com.xingmei.administrator.xingmei.utils;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.xingmei.administrator.xingmei.adapter.RecyclerViewAdapter;
import com.xingmei.administrator.xingmei.networktools.HttpConnection;
import com.xingmei.administrator.xingmei.networktools.NetPicture;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**JSONObject obj = new JSONObject();
 obj.put("name", "John");
 obj.put("sex", "male");
 obj.put("age", 22);
 obj.put("is_student", true);
 obj.put("hobbies", new String[] {"hiking", "swimming"});
 //调用toString()方法可直接将其内容打印出来
 System.out.println(obj.toString());
 */

/**
 * AsyncTask 三个参数 1 启动任务执行的输入参数  2 后台任务执行的进度  3 后台计算结果的类型
 */
public class MyTask extends AsyncTask<String,Integer,List<MoreTypeBean>> {

    private RecyclerViewAdapter recyclerViewAdapter;
    private String type;
    private List<MoreTypeBean> moreTypeBeansList;

    public MyTask(RecyclerViewAdapter recyclerViewAdapter,String type,List<MoreTypeBean> moreTypeBeanList){
        this.recyclerViewAdapter = recyclerViewAdapter;
        this.type = type;
        this.moreTypeBeansList = moreTypeBeanList;
    }
    //在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
    @Override
    protected List<MoreTypeBean> doInBackground(String... strings) {
        //调用publishProgress公布进度,最后onProgressUpdate方法将被执行
//        publishProgress((int) ((count / (float) total) * 100));
        System.out.println("MyTask ======================"+strings[0].toString());
        Map<String,Object> params = new HashMap();
        params.put("key", NetPicture.XINAPPKEY);
        params.put("type",type);
        String result = getRequest1(strings[0],params);
        return getJsonArray(result);
    }

    //在调用publishProgress(Progress... values)时，此方法被执行，直接将进度信息更新到UI组件上
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    //当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
    @Override
    protected void onPostExecute(List<MoreTypeBean> s) {
        super.onPostExecute(s);
        System.out.println("s===="+s);
//        recyclerViewAdapter.notifyDataSetChanged();
    }

    //onCancelled方法用于在取消执行中的任务时更改UI
    @Override
    protected void onCancelled(List<MoreTypeBean> s) {
        super.onCancelled(s);
    }

    public String getRequest1(String url,Map params){
        String result =null;
        try {
            result = HttpConnection.net(url, params, "POST");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                result = object.get("result").toString();
                return result;
            }else{
                result = object.get("error_code").toString()+":"+object.get("reason").toString();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //解析json数组
    private List<MoreTypeBean> getJsonArray(String result){
        try {
//            List<MoreTypeBean> moreTypeBeansList = new ArrayList<>();
            JSONObject object = new JSONObject(result);
            object.has("");//判断json  key是否存在

            JSONArray jsonArray = object.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++ ){
                MoreTypeBean moreTypeBean = new MoreTypeBean();
                moreTypeBean.setTitleString(jsonArray.getJSONObject(i).getString("title"));
                moreTypeBean.setSource(jsonArray.getJSONObject(i).getString("author_name"));
                moreTypeBean.setContentURL(jsonArray.getJSONObject(i).getString("url"));


                String pic = jsonArray.getJSONObject(i).getString("thumbnail_pic_s");
                String pic2 = jsonArray.getJSONObject(i).getString("thumbnail_pic_s02");
                String pic3 = jsonArray.getJSONObject(i).getString("thumbnail_pic_s03");

                System.out.println("IconURL ========"+pic);
                List<String> icon = new ArrayList<>();
                //一张图片都没有
                if(TextUtils.isEmpty(pic)){
                    moreTypeBean.setType(0);
                    moreTypeBeansList.add(moreTypeBean);
                    System.out.println("TextUtils ========"+pic);
                }else if (!TextUtils.isEmpty(pic) && TextUtils.isEmpty(pic3)){//有图片一没有图片二
                    icon.add(pic);
                    moreTypeBean.setType(1);
                    moreTypeBeansList.add(moreTypeBean);
                }else {
                    icon.add(pic);
                    icon.add(pic2);
                    icon.add(pic3);
                    moreTypeBean.setType(2);
                    moreTypeBeansList.add(moreTypeBean);
                }
                return moreTypeBeansList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
