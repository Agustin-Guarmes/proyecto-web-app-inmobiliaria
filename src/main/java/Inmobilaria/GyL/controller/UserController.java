package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    
    @GetMapping("/")
    public String index(ModelMap model){
        
        List <User> users = userService.listUsers();
        
        model.put("users", users);
        
        return "index.html";
    }
    
    @GetMapping("/ingresar")
    public String login(){
        return "user.html";
    }
    
    @GetMapping("/registro")
    public String register(){
        return "user.html";
    }
    
    @PostMapping("/registrar")
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, MultipartFile icon){
        try {
            userService.createUser(email, password, name, dni, icon);
            return "index.html";
        } catch (Exception ex) {
            ex.getMessage();
            return "redirect:/usuario/registro";
        }
    }
}
