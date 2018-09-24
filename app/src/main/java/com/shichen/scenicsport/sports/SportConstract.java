package com.shichen.scenicsport.sports;

import com.shichen.scenicsport.BasePresenter;
import com.shichen.scenicsport.BaseView;
import com.shichen.scenicsport.data.Sport;

import java.util.List;

public interface SportConstract {
    interface View extends BaseView<Presenter>{
        void finishRefresh();
        void finishLoadMore();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void initData(List<Sport> sports);
        void refreshData();
        void loadMoreData();
    }
}
