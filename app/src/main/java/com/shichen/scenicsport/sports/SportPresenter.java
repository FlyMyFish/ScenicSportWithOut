package com.shichen.scenicsport.sports;

import com.shichen.scenicsport.data.Sport;
import com.shichen.scenicsport.data.source.SportDataSource;
import com.shichen.scenicsport.data.source.SportsRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class SportPresenter implements SportConstract.Presenter{

    private final SportsRepository mSportsRepository;
    private final SportConstract.View mSportView;

    public SportPresenter(SportsRepository mSportsRepository, SportConstract.View mSportView) {
        this.mSportsRepository = checkNotNull(mSportsRepository,"mSportsRepository cannot be null");
        this.mSportView = checkNotNull(mSportView,"mSportView cannot be null");
        this.mSportView.setPresenter(this);
    }

    private List<Sport> mSports;

    @Override
    public void initData(List<Sport> sports) {
        this.mSports=sports;
    }

    private int curPage;
    private String keyword="";
    @Override
    public void refreshData() {
        curPage=1;
        getData(true);
    }

    @Override
    public void loadMoreData() {
        curPage++;
        getData(false);
    }
    private void getData(final boolean isRefresh){
        mSportsRepository.getSports(String.valueOf(curPage), keyword, new SportDataSource.LoadSportsCallBack() {
            @Override
            public void onSportsLoaded(List<Sport> sports) {
                checkNotNull(mSports);
                if (isRefresh){
                    mSports.clear();
                    mSports.addAll(sports);
                    mSportView.finishRefresh();
                }else {
                    mSports.addAll(sports);
                    mSportView.finishLoadMore();
                }
            }

            @Override
            public void onDataNotAvailable(Exception e) {
                mSportView.toastMessage(e.toString());
                if (isRefresh){
                    mSportView.finishRefresh();
                }else {
                    mSportView.finishLoadMore();
                    curPage--;
                }
            }
        });
    }

    @Override
    public void start() {
        refreshData();
    }
}
