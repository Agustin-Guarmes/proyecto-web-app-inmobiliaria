package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.UserRepository;
import Inmobilaria.GyL.service.UserService;
import Inmobilaria.GyL.service.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OfferService offerService;

    @GetMapping("/listaUsuarios")
    public String listUsers(ModelMap model) {

        List<User> users = userService.listUsers();

        model.put("users", users);

        return "into.html";
    }

    @GetMapping("/busquedaUsuarios")
    public String searchUsers(@RequestParam String word, ModelMap model) {

        List<User> users = userRepository.findByName(word);

        model.put("users", users);

        return "into.html";
    }

    @PostMapping("/cambiarRol/{id}")
    public String modifyRole(@RequestParam String role, @PathVariable Long id){

        userService.adminModifyRole(id,role);

        return "redirect:/admin/listaUsuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable Long id){
        offerService.adminDeleteUser(id);
        return "redirect:/admin/listaUsuarios";
    }
}
