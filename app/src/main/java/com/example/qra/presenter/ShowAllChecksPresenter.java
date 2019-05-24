package com.example.qra.presenter;

import com.example.qra.CheckDataBase;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.interfaces.IShowAllChecksPresenter;
import com.example.qra.view.interfaces.IShowAllChecksView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowAllChecksPresenter extends AndroidPresenter implements IShowAllChecksPresenter {

    private IShowAllChecksView editBoughtDataView;
    private CheckInformationStorage checkList[];
    private String checkBuyTimes[];

    public ShowAllChecksPresenter(IShowAllChecksView editBoughtDataView) {
        super(editBoughtDataView);
        this.editBoughtDataView = editBoughtDataView;
    }

    @Override
    public void addCheck() {//TODO checkname
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        CheckInformationStorage newCheck = new CheckInformationStorage.Builder()
                .setObtainingMethod("user")
                .setBuyTime(strDate)
                .build();
        CheckDataBase.insert(newCheck, getView().getContext());

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
}
