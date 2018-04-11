package com.example.test02_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radio_group;
    private ArrayList<Fragment> list;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radio_group = findViewById(R.id.radio_group);
        list = new ArrayList<>();
        list.add(new Fragment01());
        list.add(new Fragment02());
        list.add(new Fragment03());
        list.add(new Fragment04());
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.main_fl,list.get(0)).commit();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch(i){
                            case R.id.radio_btn01:
                            supportFragmentManager.beginTransaction().replace(R.id.main_fl,list.get(0)).commit();
                            break;
                            case R.id.radio_btn02:
                                supportFragmentManager.beginTransaction().replace(R.id.main_fl,list.get(1)).commit();
                            break;
                        case R.id.radio_btn03:
                            supportFragmentManager.beginTransaction().replace(R.id.main_fl,list.get(2)).commit();
                            break;
                        case R.id.radio_btn04:
                            supportFragmentManager.beginTransaction().replace(R.id.main_fl,list.get(3)).commit();
                            break;

                        }
            }
        });
    }
}
