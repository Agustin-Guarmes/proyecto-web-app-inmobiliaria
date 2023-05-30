package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.service.impl.PropertyService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AppController {

    private PropertyService propertyService;

    public AppController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    @GetMapping("/")
    public String index(ModelMap model, HttpSession session) {
        try{
            User log = (User) session.getAttribute("userSession");
            List<Property> properties = propertyService.listRandomProperties(log.getId());
            model.put("properties",properties);
        } catch(Exception ex) {
            List<Property> properties = propertyService.listRandomProperties(0L);
            model.put("properties",properties);
        }
        model.put("title", "MrHouse | Inicio");
        return "index.html";
    }
}
