package Inmobilaria.GyL.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", " | Inicio");
        return "index.html";
    }
}
