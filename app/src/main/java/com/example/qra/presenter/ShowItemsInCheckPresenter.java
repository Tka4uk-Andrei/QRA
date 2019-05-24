package com.example.qra.presenter;

import android.widget.Toast;

import com.example.qra.CheckDataBase;
import com.example.qra.CheckEditingException;
import com.example.qra.model.check.BoughtItem;
import com.example.qra.model.check.CheckInformationStorage;
import com.example.qra.presenter.interfaces.IShowItemsInCheckPresenter;
import com.example.qra.view.interfaces.IShowItemsInCheckView;

public class ShowItemsInCheckPresenter extends AndroidPresenter implements IShowItemsInCheckPresenter {
    private IShowItemsInCheckView editCheckDataView;

    private int position;
    private BoughtItem boughtItems[];
    private CheckInformationStorage check;

    public ShowItemsInCheckPresenter(IShowItemsInCheckView editCheckDataView) {
        super(editCheckDataView);
        this.editCheckDataView = editCheckDataView;
    }

    @Override
    public BoughtItem[] getShoppingList(){
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getView().getContext());
        check = checkList[position];

        return check.getShoppingList();
    }

    @Override
    public void deleteCheck(){
        CheckDataBase.deleteCheck(check.getId(), getView().getContext());
    }

    @Override
    public String getCheckObtainingMethod(){
        return check.getObtainingMethod();
    }

    @Override
    public void changeItemName(int index, String newName) throws Exception {
        if(newName.length() == 0){
            throw new Exception("Введите имя товара");
        }
        if(boughtItems[index].getNameForUser().equals(newName)){
            return;
        }
        boughtItems[index].setNameForUser(newName);
        check.getShoppingList()[index].setNameForUser(newName);

        CheckDataBase.updateNameForUser(check.getShoppingList()[index].getId(), newName, getView().getContext());
    }

    @Override
    public void changeItemCategory(int index, String newCategory) throws Exception {
        if(newCategory.length() == 0){
            throw new Exception("Введите количество товара");
        }
        if(boughtItems[index].getSubjectCategory().equals(newCategory)){
            return;
        }
        boughtItems[index].setSubjectCategory(newCategory);
        check.getShoppingList()[index].setSubjectCategory(newCategory);

        CheckDataBase.updateSubjectCategory(check.getShoppingList()[index].getId(), newCategory, getView().getContext());
    }

    @Override
    public void changeItemQuantity(int index, String newQuantity) throws Exception {
        if(newQuantity.length() == 0){
            throw new Exception("Введите количество товара");
        }
        if (newQuantity.length() > 9) {
            throw new Exception("Количество товара должно быть не больше 1000000000");
        }
        int quantity = Integer.parseInt(newQuantity);
        if(boughtItems[index].getQuantity() == quantity){
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
        if(doublePrice >= 10000000) {
            throw new Exception("Цена за товар должна быть не больше 10000000.00 рублей");
        }

        doublePrice *= 100;
        if(doublePrice -(int)doublePrice != 0) {
            throw new Exception("Цена может содержать 0-2 цифры после точки");
        }


        int price = (int) doublePrice;
        if(boughtItems[index].getPrice() == price){
            return;
        }
        boughtItems[index].setPrice(price);
        check.getShoppingList()[index].setPrice(price);

        CheckDataBase.updatePrise(check.getShoppingList()[index].getId(), price, getView().getContext());
    }


    @Override
    public void deleteItem(int index){
        try {
            CheckDataBase.deleteItem(boughtItems[index].getId(), getView().getContext());
        } catch (CheckEditingException e) {
            Toast.makeText(getView().getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate() {
        position = editCheckDataView.getStarterIntent().getIntExtra("checkNumber", 0);
        final CheckInformationStorage checkList[] = CheckDataBase.getCheckList(getView().getContext());
        check = checkList[position];

        boughtItems = check.getShoppingList();
    }

    @Override
    public void update(){
        boughtItems = check.getShoppingList();
    }
}
