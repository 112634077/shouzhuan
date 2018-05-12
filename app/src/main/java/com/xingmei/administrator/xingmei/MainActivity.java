package com.xingmei.administrator.xingmei;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.xingmei.administrator.xingmei.BottomNavigationPackage.BNVEffect;
import com.xingmei.administrator.xingmei.fragment.ImageFragment;
import com.xingmei.administrator.xingmei.fragment.MallFragment;
import com.xingmei.administrator.xingmei.fragment.MonetizationFragment;
import com.xingmei.administrator.xingmei.fragment.PersonFragment;
import com.xingmei.administrator.xingmei.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private int lastShowFragment = 0;
    protected Fragment imageFragment;
    protected Fragment videoFragment;
    protected Fragment mallFragment;
    protected Fragment monetizationFragment;
    protected Fragment personFragment;
    public static Context context = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()

    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_image://图片
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_videocam://视频
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_mall://商城
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
                case R.id.navigation_monetization://赚钱
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
                    }
                    return true;
                case R.id.navigation_person://个人
                    if (lastShowFragment != 4) {
                        switchFrament(lastShowFragment, 4);
                        lastShowFragment = 4;
                    }
                    return true;
            }


            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BNVEffect.disableShiftMode(navigation);

        initFragments();
    }

    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.message, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();

    }

    private void initFragments() {
        imageFragment = new ImageFragment();
        videoFragment = new VideoFragment();
        mallFragment = new MallFragment();
        monetizationFragment = new MonetizationFragment();
        personFragment = new PersonFragment();

        fragments = new Fragment[]{imageFragment,videoFragment,mallFragment,monetizationFragment,personFragment};

        lastShowFragment = 0;
        getSupportFragmentManager() .beginTransaction() .add(R.id.message, imageFragment) .show(imageFragment) .commit();
    }

}
