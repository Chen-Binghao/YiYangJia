package com.example.fitmvp.view.activity;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.presenter.SettingPresenter;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.dialog.GenderDialog;
import com.example.fitmvp.view.fragment.dialog.MyDatePickerDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.POST;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    @Bind(R.id.info_phone)
    TextView infoPhone;
    @Bind(R.id.info_nickname)
    EditText infoNickname;
    @Bind(R.id.info_birthday)
    EditText infoBirthday;
    @Bind(R.id.info_gender)
    EditText infoGender;
    @Bind(R.id.info_height)
    EditText infoHeight;
    @Bind(R.id.info_weight)
    EditText infoWeight;
    /*@Bind(R.id.cal_input)
    EditText calinput;
    @Bind(R.id.pro_input)
    EditText proinput;
    @Bind(R.id.fat_input)
    EditText fatinput;
    @Bind(R.id.ch2o_input)
    EditText ch2oinput;*/
    @Bind(R.id.button_update_info)
    Button update;
    @Bind(R.id.save_progress)
    ProgressBar progress;

    private EditText calinput;
    private EditText proinput;
    private EditText fatinput;
    private EditText ch2oinput;

    private String phone;
    private String oldNickname;
    private String oldBirthday;
    private String oldGender;
    private String oldHeight;
    private String oldWeight;
    private String oldcal;
    private String oldpro;
    private String oldfat;
    private String oldch2o;

    private String newNickname;
    private String newBirthday;
    private String newGender;
    private String newHeight;
    private String newWeight;
    private String newcal;
    private String newpro;
    private String newfat;
    private String newch2o;

    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("个人信息设置");
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        infoBirthday.setShowSoftInputOnFocus(false);
        infoBirthday.setKeyListener(null);
        infoGender.setShowSoftInputOnFocus(false);
        infoGender.setKeyListener(null);
        calinput=view.findViewById(R.id.cal_input);
        proinput=view.findViewById(R.id.pro_input);
        fatinput=view.findViewById(R.id.fat_input);
        ch2oinput=view.findViewById(R.id.ch2o_input);
        showButton();
    }

    @Override
    protected SettingPresenter loadPresenter() {
        return new SettingPresenter();
    }

    @Override
    public void initData() {
        SpUtils spUtils = new SpUtils();
        phone = (String)spUtils.get("phone","");
        oldNickname = (String)spUtils.get("nickname","");
        oldBirthday = (String)spUtils.get("birthday","");
        oldGender = (String)spUtils.get("gender","");
        oldHeight = (String)spUtils.get("height","");
        oldWeight = (String)spUtils.get("weight","");
        oldcal = (String)spUtils.get("cal","");
        oldpro = (String)spUtils.get("pro","");
        oldfat = (String)spUtils.get("fat","");
        oldch2o = (String)spUtils.get("ch2o","");


        // 显示手机号
        infoPhone.setText(phone);
        // 显示昵称
        infoNickname.setText(oldNickname);
        // 显示生日
        infoBirthday.setText(oldBirthday);
        // 显示性别
        infoGender.setText(oldGender);
        // 显示身高
        infoHeight.setText(oldHeight);
        // 显示体重
        infoWeight.setText(oldWeight);
        calinput.setText(oldcal);
        proinput.setText(oldpro);
        ch2oinput.setText(oldch2o);
        fatinput.setText(oldfat);
    }

    @Override
    protected void initListener() {
        update.setOnClickListener(this);
        infoBirthday.setOnClickListener(this);
        infoGender.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_update_info:
                otherViewClick(view);
                break;
            case R.id.info_birthday:
                showDatePicker(view);
                break;
            case R.id.info_gender:
                showGenderChoice(view);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        // 隐藏按键
        double cal= Double.parseDouble(oldcal);
        double pro= Double.parseDouble(oldpro);
        double fat= Double.parseDouble(oldfat);
        double ch2o= Double.parseDouble(oldch2o);
        progress.setVisibility(View.VISIBLE);
        update.setVisibility(View.GONE);

        newNickname = infoNickname.getText().toString().trim();
        newBirthday = infoBirthday.getText().toString().trim();
        newGender = infoGender.getText().toString().trim();
        newHeight = infoHeight.getText().toString().trim();
        newWeight = infoWeight.getText().toString().trim();
        if(!oldBirthday.equals(newBirthday) ||
                !oldGender.equals(newGender) || !oldHeight.equals(newHeight) || !oldWeight.equals(newWeight)){
            StringBuilder sb = new StringBuilder(newBirthday);
            String ageString = sb.substring(0,4);
            double agetmp = 2020 - Integer.parseInt(ageString);
            ToastUtil.setToast(""+agetmp);
            double weighttmp= Double.parseDouble(newWeight);
            double heighttmp=Double.parseDouble(newHeight);
            int gendertmp = 0;
            if("男".equals(newGender)){//如果是男性
                cal=66 + 13.7 * weighttmp + 5 * heighttmp - 6.8 * agetmp;//卡路里
                pro=56;//蛋白质
            }
            else{//如果是女性
                cal=65 + 9.6 * weighttmp + 1.7 * heighttmp - 4.7 * agetmp;//卡路里
                pro=46;//蛋白质
            }
            fat=67;//脂肪
            ch2o=cal*0.15;//碳水化合物
        }
        newcal = calinput.getText().toString().trim();
        newpro = proinput.getText().toString().trim();
        newfat = fatinput.getText().toString().trim();
        newch2o = ch2oinput.getText().toString().trim();
        if(oldpro.equals(newpro)  && oldcal.equals(newcal)  && oldfat.equals(newfat)  && oldch2o.equals(newch2o)){
            newcal= String.valueOf(cal);
            newpro= String.valueOf(pro);
            newfat= String.valueOf(fat);
            newch2o= String.valueOf(ch2o);
        }
        // 新旧值不相同时向后端发送修改请求
        if(!oldNickname.equals(newNickname) || !oldBirthday.equals(newBirthday) ||
            !oldGender.equals(newGender) || !oldHeight.equals(newHeight) || !oldWeight.equals(newWeight)
                || !oldpro.equals(newpro)  || !oldcal.equals(newcal)  || !oldfat.equals(newfat)  || !oldch2o.equals(newch2o)){
            mPresenter.updateInfo(phone, oldNickname, newNickname, newBirthday, newGender, newHeight, newWeight, newcal, newpro, newfat, newch2o);
            calinput.setText(newcal);
            proinput.setText(newpro);
            ch2oinput.setText(newch2o);
            fatinput.setText(newfat);
        }
        else{
            ToastUtil.setToast("没有修改个人信息");
            showButton();
        }
    }

    // 弹出日期选择对话框
    private void showDatePicker(View view){
        hideKeyboard(view);
        MyDatePickerDialog newFragment = new MyDatePickerDialog();  //实例
        newFragment.show(getSupportFragmentManager(),"datePicker"); // 显示出来
        newFragment.setCallback(new MyDatePickerDialog.Callback(){
            @Override
            public void save(String birthday) {
                newBirthday = birthday;
                infoBirthday.setText(newBirthday);
            }
        });
    }

    // 弹出性别选择框
    private void showGenderChoice(View veiw){
        hideKeyboard(view);
        GenderDialog dialog = new GenderDialog();
        dialog.show(getSupportFragmentManager(),"checkGender");
        dialog.setCallback(new GenderDialog.Callback() {
            @Override
            public void check(String choice) {
                newGender = choice;
            }

            @Override
            public void save() {
                infoGender.setText(newGender);
            }
        });
    }

    // 收起软键盘
    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public void showButton(){
        progress.setVisibility(View.GONE);
        update.setVisibility(View.VISIBLE);
    }
}
