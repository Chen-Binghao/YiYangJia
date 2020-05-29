package com.example.fitmvp.model;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.exception.ExceptionEngine;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainPageModel extends BaseModel implements MainPageContract.Model {
    private String chName[] = {"奶酪蛋糕", "鸡翅", "蒸包", "炒饭", "汉堡", "酸辣汤", "冰激凌", "牛排", "寿司", "糖醋排骨", "炒花菜", "米饭", "紫菜汤"};
    private String enName[] = {"cheesecake", "chicken_wings", "dumplings", "fried_rice", "hamburger", "hot_and_sour_soup", "ice_cream", "steak", "sushi", "tangcupaigu", "chaohuacai", "mifan", "zicaitang"};

    public String transFoodNameToEn(String foodName) {
        for (int i=0; i<enName.length; i++) {
            if (foodName.equals(chName[i])) {
                return enName[i];
            }
        }
        return "None";
    }

    public String transFoodNameToCh(String foodName) {
        for (int i=0; i<enName.length; i++) {
            if (foodName.equals(enName[i])) {
                return chName[i];
            }
        }
        return "None";
    }
    @Override
    public void getCalValue(final calCallback callback) {
        SpUtils spUtils = new SpUtils();
        String tel = (String)spUtils.get("phone","");
        if(tel==null || tel.equals("")){
            callback.fail();
        }
        else{
            httpService1.getSumCal(tel)
                    .compose(new ThreadTransformer<JSONObject>())
                    .subscribe(new CommonObserver<JSONObject>() {
                        @Override
                        public void onNext(JSONObject jsonObject) {
                            try {
                                double target = (double)jsonObject.get("standard");
                                double current = jsonObject.getDouble("result");
                                callback.success(target,current);
                            }
                            catch (JSONException e){
                                ExceptionEngine.handleException(e);
                            }
                        }

                        @Override
                        protected void onError(ApiException e) {
                            LogUtils.e("onError",e.message);
                        }
                    });
        }
    }

    @Override
    public void getList(final listCallback callback) {
        SpUtils spUtils = new SpUtils();
        String tel = (String)spUtils.get("phone","");
        if(tel==null || tel.equals("")){
            callback.fail();
        }
        else{
            httpService1.getFiveRecord(tel)
                    .compose(new ThreadTransformer<List<RecordBean>>())
                    .subscribe(new CommonObserver<List<RecordBean>>() {
                        @Override
                        public void onNext(List<RecordBean> list) {
                            // 按时间降序
                            Collections.sort(list, new Comparator<RecordBean>() {
                                @Override
                                public int compare(RecordBean t1, RecordBean t2) {
                                    long time1 = t1.getRawTime();
                                    long time2 = t2.getRawTime();
                                    long diff = time1 - time2;
                                    if (diff > 0) {
                                        return -1;
                                    }
                                    else if (diff < 0) {
                                        return 1;
                                    }
                                    return 0;
                                }
                            });
                            for (RecordBean recordBean:list) {
                                recordBean.setFood(transFoodNameToCh(recordBean.getFood()));
                            }
                            callback.success(list);
                        }

                        @Override
                        protected void onError(ApiException e) {
                            LogUtils.e("onError",e.message);
                            callback.fail();
                        }
                    });
        }
    }
}
