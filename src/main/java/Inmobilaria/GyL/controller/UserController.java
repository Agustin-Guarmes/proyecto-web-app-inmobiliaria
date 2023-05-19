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
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/propiedades")
    public String propiedades() {
        return "properties.html";
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listaPersonalizada")
    public String listUsers(ModelMap model) {

        List<User> users = userService.listUsers();

        model.put("users", users);

        return "into.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listaPersonalizadaBusqueda")
    public String searchUsers(@RequestParam String word, ModelMap model) {

        List<User> users = userRepository.findByName(word);

        model.put("users", users);

        return "into.html";
    }

    @PostMapping("/registrar")
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, @RequestParam String role, MultipartFile icon) {
        try {
            System.out.println(role);
            userService.createUser(email, password, name, dni, role, icon);
            return "redirect:/usuario/";
        } catch (Exception ex) {
            ex.getMessage();
            return "redirect:/usuario/registro";
        }
    }

    @GetMapping("/perfil")
    public String profile(){
        return "profile.html";
    }

    @PostMapping("/perfil/{id}")
    public String updateProfile(@PathVariable Long id, @RequestParam String name, @RequestParam String password, @RequestParam MultipartFile icon){

        userService.modifyUser(id,name,password,icon);

        return "profile.html";
    }

    @GetMapping("/formularioPropiedad")
    public String formProperty() {
        return "formProperty.html";
    }

    @PostMapping("/agregarPropiedad")
    public String addProperty(@RequestParam Long idUser, @RequestParam String address, @RequestParam String location, @RequestParam String status, @RequestParam String type, @RequestParam int surface, @RequestParam double price, @RequestParam String description, @RequestParam MultipartFile[] files) {

        try {
            propertyService.createProperty(userService.getOne(idUser), address, location, status, type, surface, price, description, Arrays.asList(files));

        } catch (IOException ex) {
            ex.getMessage();
        }
        return "index.html";
    }
    @GetMapping("/propiedades/{id}")
    public String listProperties(@PathVariable Long id, ModelMap model){
        List<Property> properties = propertyService.findByUser(id);
        model.put("properties",properties);
        return "user.html";
    }
}
