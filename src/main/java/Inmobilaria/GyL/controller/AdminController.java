package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listaUsuarios")
    public String listUsers(ModelMap model) {

        List<User> users = userService.listUsers();

        model.put("users", users);

        return "into.html";
    }

    @GetMapping("/busquedaUsuarios")
    public String searchUsers(@RequestParam String word, ModelMap model) {

        List<User> users = userService.findByName(word);

        model.put("users", users);

        return "into.html";
    }

    @PostMapping("/cambiarRol/{id}")
    public String modifyRole(@RequestParam String role, @PathVariable Long id) {
        System.out.println(id);

        userService.adminModifyRole(id, role);

        return "redirect:/admin/listaUsuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.adminDeleteUser(id);
        return "redirect:/admin/listaUsuarios";
    }
}
