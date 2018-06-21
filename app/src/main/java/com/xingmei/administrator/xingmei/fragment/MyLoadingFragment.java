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
    protected LoadingFrame mLoadingFrame;
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
        mLoadingFrame.show();
        return mLoadingFrame;
    }

    protected abstract View onSuccessView();

    protected abstract int onLoad();

}
