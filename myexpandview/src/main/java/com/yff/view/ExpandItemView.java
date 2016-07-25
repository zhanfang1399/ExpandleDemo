package com.yff.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myexpandview.R;

import java.util.ArrayList;
import java.util.List;

import adapter.MyBaseAdapter;
import adapter.ViewHolder;

/**
 * expand item
 * Created by Administrator on 2016/7/7.
 */
public class ExpandItemView extends LinearLayout {


    /**
     * 显示在toggleButton的标题文字
     */
    public String mTitle;
    /**
     * 筛选的数据内容
     */
    private List<String> mGridviewDatas;


    public ExpandItemView(Context context) {
        this(context, null);
    }

    public ExpandItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExpandItemView(Context context, String mTitle, List<String> mGridviewDatas) {
        this(context);
        setTitle(mTitle);
        this.mGridviewDatas = mGridviewDatas;
        init();
    }


    private void init() {
        setBackgroundColor(getResources().getColor(android.R.color.white));
        //将布局添加进去
        LayoutInflater.from(getContext()).inflate(R.layout.expand_item_layout, this, true);
        setOrientation(LinearLayout.VERTICAL);
        //展示要筛选的数据
        GridView mGridView = (GridView) findViewById(R.id.gridview);
        //底部按钮
        LinearLayout mBottomBtn = (LinearLayout) findViewById(R.id.btn_all);
        //自定义的adapter   万能适配器
        MyBaseAdapter<String> adapter = new MyBaseAdapter<String>(mGridviewDatas, R.layout.gridview_item, getContext()) {
            @Override
            protected void convert(ViewHolder viewHolder, final String s) {
                View view = viewHolder.getmContentView();
                TextView tv = (TextView) view.findViewById(R.id.tv);
                tv.setText(s);
                tv.setTextColor(Color.LTGRAY);

            }
        };

        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnExpandItemClick != null) {
                    mOnExpandItemClick.onItemClick(position);
                }
            }
        });

        /**
         * 底部按钮的回调
         */
        mBottomBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnExpandItemClick != null) {
                    mOnExpandItemClick.onBottomClick();
                }
            }
        });

    }


    public String getTitle() {
        return mTitle == null ? new String() : mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public List<String> getmGridviewDatas() {
        return mGridviewDatas == null ? new ArrayList<String>() : mGridviewDatas;
    }

    public void setmGridviewDatas(List<String> mGridviewDatas) {
        this.mGridviewDatas = mGridviewDatas;
    }


    /**
     * 累计子类的高度作为自身的高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        int desireWidth = MeasureSpec.getSize(widthMeasureSpec);
        int desireHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            desireHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(desireWidth, desireHeight);
    }

    /**
     * 点击item事件回调给监听者
     *
     * @author rander
     */
    public interface OnExpandItemClick {
        void onItemClick(int position);

        void onBottomClick();
    }

    private OnExpandItemClick mOnExpandItemClick;

    public void setOnExpandItemClick(OnExpandItemClick onExpandItemClick) {
        this.mOnExpandItemClick = onExpandItemClick;
    }
}
