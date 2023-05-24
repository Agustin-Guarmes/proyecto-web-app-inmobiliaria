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

    public List<Offer> findByProperty(Long id) {
        return offerRepository.findByProperty(id);
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
        Property property = propertyService.findById(propertyId);
        Offer offer = new Offer();
        offer.setProperty(property);
        offer.setUser(clientId);
        offer.setPrice(property.getPrice());
        offer.setOfferStatus(OfferStatus.CLIENT_OFFER);
        offerRepository.save(offer);
    }


    /*Ente */
    public void offerResponse(Long userId, Long offerId, String response) {
        Offer offer = offerRepository.findById(offerId).get();
        User user = userService.getOne(userId);
        if (user.getRole().equals(Role.ENTITY)) {
            entityResponse(offer, response);
        } else if (user.getRole().equals(Role.CLIENT)) {
            clientResponse(offer, response);
        }
        offerRepository.save(offer);
    }

    private void entityResponse(Offer offer, String response) {
        if (response.equalsIgnoreCase("Accept")) {
            offer.setOfferStatus(OfferStatus.ENTITY_ACCEPTED);
        } else {
            offer.setOfferStatus(OfferStatus.ENTITY_REJECTED);
        }
    }

    private void clientResponse(Offer offer, String response) {
        if (offer.getOfferStatus().equals(OfferStatus.ENTITY_ACCEPTED) && response.equalsIgnoreCase("Accept")) {
            propertyService.changeUser(offer.getProperty(), userService.getOne(offer.getUser()));
        }
        offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
    }


    @Transactional
    public void adminDeleteUser(Long id) {
        User user = userService.getOne(id);

        System.out.println(user.getIcon().getId());
        if (!user.getRole().equals(Role.ADMIN)) {
            if ((!findByUser(id).isEmpty())) {
                for (Offer offer : findByUser(id)) {
                    deleteOffer(offer.getId());
                    user.getProperties().remove(offer.getProperty());
                    propertyService.deleteProperty(offer.getProperty().getId());
                }
            }
            if (!user.getProperties().isEmpty()) {
                for (Property property : user.getProperties()) {
                    propertyService.deleteProperty(property.getId());
                }
            }
        }
        userService.deleteImgUser(user.getIcon().getId());
        userService.deleteUser(id);
    }

}
