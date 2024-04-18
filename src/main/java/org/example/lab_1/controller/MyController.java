package org.example.lab_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {

    @GetMapping("index")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("orders")
    public String orders() {
        return "orders";
    }

    @GetMapping("chat")
    public String chat() {
        return "chat";
    }
}