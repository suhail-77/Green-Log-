package com.greenlog.greenlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/volunteers")
    public String volunteers() {
        return "forward:/volunteers.html";
    }

    @GetMapping("/plantation-drives")
    public String plantationDrives() {
        return "forward:/plantation-drives.html";
    }

    @GetMapping("/trees")
    public String trees() {
        return "forward:/trees.html";
    }

    @GetMapping("/check-ins")
    public String checkIns() {
        return "forward:/check-ins.html";
    }
}
