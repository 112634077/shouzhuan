package com.xingmei.administrator.xingmei;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xingmei.administrator.xingmei.BottomNavigationPackage.BNVEffect;
import com.xingmei.administrator.xingmei.fragment.HomeFragment;
import com.xingmei.administrator.xingmei.fragment.JournalismFragment;
import com.xingmei.administrator.xingmei.fragment.PersonFragment;
import com.xingmei.administrator.xingmei.fragment.SquareFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private int lastShowFragment = 0;

    protected Fragment homeFragment;
    protected Fragment journalismFragment;
    protected Fragment squareFragment;
    protected Fragment personFragment;
    public static Context context = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()

    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home://首页
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;

                case R.id.navigation_journalism://新闻
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;

                case R.id.navigation_square://广场
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;

                case R.id.navigation_person://个人
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
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
        homeFragment = new HomeFragment();//首页
        journalismFragment = new JournalismFragment();//新闻
        squareFragment = new SquareFragment();//广场
        personFragment = new PersonFragment();//个人

        fragments = new Fragment[]{homeFragment,journalismFragment,squareFragment,personFragment};

        lastShowFragment = 0;
        getSupportFragmentManager() .beginTransaction() .add(R.id.message, homeFragment) .show(homeFragment) .commit();
    }

}
