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

    @GetMapping("/lista")
    public String propiedades(ModelMap model) {
        model.put("properties", propertyService.listProperties());
        return "propertiesTest.html";
    }

    @GetMapping("/formulario")
    public String formProperty() {
        return "formProperty.html";
    }

    @GetMapping("/modificar/{id}")
    public String updateProperty(@PathVariable Long id, ModelMap model) {
        model.put("property", propertyService.findById(id));
        model.put("idUser",id);
        return "updateProperty.html";
    }

    @PostMapping("/modificar/{idUser}")
    public String modifyProperty(@PathVariable Long idUser, @RequestParam Long id, @RequestParam String address, @RequestParam Double price, @RequestParam int surface) {
        propertyService.updateProperty(id, address, surface, price);

        return "redirect:/usuario/propiedades/" + idUser;
    }

    @DeleteMapping("/eliminar/{id}")
    public String deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return "redirect:/usuario/propiedades/1";
    }
}
