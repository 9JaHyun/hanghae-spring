package com.blogservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/doc")
    public String restDoc() {
        return "index.adoc";
    }
}
