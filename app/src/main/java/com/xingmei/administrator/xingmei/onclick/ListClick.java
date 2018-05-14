package com.xingmei.administrator.xingmei.onclick;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.xingmei.administrator.xingmei.activity.MyWebViewActivity;

public class ListClick implements AdapterView.OnItemClickListener {
    private String url = "https://m.baidu.com/?from=1086k";
    private Context context;

    public ListClick(Context context){
        this.context = context;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context,MyWebViewActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
}
