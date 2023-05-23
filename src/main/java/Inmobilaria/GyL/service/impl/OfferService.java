package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.OfferStatus;
import Inmobilaria.GyL.enums.Role;
import Inmobilaria.GyL.repository.OfferRepository;
import Inmobilaria.GyL.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;

    public Offer getOne(Long id) {
        return offerRepository.getOne(id);
    }

    @Transactional
    public void updateOffer(Long id, Double price) {

        Optional<Offer> response = offerRepository.findById(id);
        if (response.isPresent()) {

            Offer offer = response.get();
            offer.setPrice(price);
            offerRepository.save(offer);
        }
    }

    @Transactional
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    public List<Offer> findByUser(Long id) {
        List<Offer> offers;
        User user = userService.getOne(id);
        if (user.getRole().equals(Role.CLIENT)) {
            offers = offerRepository.findByUser(id);
        } else {
            offers = offerRepository.findByEntity(id);
        }
        return offers;
    }

    @Transactional
    public void createOffer(Long propertyId, Long clientId) {
        User client = userService.getOne(clientId);
        Property property = propertyService.findById(propertyId);
        Offer offer = new Offer();
        offer.setProperty(property);
        offer.setUser(client);
        offer.setPrice(property.getPrice());
        offer.setOfferStatus(OfferStatus.CLIENT_OFFER);

        offerRepository.save(offer);
    }


    /*Ente */
    public void offerRespond(Long userId, Long offerId, String response) {
        Offer offer = offerRepository.findById(offerId).get();
        User user = userService.getOne(userId);
        if (user.getRole().equals(Role.ENTITY)) {
            if (response.equalsIgnoreCase("Accept")) {
                offer.setOfferStatus(OfferStatus.ENTITY_ACCEPTED);
            } else {
                offer.setOfferStatus(OfferStatus.ENTITY_REJECTED);
            }
        } else if (user.getRole().equals(Role.CLIENT) && offer.getOfferStatus().equals(OfferStatus.ENTITY_ACCEPTED)) {
            if (response.equalsIgnoreCase("Accept")) {
                propertyService.changeUser(offer.getProperty(), user);
                offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
            }
            offerRepository.save(offer);
        }
    }

}
