package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.service.impl.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/propiedades")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/modificar/{id}")
    public String updateProperty(@PathVariable Long id, ModelMap model) {
        model.put("property", propertyService.findById(id));
        return "updateProperty.html";
    }

    @PostMapping("/modificar")
    public String modifyProperty(@RequestParam Long id, @RequestParam String address, @RequestParam Double price, @RequestParam int surface) {
        propertyService.updateProperty(id, address, surface, price);
        return "redirect:/usuario/propiedades/1";
    }

    @DeleteMapping("/eliminar/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/usuario/propiedades/1";
    }
}
