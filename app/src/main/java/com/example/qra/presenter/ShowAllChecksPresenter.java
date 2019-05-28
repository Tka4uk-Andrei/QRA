package com.example.qra.presenter;

import android.content.Intent;

import com.example.qra.CheckDataBase;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.interfaces.IShowAllChecksPresenter;
import com.example.qra.view.ShowItemsInCheckActivity;
import com.example.qra.view.interfaces.IShowAllChecksView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowAllChecksPresenter extends AndroidPresenter implements IShowAllChecksPresenter {
    public static final String CURRENT_CHECK = "CHECK";

    private IShowAllChecksView showAllChecksView;
    private CheckInformationStorage checkList[];
    private String checkBuyTimes[];

    public ShowAllChecksPresenter(IShowAllChecksView showAllChecksView) {
        super(showAllChecksView);
        this.showAllChecksView = showAllChecksView;
    }

    public void startShowItemsInCheckActivity(int i){
        Intent intent = new Intent(getView().getContext(), ShowItemsInCheckActivity.class);
        intent.putExtra(CURRENT_CHECK, i);
        startActivityForResult(intent, 1);
    }
    @Override
    public void addCheck() {//TODO checkname
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        CheckInformationStorage newCheck = new CheckInformationStorage.Builder()
                .setObtainingMethod(CheckInformationStorage.OBTAIN_METHOD_USER)
                .setBuyTime(strDate)
                .build();
        CheckDataBase.insert(newCheck, getView().getContext());
        showAllChecksView.update(getCheckList());
    }

    @Override
    public String[] getCheckList() {//TODO checkname
        checkList = CheckDataBase.getCheckList(getView().getContext());
        checkBuyTimes = new String[checkList.length];
        for (int i = 0; i < checkList.length; i++) {
            checkBuyTimes[i] = checkList[i].getBuyTime();
        }

        return checkBuyTimes;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void updateView() {
        showAllChecksView.update(getCheckList());
    }
}
