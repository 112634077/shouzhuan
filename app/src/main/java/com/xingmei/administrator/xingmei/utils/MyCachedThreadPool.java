package com.xingmei.administrator.xingmei.utils;

import android.content.Context;
import android.os.Message;

import com.xingmei.administrator.xingmei.networktools.HttpConnection;
import com.xingmei.administrator.xingmei.networktools.NetPicture;

import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyCachedThreadPool{
    private Context context;
    private String url;
    private Map params;
    private ExecutorService mCachedThredPool;
    private MyThreadTask myThreadTask;
    private NotLeakHandler threadHandler;
    private OnThreadTask onThreadTask;

    /**
     * @param context Context上下文
     * @param url 请求地址
     * @param params 请求参数
     */
    //构造参数输入地址和获取字段参数
    public MyCachedThreadPool(Context context,String url, Map params,NotLeakHandler threadHandler){
        this.context = context;
        this.url = url;
        this.params = params;
        mCachedThredPool = Executors.newCachedThreadPool();
        myThreadTask = new MyThreadTask();
        this.threadHandler = threadHandler;
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

    public void setOnThreadTask(OnThreadTask onThreadTask){
        this.onThreadTask = onThreadTask;
    }

    private OnThreadTask getOnThreadTask(){
        return onThreadTask;
    }

    public interface OnThreadTask{
        /**
         * 这个方法是用来解析json
         * @param result json数据
         */
        int onTask(String result);
    }

    public class MyThreadTask implements Runnable{

        @Override
        public void run() {
            //操作前
            setMessageWhat(0);
            String result;
            result = getRequest1(NetPicture.XINURL,params,"result","error_code");

            if (getOnThreadTask() != null){
                //数据获取完毕，解析json并通知主线程
                int state = getOnThreadTask().onTask(result);
                if (state == 0)
                    setMessageWhat(1);
                else setMessageWhat(2);
            }else setMessageWhat(2);//数据获取失败，通知主线程


//            setMessageWhat(1);
//            message.what = 1;
//            Bundle bundle = new Bundle();
//            bundle.putString("result",result);
//            message.setData(bundle);
//            threadHandler.sendMessage(message);

        }

        private void setMessageWhat(int what){
            Message message = new Message();
            message.what = what;
            threadHandler.sendMessage(message);
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

    }
}
