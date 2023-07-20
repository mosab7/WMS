package com.springboot.warehousemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {

    @GetMapping("/ui/products")
    public String productsPage(Model model) {
        return "products";
    }

    @GetMapping("/ui/dashboard")
    public String homePage(Model model) {
        return "charts";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        return "charts";
    }

    @GetMapping("")
    public String defaultPage(Model model) {
        return "home";
    }

    @GetMapping("/ui/customers")
    public String customersPage(Model model) {
        return "customers";
    }

    @GetMapping("/ui/orders")
    public String ordersPage(Model model) {
        return "orders";
    }

    @GetMapping("/ui/carriers")
    public String carriersPage(Model model) {
        return "carriers";
    }

    @GetMapping("/ui/suppliers")
    public String suppliersPage(Model model) {
        return "suppliers";
    }

}

