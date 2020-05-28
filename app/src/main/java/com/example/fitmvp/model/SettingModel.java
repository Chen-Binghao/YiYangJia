package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.UserInfoBean;
import com.example.fitmvp.contract.SettingContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;

public class SettingModel extends BaseModel implements SettingContract.Model {
    @Override
    public void updateInfo(String tel, String nickname, String birthday,
                           String gender, String height, String weight, String cal, String pro, String fat, String ch2o, final InfoHint infoHint) {
        UserInfoBean user = new UserInfoBean();
        user.setTel(tel);
        user.setNickName(nickname);
        user.setBirthday(birthday);


        int genderNum = 0;
        if("男".equals(gender)){
            genderNum = 1;
        }
        else if("女".equals(gender)){
            genderNum = 2;
        }
        user.setGender(genderNum);
        if(height.equals("")){
            user.setHeight(0);
        }
        else {
            user.setHeight(Integer.parseInt(height));
        }
        if(weight.equals("")){
            user.setWeight(0);
        }
        else {
            user.setWeight(Double.parseDouble(weight));
        }
        if(cal.equals("")){
            user.setCal(0);
        }
        else {
            user.setCal(Double.parseDouble(cal));
        }
        if(fat.equals("")){
            user.setFat(0);
        }
        else {
            user.setFat(Double.parseDouble(fat));
        }
        if(pro.equals("")){
            user.setPro(0);
        }
        else {
            user.setPro(Double.parseDouble(pro));
        }
        if(ch2o.equals("")){
            user.setCh2o(0);
        }
        else {
            user.setCh2o(Double.parseDouble(ch2o));
        }

        httpService1.updateInfo(user)
                .compose(new ThreadTransformer<Boolean>())
                .subscribe(new CommonObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean response) {
                        if(response){
                            LogUtils.e("update_info","success");
                            infoHint.successInfo();
                        }
                        else {
                            LogUtils.e("update_info","fail");
                            infoHint.failInfo();
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        LogUtils.e("onError: ", e.getMessage());
                    }
                });
    }
}
