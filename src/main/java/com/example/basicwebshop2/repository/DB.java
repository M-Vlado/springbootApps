package com.example.basicwebshop2.repository;

import com.example.basicwebshop2.models.ShopItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DB {

    private List<ShopItem> shopItems;

    public DB() {
        this.shopItems = new ArrayList<>(Arrays.asList(
                new ShopItem(1,"Running shoes","Clothes and Shoes", "Nike running shoes for every day sport", 115, 5),
                new ShopItem(2,"Printer","Electronics", "Some HP printer that will print pages", 145, 2),
                new ShopItem(3,"Coca cola","Beverages and Snacks", "0.5l standard coke", 1.50, 0),
                new ShopItem(4,"Wokin","Beverages and Snacks", "Chicken with fired rice and WOKIN sauce", 3.60, 100),
                new ShopItem(5,"T-shirt","Clothes and Shoes", "Blue with a corgi on a bike", 9.90, 1)
        ));
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public void addShopItem(ShopItem shopItem){
        shopItems.add(shopItem);
    }

    public void deleteShopItem(int id){
        shopItems.removeIf(item -> item.getId() == id);
    }

    public void updateShopItem(ShopItem shopItem){
        for(ShopItem item: shopItems){
            if(item.getId() == shopItem.getId()){
                item.setName(shopItem.getName());
                item.setDescription(shopItem.getDescription());
                item.setPrice(shopItem.getPrice());
                item.setQuantityOfStock(shopItem.getQuantityOfStock());
            }
        }
    }

}
