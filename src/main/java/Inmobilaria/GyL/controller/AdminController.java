package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.IImageService;
import Inmobilaria.GyL.service.IPropertyService;
import Inmobilaria.GyL.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final IImageService iImageService;
    private final IPropertyService iPropertyService;

    public AdminController(UserService userService, IImageService iImageService, IPropertyService iPropertyService) {
        this.userService = userService;
        this.iImageService = iImageService;
        this.iPropertyService = iPropertyService;
    }

    @GetMapping("/")
    public String listUsers(ModelMap model) {
        List<User> users = userService.listUsers();
        List<Property> properties = iPropertyService.listProperties();
        model.put("properties", properties);
        model.put("users", users);
        model.put("title", "MrHouse | ADMIN");
        return "into.html";
    }

    @GetMapping("/busqueda")
    public String searchAdmin(@RequestParam String word, ModelMap model) {

        List<User> users = userService.findByName(word);
        if (!users.isEmpty()) {
            model.put("properties", iPropertyService.findByUserName(word));
            model.put("users", users);
        } else {
            try {
                User user = userService.findByDNI(Long.parseLong(word));
                if (user != null) {
                    model.put("properties", iPropertyService.findByUser(user.getId()));
                    model.put("users", user);
                }
            } catch (NumberFormatException e){
                model.put("title", "MrHouse | Busqueda");
                return "into.html";
            }
        }
        model.put("title", "MrHouse | Busqueda");
        return "into.html";
    }

    @PostMapping("/modificarUsuario")
    public String modifyUser(@RequestParam Long id, @RequestParam String role, @RequestParam String name, @RequestParam Long dni, @RequestParam String email, @RequestParam String status) {
        userService.adminModifyUser(id, name, dni, role, email, status);
        return "redirect:/admin/";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.adminDeleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("/crearImg")
    public String adminViewImg(ModelMap model) {
        model.put("title", "MrHouse | Usuarios");
        return "adminCreateImg";
    }

    @PostMapping("/creaImg")
    public String adminCreateIcon(MultipartFile icon) throws Exception {
        iImageService.submitImg(icon);
        return "redirect:/admin/crearImg";
    }

}
