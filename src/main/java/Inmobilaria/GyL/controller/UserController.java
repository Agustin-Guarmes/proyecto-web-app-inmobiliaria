package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.UserRepository;
import Inmobilaria.GyL.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
<<<<<<< HEAD
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, @RequestParam String role, MultipartFile icon){
=======
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, MultipartFile icon) {
>>>>>>> 0ebd2e75df6319b327bbdaaefb175f5adb034019
        try {
            userService.createUser(email, password, name, dni, role, icon);
            return "redirect:/usuario/";
        } catch (Exception ex) {
            ex.getMessage();
            return "redirect:/usuario/registro";
        }
    }
}
