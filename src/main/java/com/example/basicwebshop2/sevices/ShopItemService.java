package com.example.basicwebshop2.sevices;

import com.example.basicwebshop2.models.ShopItem;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface ShopItemService {

    List<ShopItem> getAll();

    List<ShopItem> onlyAvailable();

    List<ShopItem> cheapestFirst();

    List<ShopItem> containsNike();

    OptionalDouble averageStock();

    Optional<ShopItem> mostExpensive();

    List<ShopItem> searchFor(String name);

    List<ShopItem> filterByType(String type);

    List<ShopItem> getCZPrice();

    List<ShopItem> getFilterByPrice(double price, String option);
}
