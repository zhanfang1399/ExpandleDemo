package com.yff.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.example.administrator.myexpandview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * expand view
 * Created by Administrator on 2016/7/7.
 */
public class ExpandView extends LinearLayout implements ExpandItemView.OnExpandItemClick {

    /**
     * 记录选中的ToggleButton
     */
    private ToggleButton mSelectToggleBtn;
    /**
     * 筛选
     */
    private List<View> mToggleButtons = new ArrayList<>();
    /**
     * 筛选项集合
     */
    private List<View> mPopupviews;

    /**
     * popupwindow展示的宽
     */
    private int mDisplayWidth;
    /**
     * popupwindow展示的高
     */
    private int mDisplayHeight;
    /**
     * 筛选内容用PopupWindow弹出来
     */
    private PopupWindow mPopupWindow;
    private Context mContext;

    /**
     * toggleButton正常的字体颜色
     */
    int mNormalTextColor = getResources().getColor(R.color.grey);
    /**
     * toggleButton被选中的类型字体颜色
     */
    int mSelectTextColor = getResources().getColor(R.color.themeYellow);


    public ExpandView(Context context) {
        this(context, null);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        mDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        mDisplayHeight = getResources().getDisplayMetrics().heightPixels;
        mContext = getContext();
        setBackgroundResource(R.mipmap.choosearea_bg_right);
    }


    /**
     * 初始化数据和布局，做的工作如下：
     * 1.根据筛选项的数量，动态增加上面一排ToggleButton
     * 2.设置每一个ToggleButton的监听事件
     * 3.toggleButton.setTag(i)这一句非常重要，我们取View数据都是根据这个tag取的
     * 4.
     *
     * @param views views
     */
    public void initViews(List<ExpandItemView> views) {
        mPopupviews = new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < views.size(); i++) {
            ExpandItemView itemView = views.get(i);
            itemView.setOnExpandItemClick(this);

            RelativeLayout relativeLayout = new RelativeLayout(mContext);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeLayout.addView(itemView, params);
            mPopupviews.add(relativeLayout);


            View view =inflater.inflate(R.layout.expand_view,this,false);
            final ToggleButton toggleButton=(ToggleButton)view.findViewById(R.id.toggle);

            toggleButton.setText(itemView.getTitle());
            mToggleButtons.add(toggleButton);
            addView(view);
            toggleButton.setTag(i);
            toggleButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //记录选中的toggleBtn
                    mSelectToggleBtn = toggleButton;
                    showPopWindow();
                }
            });

            /**
             * 点击popupwindow外部，就隐藏popupwindow,这个r是点击事件包裹了一个ExpandleItemView
             * 如果用户所点之处为ExpandleItemView所在范围，点击事件由ExpandleItemView，如果点到
             * ExpandleItemView外面，则有r处理，处理方式就是收缩
             */
            relativeLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hidePopwind();
                }
            });

        }

    }


    /**
     * 关闭popWindow
     */
    public void hidePopwind() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }

        if (mSelectToggleBtn != null) {
            mSelectToggleBtn.setTextColor(mNormalTextColor);
            mSelectToggleBtn.setChecked(false);
        }
    }


    /**
     * 显示popWindow
     */
    public void showPopWindow() {
        if (null == mPopupWindow) {
            mPopupWindow = new PopupWindow(mPopupviews.get((int) mSelectToggleBtn.getTag()), mDisplayWidth, mDisplayHeight);
            //关闭的时候进行监听事件
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (mSelectToggleBtn != null) {
                        mSelectToggleBtn.setTextColor(mNormalTextColor);
                        mSelectToggleBtn.setChecked(false);
                    }
                }
            });

            mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        } else {
            mPopupWindow.setContentView(mPopupviews.get((Integer) mSelectToggleBtn.getTag()));
        }


        //如果当前正在show则关闭
        if (mPopupWindow.isShowing()) {
            hidePopwind();
        } else {
            //显示的时候设为选种颜色
            mSelectToggleBtn.setTextColor(mSelectTextColor);
            mPopupWindow.showAsDropDown(mToggleButtons.get(0), 0, 0);
        }


    }


    /**
     * Item项选中的回调 注意Tag的使用 筛选项视图是根据tag拿的，因为mSelectToggleBtn的tag就是视图的索引
     * mSelectToggleBtn显示筛选的内容
     */
    @Override
    public void onItemClick(int position) {
        hidePopwind();
        if (null != mSelectToggleBtn) {
            int selectBtnIndex = (int) mSelectToggleBtn.getTag();
            View layout = ((RelativeLayout) mPopupviews.get(selectBtnIndex)).getChildAt(0);
            String text = ((ExpandItemView) layout).getmGridviewDatas().get(position);
            mSelectToggleBtn
                    .setText(text);

        }

    }

    @Override
    public void onBottomClick() {
        hidePopwind();
        if (null != mSelectToggleBtn) {
            int selectIndex = (int) mSelectToggleBtn.getTag();
            View view = ((RelativeLayout) mPopupviews.get(selectIndex)).getChildAt(0);
            String text = ((ExpandItemView) view).getTitle();
            mSelectToggleBtn.setText(text);
        }

    }
}
