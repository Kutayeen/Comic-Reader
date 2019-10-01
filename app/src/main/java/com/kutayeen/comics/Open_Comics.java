package com.kutayeen.comics;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import me.relex.circleindicator.CircleIndicator;

public class Open_Comics extends AppCompatActivity {
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private Mypager myPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.open_comics);
        String dir= (String) getIntent().getSerializableExtra("dir");
        myPager = new Mypager(this,dir);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myPager);

    }

}
