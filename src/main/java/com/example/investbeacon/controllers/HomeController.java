package com.example.investbeacon.controllers;

import com.example.investbeacon.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${EOD_API}")
    String eodApiKey;

    @Value("${MARKETAUX_API}")
    String marketAuxKey;

    @Value("${POLYGON_API}")
    String polygonAPIKey;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("EOD_API", eodApiKey);
        model.addAttribute("MARKETAUX_API", marketAuxKey);
        model.addAttribute("POLYGON_API", polygonAPIKey);
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(){
        return "about-us";
    }

    @GetMapping("/features")
    public String features(){
        return "features";
    }


}
