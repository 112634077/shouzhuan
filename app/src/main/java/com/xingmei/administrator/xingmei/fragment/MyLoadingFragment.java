package com.xingmei.administrator.xingmei.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.carousel.LoadingFrame;

public abstract class MyLoadingFragment extends Fragment {
    /**
     * 控件是否显示
     */
    private boolean isViewCreated;
    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;
    private static LoadingFrame mLoadingFrame;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //可见，并且没有加载过

        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted){
            isLoadDataCompleted = true;
            loadData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            mLoadingFrame = new LoadingFrame(getActivity()) {
                @Override
                public View onSuccessView() {
                    return MyLoadingFragment.this.onSuccessView();
                }

                @Override
                public int onLoad() {
                    return MyLoadingFragment.this.onLoad();
                }
            };
        isViewCreated = true;
        mLoadingFrame.show();
        return mLoadingFrame;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getUserVisibleHint()){
            isLoadDataCompleted = true;
            if (onSuccessView() != null){
                loadInit(onSuccessView());
                loadData();
            }

        }
    }

    protected abstract View onSuccessView();

    protected abstract int onLoad();

    /**
     * 子类实现加载数据的方法
     */
    protected abstract void loadData();

    /**
     * 子类实现初始化控件的方法
     */
    protected abstract void loadInit(View view);


}
