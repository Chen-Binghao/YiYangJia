package com.example.fitmvp.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.RecipeContract;
import com.example.fitmvp.presenter.RecipePresenter;
import com.example.fitmvp.presenter.RecordPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.view.activity.MainActivity;
import com.example.fitmvp.view.activity.RecordDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class FragmentRecipe extends BaseFragment<RecipePresenter> implements RecipeContract.View {
/*    @Bind(R.id.cal_input)
    EditText calinput;
    @Bind(R.id.pro_input)
    EditText proinput;
    @Bind(R.id.fat_input)
    EditText fatinput;
    @Bind(R.id.ch2o_input)
    EditText ch2oinput;
    @Bind(R.id.button_gen_recipe)
    Button gen_recipe;
    @Bind(R.id.gen_progress)
    Button progress;*/

    private LinearLayout gen_progress;
    private LinearLayout gen_recipe;
    private LinearLayout recipe;
    private Button button_gen;
    private ProgressBar progressbar;

    private TextView caltext;
    private TextView fattext;
    private TextView protext;
    private TextView ch2otext;
    private TextView calrecipe;
    private TextView fatrecipe;
    private TextView prorecipe;
    private TextView ch2orecipe;
    private TextView foodcal;
    private TextView foodpro;
    private TextView foodfat;
    private TextView foodch2o;
    private TableLayout table;
    private TableRow tablerow[]=new TableRow[5];
    private TextView foodname[]=new TextView[5];
    private TextView foodweight[]=new TextView[5];
    private Context mContext = null;


    private String phone;
    private String oldcal;
    private String oldfat;
    private String oldpro;
    private String oldch2o;
    private String calres;
    private String fatres;
    private String prores;
    private String ch2ores;


    @Override
    protected Integer getLayoutId() {
        return R.layout.recipe_all;
    }

    @Override
    protected RecipePresenter loadPresenter() {
        return new RecipePresenter();
    }

    @Override
    protected void initView() {
        //gen_progress=view.findViewById(R.id.l_progress);
        //gen_recipe=view.findViewById(R.id.l_gen);
        mContext=getActivity();
        button_gen=view.findViewById(R.id.button_gen_recipe);
        progressbar=view.findViewById(R.id.gen_progress);
        caltext=view.findViewById(R.id.cal_input);
        fattext=view.findViewById(R.id.fat_input);
        protext=view.findViewById(R.id.pro_input);
        ch2otext=view.findViewById(R.id.ch2o_input);
        recipe=view.findViewById(R.id.l_recipe);
        calrecipe=view.findViewById(R.id.cal_recipe);
        prorecipe=view.findViewById(R.id.pro_recipe);
        fatrecipe=view.findViewById(R.id.fat_recipe);
        ch2orecipe=view.findViewById(R.id.ch2o_recipe);
        table=view.findViewById(R.id.table);
        tablerow[0]=view.findViewById(R.id.table1);
        tablerow[1]=view.findViewById(R.id.table2);
        tablerow[2]=view.findViewById(R.id.table3);
        tablerow[3]=view.findViewById(R.id.table4);
        tablerow[4]=view.findViewById(R.id.table5);
        foodname[0]=view.findViewById(R.id.foodname1);
        foodname[1]=view.findViewById(R.id.foodname2);
        foodname[2]=view.findViewById(R.id.foodname3);
        foodname[3]=view.findViewById(R.id.foodname4);
        foodname[4]=view.findViewById(R.id.foodname5);
        foodweight[0]=view.findViewById(R.id.foodweight1);
        foodweight[1]=view.findViewById(R.id.foodweight2);
        foodweight[2]=view.findViewById(R.id.foodweight3);
        foodweight[3]=view.findViewById(R.id.foodweight4);
        foodweight[4]=view.findViewById(R.id.foodweight5);
        showButton();
    }

    private void showButton() {
        progressbar.setVisibility(View.INVISIBLE);
        button_gen.setVisibility(View.VISIBLE);
        recipe.setVisibility(View.INVISIBLE);
        table.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        SpUtils spUtils = new SpUtils();
        phone = (String)spUtils.get("phone","");
        oldcal = (String)spUtils.get("cal","");
        oldpro = (String)spUtils.get("pro","");
        oldfat = (String)spUtils.get("fat","");
        oldch2o = (String)spUtils.get("ch2o","");

        // 输入框显示（旧）营养信息
        caltext.setText(oldcal);
        protext.setText(oldpro);
        fattext.setText(oldfat);
        ch2otext.setText(oldch2o);
    }

    @Override
    protected void initListener() {

        button_gen.setOnClickListener(this);
        for(int i=0;i<5;i++)
        {
            tablerow[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_gen_recipe:
                getrecipe(view);
                break;
            case R.id.table1:
            case R.id.table2:
            case R.id.table3:
            case R.id.table4:
            case R.id.table5:
                showPopupWindow(view);
                break;
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.pop_window, null);
        // 设置按钮的点击事件
        /*Button button = (Button) contentView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "button is pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.shape_corner));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    private void getrecipe(View view) {
        button_gen.setVisibility(View.INVISIBLE);
        progressbar.setVisibility(View.VISIBLE);
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        ////////////获取数据///////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        calres=1+"";
        prores=2+"";
        fatres=3+"";
        ch2ores=4+"";
        int foodnum = 3;
        String[] foodnamestring={"米饭","排骨","青菜"};
        int[] foodweightnum={1,2,3};

        progressbar.setVisibility(View.INVISIBLE);
        button_gen.setVisibility(View.VISIBLE);
        recipe.setVisibility(View.VISIBLE);
        table.setVisibility(View.VISIBLE);


        calrecipe.setText(calres);
        prorecipe.setText(prores);
        fatrecipe.setText(fatres);
        ch2orecipe.setText(ch2ores);


        for(int i=0;i<foodnum;i++)
        {
            tablerow[i].setVisibility(View.VISIBLE);
            foodname[i].setText(foodnamestring[i]);
            foodweight[i].setText(foodnamestring[i]);
        }

        for(int i=foodnum;i<5;i++)
        {
            tablerow[i].setVisibility(View.INVISIBLE);
        }



    }


}
