package com.example.basicwebshop2.controllers;

import com.example.basicwebshop2.sevices.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebShopController {


    private ShopItemService shopItemService;

    @Autowired
    public WebShopController(ShopItemService shopItemService) {
        this.shopItemService = shopItemService;

    }

    @GetMapping("/webshop")
    public String showWebShop(Model model) {
        model.addAttribute("items", shopItemService.getAll());
        return "index";
    }

    @GetMapping("/webshop/more-filters")
    public String showMoreFilters(Model model) {
        model.addAttribute("items", shopItemService.getAll());
        return "index";
    }

    @GetMapping("/webshop/only-available")
    public String onlyAvailable(Model model) {
        model.addAttribute("items", shopItemService.onlyAvailable());
        return "index";
    }

    @GetMapping("/webshop/cheapest-first")
    public String cheapestFirst(Model model) {
        model.addAttribute("items", shopItemService.cheapestFirst());
        return "index";
    }

    @GetMapping("/webshop/contains-nike")
    public String containsNike(Model model) {
        model.addAttribute("items", shopItemService.containsNike());

        return "index";
    }

    @GetMapping("/webshop/average-stock")
    public String averageStock(Model model) {
        if (shopItemService.averageStock().isPresent()) {
            model.addAttribute("averageStock", shopItemService.averageStock().getAsDouble());
        } else {
            model.addAttribute("averageStock", "nothing to show list of products is empty!");
        }
        return "index";
    }

    @GetMapping("/webshop/most-expensive")
    public String mostExpensive(Model model) {
        if (shopItemService.mostExpensive().isPresent()) {
            model.addAttribute("mostExpensive", shopItemService.mostExpensive().get().getName());
        } else {
            model.addAttribute("mostExpensive", "nothing to show list of products is empty!");
        }
        return "index";
    }

    @PostMapping("/webshop/search")
    public String search(Model model, @RequestParam(name = "product") String product) {
        model.addAttribute("items", shopItemService.searchFor(product));
        return "index";
    }

    @GetMapping("/webshop/more-filters/filter-by-type/{type}")
    public String clothesAndShoes(Model model, @PathVariable(name = "type") String type) {
        model.addAttribute("items", shopItemService.filterByType(type));
        return "index";
    }

    @GetMapping("/webshop/more-filters/euro")
    private String euroCurrency(Model model) {
        model.addAttribute("items", shopItemService.getAll());
        model.addAttribute("currency", " €");
        return "index";
    }

    @GetMapping("/webshop/more-filters/czech-crown")
    private String czechCrownCurrency(Model model) {
        model.addAttribute("items", shopItemService.getCZPrice());
        model.addAttribute("currency", " Kč");
        return "index";
    }

    @PostMapping(value = "/webshop/more-filters/filter-by-price", params = "above")
    private String filterByPriceAbove(Model model, @RequestParam(name = "price") String price) {
        model.addAttribute("items", shopItemService.getFilterByPrice(Double.parseDouble(price), "above"));
        return "index";
    }

    @PostMapping(value = "/webshop/more-filters/filter-by-price", params = "below")
    private String filterByPriceBelow(Model model, @RequestParam(name = "price") String price) {
        model.addAttribute("items", shopItemService.getFilterByPrice(Double.parseDouble(price), "below"));
        return "index";
    }

    @PostMapping(value = "/webshop/more-filters/filter-by-price", params = "exactly")
    private String filterByPriceExactly(Model model, @RequestParam(name = "price") String price) {
        model.addAttribute("items", shopItemService.getFilterByPrice(Double.parseDouble(price), "exactly"));
        return "index";
    }
}
