package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.service.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/usuario")
public class UserController {
 
    @Autowired
    private UserService userService;
    
    @GetMapping("/ingresar")
    public String login(){
        return "login.html";
    }
    
    @GetMapping("/registro")
    public String register(){
        return "register.html";
    }
    
    @PostMapping("/registrar")
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, MultipartFile icon){
        try {
            userService.createUser(email, password, name, icon);
            return "login.html";
        } catch (Exception ex) {
            ex.getMessage();
            return "register.html";
        }
    }
}
