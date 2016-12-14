package com.incasa.incasa;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.incasa.incasa.fragments.FragmentPagerAdapter;

import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_Pager);
        mTabLayout.setTabMode(mTabLayout.MODE_SCROLLABLE);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),getResources().getStringArray(R.array.titulos_tab)));

        mTabLayout.setupWithViewPager(mViewPager);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
