package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.RecipeContract;
import com.example.fitmvp.contract.RecordContract;
import com.example.fitmvp.model.RecipeModel;
import com.example.fitmvp.model.RecordModel;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.fragment.FragmentRecipe;

import java.util.List;

public class RecipePresenter extends BasePresenter<FragmentRecipe> implements RecipeContract.Presenter {
    private RecipeModel model = new RecipeModel();

}