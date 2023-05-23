package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.service.impl.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ofertas")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping("/realizar/user/{userId}/property/{propertyId}")
    public String makeOffer(@PathVariable("userId") Long userId, @PathVariable("propertyId") Long propertyId) {
        offerService.createOffer(propertyId, userId);
        return "redirect:/ofertas/listar/" + userId;
    }

    @GetMapping("/listar/{id}")
    public String listOffers(@PathVariable Long id, ModelMap model) {
        List<Offer> offers = offerService.findByUser(id);
        model.put("offers", offers);
        return "listOffers";
    }

    @GetMapping("/lista/{id}")
    public String listOffersProperty(@PathVariable Long id, ModelMap model) {
        List<Offer> offers = offerService.findByUser(id);
        model.put("offers", offers);
        return "listOffers";
    }

}
