package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.Role;
import Inmobilaria.GyL.repository.OfferRepository;
import Inmobilaria.GyL.service.UserService;
import Inmobilaria.GyL.service.impl.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Transactional
    public void createOffer(Long propertyId, Long userId, Double price) {
        User user = userService.getOne(userId);
        Property property = propertyService.findById(propertyId);
        Offer offer = new Offer();
        offer.setPrice(price);
        offer.setState(true);
        offer.setProperty(property);
        offer.setUser(user);

        offerRepository.save(offer);
    }

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
        List<Offer> offers = new ArrayList<>();
        User user = userService.getOne(id);
        if (user.getRole().equals(Role.CLIENT)) {
            offers = offerRepository.findByUser(id);
        } else {
//            for (Property property : user.getProperties()) {
//                for (Offer offer : property.getOffers()) {
//                    offers.add(offer);
//                }
//            }
            offers = offerRepository.findByEntity(id);
        }
        return offers;
    }

    public void acceptOffer(Long offerId) {
        Optional<Offer> offer = offerRepository.findById(offerId);
        if (offer.isPresent()) {
            Offer offerFound = offer.get();
            if (offerFound.getProperty().getStatus().equals(PropertyStatus.FOR_SALE)) {
                propertyService.changeUser(offerFound.getProperty(), offerFound.getUser());
                offerFound.setState(false);
            }
        }
    }

}
