package Inmobilaria.GyL.controller;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.service.IOfferService;
import Inmobilaria.GyL.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ofertas")
public class OfferController {

    private final UserService userService;
    private final IOfferService offerService;

    public OfferController(UserService userService, IOfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @GetMapping("/realizar/user/{userId}/property/{propertyId}")
    public String makeOffer(@PathVariable("userId") Long userId, @PathVariable("propertyId") Long propertyId) {
        offerService.createOffer(propertyId, userId);
        return "redirect:/ofertas/listaCliente/" + userId;
    }

    @GetMapping("/listaCliente/{id}")
    public String listOffersClient(@PathVariable Long id, ModelMap model) {
        List<Offer> offers = offerService.findByUser(id);
        model.put("offers", offers);
        return "listOffersClient";
    }

    @GetMapping("/listaPropiedad/{id}")
    public String listOffersProperty(@PathVariable Long id, ModelMap model) {
        List<Offer> offers = offerService.findByProperty(id);
        model.put("offers", offers);
        return "listOffersEntity";
    }

    @GetMapping("/respuesta/user/{userId}/offer/{offerId}/response/{response}")
    public String response(@PathVariable("userId") Long userId, @PathVariable("offerId") Long offerId, @PathVariable("response") String response) {

        offerService.offerResponse(userId, offerId, response);

        Long idProperty = offerService.getOne(offerId).getProperty().getId();

        if (userService.getOne(userId).getRole().toString().equals("ENTITY")) {
            return "redirect:/ofertas/listaPropiedad/" + idProperty;
        } else {
            return "redirect:/ofertas/listaCliente/" + userId;
        }
    }
}
