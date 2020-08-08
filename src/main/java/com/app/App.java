package com.app;

import com.app.service.MenuService;

public class App {
    public static void main(String[] args) {
        MenuService menuService = new MenuService();
        menuService.mainMenuService();
    }
}