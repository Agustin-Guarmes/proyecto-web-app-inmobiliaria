package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.service.impl.PropertyService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AppController {

    private PropertyService propertyService;

    public AppController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    @GetMapping("/")
    public String index(ModelMap model) {
        List<Property> properties = propertyService.listRandomProperties();
        model.put("properties",properties);
        model.put("title", "MrHouse | Inicio");
        return "index.html";
    }
}
