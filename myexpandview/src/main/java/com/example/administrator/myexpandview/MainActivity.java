package com.example.administrator.myexpandview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yff.view.ExpandItemView;
import com.yff.view.ExpandView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {


    private ExpandView expandedMenu;
    private Map<String,ExpandItemView> expandItemView;
    private String[] mColleages = { "A学校", "B学校", "C学校", "D学校", "E学校", "F学校", "G学校", "H学校", "I学校", "J学校" };
    private String[] mDepartments = { "A系", "B系", "C系", "D系", "E系", "F系", "G系", "H系" , "I系" };
    private String[] mProfessions = { "A专业", "B专业", "C专业" , "D专业" , "E专业" , "F专业" };
    private String[] mClasses = { "A班", "B班", "C班", "D班" , "E班" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();


        expandItemView=new HashMap<>();

        expandItemView.put("A",new ExpandItemView(this,"学校",Arrays.asList(mColleages)));
        expandItemView.put("B",new ExpandItemView(this,"院系",Arrays.asList(mDepartments)));
        expandItemView.put("C",new ExpandItemView(this,"专业",Arrays.asList(mProfessions)));
        expandItemView.put("D",new ExpandItemView(this,"班级",Arrays.asList(mClasses)));
        expandedMenu.initViews(new ArrayList<>(expandItemView.values()));
    }



    public void click(View view){
        Toast.makeText(this,"TTTTT",Toast.LENGTH_SHORT).show();
    }



    private void assignViews() {
        expandedMenu = (ExpandView) findViewById(R.id.expanded_menu);
    }

}
