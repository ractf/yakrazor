package uk.co.ractf.yakrazor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UIController {

    @GetMapping
    public String index() {
        return "index";
    }

}
