package com.xingmei.administrator.xingmei.onclick;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.tencent.smtt.sdk.TbsVideo;
import com.xingmei.administrator.xingmei.activity.MyWebViewActivity;

public class ListClick implements AdapterView.OnItemClickListener {
    private String url = "https://m.baidu.com/?from=1086k";
    private Context context;
    private  int type;

    public ListClick(Context context,int type){
        this.context = context;
        this.type = type;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (type == 0){
            startVideo();
        }else  if (type == 1){
            Intent intent = new Intent(context,MyWebViewActivity.class);
            intent.putExtra("url",url);
            context.startActivity(intent);
        }

    }

    protected void startVideo(){
        if (TbsVideo.canUseTbsPlayer(context)){
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);
           //true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);
            //false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);
            //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
            TbsVideo.openVideo(context,"http://aass-10009076.cossh.myqcloud.com/video.mp4",data);
        }
    }
}
