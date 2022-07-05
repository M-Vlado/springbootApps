package com.example.basicwebshop2.sevices;

import com.example.basicwebshop2.models.ShopItem;
import com.example.basicwebshop2.repository.DB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ShopItemServiceImpl implements ShopItemService {

    private DB db;

    @Autowired
    public ShopItemServiceImpl(DB db) {
        this.db = db;
    }

    @Override
    public List<ShopItem> getAll() {
        return db.getShopItems();
    }

    @Override
    public List<ShopItem> onlyAvailable() {
        Predicate<ShopItem> biggerThanZero = new Predicate<ShopItem>() {
            @Override
            public boolean test(ShopItem shopItem) {
                return shopItem.getQuantityOfStock() > 0;
            }
        };
        return db.getShopItems().stream().filter(biggerThanZero).collect(Collectors.toList());
    }

    @Override
    public List<ShopItem> cheapestFirst() {
        Comparator<ShopItem> cheapestFirst = new Comparator<ShopItem>() {
            @Override
            public int compare(ShopItem o1, ShopItem o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        };
        return db.getShopItems().stream().sorted(cheapestFirst).collect(Collectors.toList());
    }

    @Override
    public List<ShopItem> containsNike() {
        Predicate<ShopItem> containsNike = new Predicate<ShopItem>() {
            @Override
            public boolean test(ShopItem shopItem) {
                return (shopItem.getName().toLowerCase().contains("nike") || shopItem.getDescription().toLowerCase().contains("nike"));
            }
        };
        return db.getShopItems().stream().filter(containsNike).collect(Collectors.toList());
    }

    @Override
    public OptionalDouble averageStock() {
        return db.getShopItems().stream().mapToInt(ShopItem::getQuantityOfStock).average();
    }

    @Override
    public Optional<ShopItem> mostExpensive() {
        return db.getShopItems().stream().max(Comparator.comparingDouble(ShopItem::getPrice));
    }

    @Override
    public List<ShopItem> searchFor(String name) {
        Predicate<ShopItem> item = new Predicate<ShopItem>() {
            @Override
            public boolean test(ShopItem shopItem) {
                return (shopItem.getDescription().toLowerCase().contains(name.toLowerCase()) || shopItem.getName().toLowerCase().contains(name.toLowerCase()));
            }
        };
        return db.getShopItems().stream().filter(item).collect(Collectors.toList());
    }

    @Override
    public List<ShopItem> filterByType(String type) {
        return switch (type) {
            case "clothes-and-shoes" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getType().contains("Clothes and Shoes")).collect(Collectors.toList());
            case "electronic" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getType().contains("Electronics")).collect(Collectors.toList());
            case "beverages-and-snacks" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getType().contains("Beverages and Snacks")).collect(Collectors.toList());
            default -> null;
        };
    }

    @Override
    public List<ShopItem> getCZPrice() {
        ArrayList<ShopItem> items = new ArrayList<>();

        for (ShopItem i : db.getShopItems()) {
            double oldPrice = i.getPrice();
            double newPrice = oldPrice * 24;
            ShopItem s = new ShopItem(i.getId(), i.getName(), i.getType(), i.getDescription(), newPrice, i.getQuantityOfStock());
            items.add(s);
        }

        return items;
    }

    @Override
    public List<ShopItem> getFilterByPrice(double price, String option) {
        return switch (option) {
            case "above" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getPrice() > price).collect(Collectors.toList());
            case "below" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getPrice() < price).collect(Collectors.toList());
            case "exactly" ->
                    db.getShopItems().stream().filter(shopItem -> shopItem.getPrice() == price).collect(Collectors.toList());
            default -> null;
        };
    }
}
