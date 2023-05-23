package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.service.impl.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ofertas")
public class OfferController {

    @Autowired
    private OffersService offerService;

    @PostMapping("/realizar")
    public String makeOffer(@RequestParam Long userId, @RequestParam Long propertyId, @RequestParam Double price) {
        offerService.createOffer(propertyId, userId, price);
        return "alguna-pagina.html";
    }

    @GetMapping("/listar/{id}")
    public String listOffers(@PathVariable Long id, ModelMap model) {
        List<Offer> offers = offerService.findByUser(id);
        model.put("offers", offers);
        return "alguna-pagina.html";
    }

}
