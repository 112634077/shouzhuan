package com.xingmei.administrator.xingmei.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.xingmei.administrator.xingmei.networktools.HttpConnection;
import com.xingmei.administrator.xingmei.networktools.NetPicture;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyCachedThreadPool{
    private Context context;
    private String url;
    private Map params;
    private ExecutorService mCachedThredPool;
    private MyThreadTask myThreadTask;
    private Handler threadHandler;

    /**
     * @param context Context上下文
     * @param url 请求地址
     * @param params 请求参数
     */
    //构造参数输入地址和获取字段参数
    public MyCachedThreadPool(Context context,String url, Map params){
        this.context = context;
        this.url = url;
        this.params = params;
        mCachedThredPool = Executors.newCachedThreadPool();
        myThreadTask = new MyThreadTask();
    }

    /**
     * @param threadPool 设置threadpool接口
     */
    public void setOnThreadPool(ThreadPool threadPool){
        myThreadTask.setOnThreadPool(threadPool);
    }

    /**
     * 需要在setOnThreadPool方法之后调用
     */
    //json解析封装  1 解析jsonarray数组  2解析jsonobject对象  3 解析json字段 字符类型和值
    public void cachedThredPool(){
        try {
            mCachedThredPool.execute(myThreadTask);
        }catch (Exception e){e.printStackTrace();}
        finally {
            mCachedThredPool.shutdown();
        }
    }

    public class MyThreadTask implements Runnable,ThreadPool{
        private ThreadPool threadPool = null;

        @Override
        public void run() {
//           sendEmptyMessage(0);
            String result;
            getThreadPool().onExecute();
            result = getRequest1(NetPicture.XINURL,params,"result","error_code");
//            if (result.contains("错误码") || TextUtils.isEmpty(result))
            System.out.println("MyCachedThreadPool============="+result);
            getThreadPool().onInBackground(result);
            getThreadPool().onCancelled();

            Looper.prepare();
//            threadHandler = new Handler(){
//                @Override
//                public void handleMessage(Message msg) {
//                    Fragment reference = (Fragment) mWeakReference.get();
//                    if (reference == null) { // the referenced object has been cleared
//                        return;
//                    }
//                }
//            };
            Looper.loop();
        }

        private void setOnThreadPool(ThreadPool threadPool){
            this.threadPool = threadPool;
        }
        private ThreadPool getThreadPool(){
            if (threadPool == null)
                //如果为空 直接返回一个对象 里面的方法不执行
                return this;
            else return threadPool;
        }
        /**
         * @param url 请求地址
         * @param params 请求参数
         * @param results 解析result的字段
         * @param error_code 解析error_code状态字段  0为成功返回数据
         * @return
         */
        private String getRequest1(String url,Map params,String results,String error_code){

            String result ;
            try {
                result = HttpConnection.net(url, params, "GET");
                JSONObject object = new JSONObject(result);
                if(object.getInt("error_code")==0){
                    result = object.get("result").toString();
                    return result;
                }else{
                    result = "错误码：" +object.get(error_code).toString();
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onExecute() {

        }

        @Override
        public void onInBackground(String result) {

        }

        @Override
        public void onCancelled() {

        }

    }
}
