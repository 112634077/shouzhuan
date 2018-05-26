package com.xingmei.administrator.xingmei.onclick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.tbs.video.interfaces.a;
import com.xingmei.administrator.xingmei.activity.MyVideoActivity;
import com.xingmei.administrator.xingmei.activity.MyWebViewActivity;

public class ListClick implements AdapterView.OnItemClickListener {
    private String url = "http://app.html5.qq.com/navi/index";
    private Context context;
    private  int type;

    public ListClick(Context context,int type){
        this.context = context;
        this.type = type;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (type == 0){
            setIntent(MyVideoActivity.class,url);
        }else  if (type == 1){
           setIntent(MyWebViewActivity.class,url);
        }

    }

    private void setIntent(Class<?> cls,String url){
        Intent intent = new Intent(context,cls);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    protected void startVideo(){
        if (TbsVideo.canUseTbsPlayer(context)){
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);
           //true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);
            //false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);
            //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            TbsVideo.openVideo(context,"http://aass-10009076.cossh.myqcloud.com/video.mp4",data);

        }
    }

    protected  void startYunVideo(){
        if (TbsVideo.canUseYunbo(context)){
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);
            //true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);
            //false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);

            TbsVideo.openYunboVideo(context, "http://aass-10009076.cossh.myqcloud.com/video.mp4", data, (a)null) ;
        }else {
            Toast.makeText(context,"TbsVieo.canUseYunbo == null",Toast.LENGTH_LONG).show();
        }
    }
}
