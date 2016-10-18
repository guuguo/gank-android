package com.guuguo.learnsave.view;

import com.guuguo.learnsave.bean.entity.GankBean;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMainView extends IBaseView {
    void showProgress();
    void hideProgress();
    void showNoMoreData();
    void showErrorView();
    void showMeiziList(List<GankBean> lMeiziList);
}
