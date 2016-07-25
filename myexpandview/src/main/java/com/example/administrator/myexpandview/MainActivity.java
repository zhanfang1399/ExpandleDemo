package com.example.administrator.myexpandview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yff.view.ExpandItemView;
import com.yff.view.ExpandView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {


    private ExpandView expandedMenu;
    private List<ExpandItemView> expandItemView;
    private String[] mColleages = { "最近一周", "最近一个月", "最近三个月" };
    private String[] mDepartments = { "体现", "营收", "付费推广", "商家粮票"};
    private String[] mProfessions = { "收入", "支出"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();


        expandItemView=new ArrayList<>();


        ExpandItemView itemView1=new ExpandItemView(this,"全部时间",Arrays.asList(mColleages));
        ExpandItemView itemView2=new ExpandItemView(this,"全部类型",Arrays.asList(mDepartments));
        ExpandItemView itemView3=new ExpandItemView(this,"收支明细",Arrays.asList(mProfessions));
        expandItemView.add(itemView1);
        expandItemView.add(itemView2);
        expandItemView.add(itemView3);
        expandedMenu.initViews(expandItemView);

        itemView1.setOnExpandItemClick(new ExpandItemView.OnExpandItemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"itemView1--item"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBottomClick() {
                expandedMenu.hidePopwind();
                Toast.makeText(MainActivity.this,"itemView1--bottom",Toast.LENGTH_SHORT).show();
            }
        });


        itemView2.setOnExpandItemClick(new ExpandItemView.OnExpandItemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"itemView2--item"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBottomClick() {
                expandedMenu.hidePopwind();
                Toast.makeText(MainActivity.this,"itemView2--bottom",Toast.LENGTH_SHORT).show();
            }
        });


        itemView3.setOnExpandItemClick(new ExpandItemView.OnExpandItemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"itemView3--item"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBottomClick() {
                expandedMenu.hidePopwind();
                Toast.makeText(MainActivity.this,"itemView3--bottom",Toast.LENGTH_SHORT).show();
            }
        });


    }



    public void click(View view){
        Toast.makeText(this,"TTTTT",Toast.LENGTH_SHORT).show();
    }



    private void assignViews() {
        expandedMenu = (ExpandView) findViewById(R.id.expanded_menu);
    }

}
