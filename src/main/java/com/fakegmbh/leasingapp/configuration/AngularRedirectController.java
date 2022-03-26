package com.fakegmbh.leasingapp.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AngularRedirectController {

    @RequestMapping({ "/contracts/**", "/customers/**", "vehicles/**" })
    public String index() {
        return "forward:/index.html";
    }
}