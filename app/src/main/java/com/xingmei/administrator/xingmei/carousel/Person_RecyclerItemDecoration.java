package com.xingmei.administrator.xingmei.carousel;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 创建时间 2018/3/14.
 */

public class Person_RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private int itemHeight;

    /*
    * @dividerHeight item间隔高度
    * @itemHeight  item的位置
     */
    public Person_RecyclerItemDecoration(int dividerHeight,int itemHeight){
        this.dividerHeight = dividerHeight;
        this.itemHeight = itemHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        if (parent.getChildCount() == itemHeight){
//            outRect.top = dividerHeight;
//        }
          for (int divider = 0; divider < parent.getChildCount(); divider++){
              if (itemHeight == divider){
                  outRect.top = dividerHeight;
              }
          }
    }
}
