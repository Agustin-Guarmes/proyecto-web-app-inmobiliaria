package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.UserRepository;
import Inmobilaria.GyL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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
}
