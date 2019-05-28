package com.example.qra.presenter;

import com.example.qra.CheckDataBase;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.interfaces.ICreateCheckItemPresenter;
import com.example.qra.view.interfaces.ICreateCheckItemView;

public class CreateCheckItemPresenter extends AndroidPresenter implements ICreateCheckItemPresenter {
    private ICreateCheckItemView createCheckItemView;

    public CreateCheckItemPresenter(ICreateCheckItemView createCheckItemView) {
        super(createCheckItemView);
        this.createCheckItemView = createCheckItemView;
    }

    @Override
    public boolean tryAddItem(String name, String category, String quantity, String price) {
        try {
            int position = createCheckItemView.getStarterIntent().
                    getIntExtra(ShowAllChecksPresenter.CURRENT_CHECK, 0);
            //check name is correct
            if (name.length() == 0) {
                throw new Exception("Введите имя товара");
            }

            //check category is correct
            if (category.length() == 0) {
                throw new Exception("Введите категорию товара");
            }

            //check quantity is correct
            if (quantity.length() == 0) {
                throw new Exception("Введите количество товара");
            }
            if (quantity.length() > 9) {
                throw new Exception("Количество товара должно быть не больше 1000000000");
            }

            //check price is correct
            if (price.length() == 0) {
                throw new Exception("Введите цену товара");
            }

            double doublePrice = 0;

            doublePrice = Double.parseDouble(price);
            if (doublePrice >= 10000000) {
                throw new Exception("Цена за товар должна быть не больше 10000000.00 рублей");
            }

            doublePrice *= 100;
            if (doublePrice - (int) doublePrice != 0) {
                throw new Exception("Цена может содержать 0-2 цифры после точки");
            }
            int intPrice = (int) doublePrice;

            BoughtItem boughtItems[];
            CheckInformationStorage check;
            final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getView().getContext());
            check = checkList[position];
            boughtItems = check.getShoppingList();

            BoughtItem[] newList = new BoughtItem[boughtItems.length + 1];
            for (int i = 0; i < boughtItems.length; i++) {
                newList[i] = boughtItems[i];
            }
            BoughtItem newItem = new BoughtItem.Builder()
                    .setName(name)
                    .setNameForUser(name)
                    .setSubjectCategory(category)
                    .setQuantity(Integer.parseInt(quantity))
                    .setPrice(intPrice)
                    .build();

            newItem = CheckDataBase.addItem(check.getId(), newItem, getView().getContext());
            newList[newList.length - 1] = newItem;
            check.setShoppingList(newList);
            CheckDataBase.updateAllPositionUserCheck(getView().getContext(), check);

        } catch (Exception e) {
            createCheckItemView.showErrorMessage(e.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public void onCreate() {
        // nothing to do
    }

}
