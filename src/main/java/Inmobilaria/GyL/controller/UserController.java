package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.exception.AlreadyExistsException;
import Inmobilaria.GyL.service.IPropertyService;
import Inmobilaria.GyL.service.impl.UserService;
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

    private final IPropertyService propertyService;
    private final UserService userService;

    public UserController(IPropertyService propertyService, UserService userService) {
        this.propertyService = propertyService;
        this.userService = userService;
    }

    @GetMapping("/registrarse")
    public String register(ModelMap model) {
        model.put("title", "MrHouse | Registro");
        return "register.html";
    }

    @PostMapping("/registrar")
    public String registered(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam Long dni, @RequestParam String role, MultipartFile icon,ModelMap model) throws Exception {
        try {
            userService.createUser(email, password, name, dni, role, icon);
            return "redirect:/usuario/iniciarSesion";
        } catch (AlreadyExistsException ex) {
            model.put("dniRegistrado", ex.getMessage());
            return "register.html";
        }
    }

    @GetMapping("/iniciarSesion")
    public String login(ModelMap model) {
        model.put("title", "MrHouse | Ingreso");
        return "login.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/restablecerContrasena")
    public String resetPassword(ModelMap model) {
        model.put("title", "MrHouse | Contraseña");
        return "resetPassword.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/perfil")
    public String profile(ModelMap model) {
        model.put("title", "MrHouse | Perfil");
        return "profile.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN','ROLE_ENTITY')")
    @PostMapping("/perfil/{id}")
    public String updateProfile(@PathVariable Long id, @RequestParam String name, @RequestParam String password, MultipartFile icon) {
        if (icon.getContentType().equalsIgnoreCase("application/octet-stream")) {
            userService.modifyUser(id, name, password);
        } else {
            userService.modifyUser(id, name, password, icon);
        }
        return "redirect:/usuario/perfil";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ENTITY')")
    @PostMapping("/agregarPropiedad")
    public String addProperty(@RequestParam Long idUser, @RequestParam String address, @RequestParam String location,@RequestParam String province, @RequestParam String status, @RequestParam String type, @RequestParam int surface, @RequestParam double price, @RequestParam String description, @RequestParam MultipartFile[] files, @RequestParam int bathrooms, @RequestParam int bedrooms) {
        try {
            propertyService.createProperty(userService.getOne(idUser), address, location, province, status, type, surface, price, description, Arrays.asList(files), bathrooms, bedrooms);
        } catch (IOException ex) {
            ex.getMessage();
        }
        return "redirect:/usuario/propiedades/" + idUser;
    }

    @PreAuthorize("hasAnyRole('ROLE_ENTITY')")
    @GetMapping("/propiedades/{id}")
    public String listProperties(@PathVariable Long id, ModelMap model) {
        List<Property> properties = propertyService.findByUser(id);
        model.put("properties", properties);
        model.put("title", "MrHouse | Propiedades");
        return "myProperties.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @GetMapping("/propiedadesCliente/{id}")
    public String listPropertiesClient(@PathVariable Long id, ModelMap model) {
        List<Property> properties = propertyService.clientProperties(id);
        model.put("properties", properties);
        model.put("title", "MrHouse | Propiedades");
        return "myProperties.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ENTITY')")
    @GetMapping("/gestionEnidad")
    public String enteManagement(ModelMap model) {
        model.put("title", "MrHouse | Gestion");
        return "enteManagement.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN','ROLE_ENTITY')")
    @GetMapping("/contraseña/{id}")
    public String modifyPassword(@PathVariable Long id) {
        return "resetPassword.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_ADMIN','ROLE_ENTITY')")
    @PostMapping("/contraseña/{id}")
    public String modifyPassword(@PathVariable Long id, @RequestParam String password, @RequestParam String newPassword) {
        userService.modifyUserPassword(id, password, newPassword);
        return "redirect:/usuario/perfil";
    }

    @GetMapping("/{user_id}/turnos")
    public String getAppointmentsForUser(@PathVariable("user_id") Long userId) {
        return "appointments.html";
    }

    @PostMapping("/invitado")
    public String searchDni(@RequestParam Long dni){
       if(userService.findByDNI(dni) == null){
           return "redirect:/usuario/registrarse";
       } else {
           return "redirect:/usuario/iniciarSesion";
       }
    }
}



