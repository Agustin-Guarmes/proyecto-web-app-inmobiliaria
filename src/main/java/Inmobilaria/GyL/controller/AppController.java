package Inmobilaria.GyL.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    @GetMapping("/")
    public String index(ModelMap model) {
        model.put("title", "MrHouse | Inicio");
        return "index.html";
    }
}
