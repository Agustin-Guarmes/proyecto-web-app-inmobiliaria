package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.UserRepository;
import Inmobilaria.GyL.service.UserService;
import Inmobilaria.GyL.service.impl.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;

    @PostMapping("/registrar")
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, @RequestParam String role, MultipartFile icon) {
        try {
            System.out.println(icon + "    Soy icon register!!");
            userService.createUser(email, password, name, dni, role, icon);
            return "redirect:/";
        } catch (Exception ex) {
            ex.getMessage();
            return "redirect:/";
        }
    }

    @GetMapping("/perfil")
    public String profile(){
        return "profile.html";
    }
    
    @GetMapping("/iniciarSesion")
    public String login(){
        return "login.html";
    }
    
    @GetMapping("/registrarse")
    public String register(){
        return "register.html";
    }
    
    @GetMapping("/restablecerContrasena")
    public String resetPassword(){
        return "resetPassword.html";
    }

    @PostMapping("/perfil/{id}")
    public String updateProfile(@PathVariable Long id, @RequestParam String name, @RequestParam String password, MultipartFile icon){
        if (icon.getContentType().equalsIgnoreCase("application/octet-stream")){
            userService.modifyUser(id,name,password);
        } else {
            userService.modifyUser(id,name,password,icon);
        }
        return "profile.html";
    }

    @PostMapping("/agregarPropiedad")
    public String addProperty(@RequestParam Long idUser, @RequestParam String address, @RequestParam String location, @RequestParam String status, @RequestParam String type, @RequestParam int surface, @RequestParam double price, @RequestParam String description, @RequestParam MultipartFile[] files) {
        try {
            propertyService.createProperty(userService.getOne(idUser), address, location, status, type, surface, price, description, Arrays.asList(files));
        } catch (IOException ex) {
            ex.getMessage();
        }
        return "redirect:/";
    }

    @GetMapping("/propiedades/{id}")
    public String listProperties(@PathVariable Long id, ModelMap model) {
        List<Property> properties = propertyService.findByUser(id);
        model.put("properties", properties);
        return "user.html";
    }
}
