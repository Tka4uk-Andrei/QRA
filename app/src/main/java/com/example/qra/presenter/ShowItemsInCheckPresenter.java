package com.example.qra.presenter;

import android.content.Intent;
import android.widget.Toast;

import com.example.qra.model.CheckDataBase;
import com.example.qra.model.CheckEditingException;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.model.check.CheckItem;
import com.example.qra.presenter.interfaces.IShowItemsInCheckPresenter;
import com.example.qra.view.CreateCheckItemActivity;
import com.example.qra.view.interfaces.IShowItemsInCheckView;

public class ShowItemsInCheckPresenter extends AndroidPresenter implements IShowItemsInCheckPresenter {
    private IShowItemsInCheckView showItemsInCheckView;
    public static final String CURRENT_ITEM = "ITEM";


    private int position;
    private CheckItem boughtItems[];
    private CheckInformationStorage check;

    public ShowItemsInCheckPresenter(IShowItemsInCheckView showItemsInCheckView) {
        super(showItemsInCheckView);
        this.showItemsInCheckView = showItemsInCheckView;
    }

    public void startCreateCheckItemActivity() {
        Intent intent1 = new Intent(getView().getContext(), CreateCheckItemActivity.class);
        intent1.putExtra(ShowAllChecksPresenter.CURRENT_CHECK,
                showItemsInCheckView.getStarterIntent().getIntExtra(ShowAllChecksPresenter.CURRENT_CHECK, 0));
        startActivityForResult(intent1, 1);
    }

    @Override
    public CheckItem[] getShoppingList() {
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getView().getContext());
        check = checkList[position];

        return check.getShoppingList();
    }

    @Override
    public void deleteCheck() {
        CheckDataBase.deleteCheck(check.getId(), getView().getContext());
        finish();
    }

    @Override
    public String getCheckObtainingMethod() {
        return check.getObtainingMethod();
    }

    @Override
    public void changeItemName(int index, String newName) throws Exception {
        if (newName.length() == 0) {
            throw new Exception("Введите имя товара");
        }
        if (boughtItems[index].getNameForUser().equals(newName)) {
            return;
        }
        boughtItems[index].setNameForUser(newName);
        check.getShoppingList()[index].setNameForUser(newName);

        CheckDataBase.updateNameForUser(check.getShoppingList()[index].getId(), newName, getView().getContext());
    }

    @Override
    public void changeItemCategory(int index, String newCategory) throws Exception {
        if (newCategory.length() == 0) {
            throw new Exception("Введите количество товара");
        }
        if (boughtItems[index].getSubjectCategory().equals(newCategory)) {
            return;
        }
        boughtItems[index].setSubjectCategory(newCategory);
        check.getShoppingList()[index].setSubjectCategory(newCategory);

        CheckDataBase.updateSubjectCategory(check.getShoppingList()[index].getId(), newCategory, getView().getContext());
    }

    @Override
    public void changeItemQuantity(int index, String newQuantity) throws Exception {
        if (newQuantity.length() == 0) {
            throw new Exception("Введите количество товара");
        }
        if (newQuantity.length() > 9) {
            throw new Exception("Количество товара должно быть не больше 1000000000");
        }
        int quantity = Integer.parseInt(newQuantity);
        if (boughtItems[index].getQuantity() == quantity) {
            return;
        }
        boughtItems[index].setQuantity(quantity);
        check.getShoppingList()[index].setQuantity(quantity);

        CheckDataBase.updateQuantyty(check.getShoppingList()[index].getId(), quantity, getView().getContext());
    }

    @Override
    public void changeItemPrice(int index, String newPrice) throws Exception {
        if (newPrice.length() == 0) {
            throw new Exception("Введите цену товара");
        }

        double doublePrice = 0;
        try {
            doublePrice = Double.parseDouble(newPrice);
        } catch (Exception e) {
            Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (doublePrice >= 10000000) {
            throw new Exception("Цена за товар должна быть не больше 10000000.00 рублей");
        }

        doublePrice *= 100;
        if (doublePrice - (int) doublePrice != 0) {
            throw new Exception("Цена может содержать 0-2 цифры после точки");
        }


        int price = (int) doublePrice;
        if (boughtItems[index].getPrice() == price) {
            return;
        }
        boughtItems[index].setPrice(price);
        check.getShoppingList()[index].setPrice(price);

        CheckDataBase.updatePrise(check.getShoppingList()[index].getId(), price, getView().getContext());
    }


    @Override
    public void deleteItem(int index) {
        try {
            CheckDataBase.deleteItem(boughtItems[index].getId(), getView().getContext());
        } catch (CheckEditingException e) {
            showItemsInCheckView.showErrorMessage(e.getMessage());
        }
    }

    public void setCurrentItem(int index) {
        boughtItems = check.getShoppingList();
        showItemsInCheckView.getStarterIntent().putExtra(CURRENT_ITEM, index);
    }

    @Override
    public void onCreate() {
        position = showItemsInCheckView.getStarterIntent().getIntExtra(ShowAllChecksPresenter.CURRENT_CHECK, 0);
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getView().getContext());
        check = checkList[position];

        boughtItems = check.getShoppingList();
    }

    @Override
    public void updateView() {
        showItemsInCheckView.update(getShoppingList());
    }

    @Override
    public boolean tryChangeItem(int index, String name, String category, String quantity, String price) {
        try {
            changeItemName(index, name);
            changeItemCategory(index, category);
            changeItemQuantity(index, quantity);
            changeItemPrice(index, price);
        } catch (Exception e) {
            showItemsInCheckView.showErrorMessage(e.getMessage());
            return false;
        }
        return true;
    }
}
