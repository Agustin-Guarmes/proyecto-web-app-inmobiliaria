package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.enums.OfferStatus;
import Inmobilaria.GyL.enums.PropertyStatus;
import Inmobilaria.GyL.enums.Role;
import Inmobilaria.GyL.repository.OfferRepository;
import Inmobilaria.GyL.service.IOfferService;
import Inmobilaria.GyL.service.IPropertyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final IPropertyService propertyService;
    private final UserService userService;
    private Long id;

    public OfferService(OfferRepository offerRepository, IPropertyService propertyService, UserService userService) {
        this.offerRepository = offerRepository;
        this.propertyService = propertyService;
        this.userService = userService;
    }

    @Override
    public Offer getOne(Long id) {
        return offerRepository.getOne(id);
    }

    @Override
    public void updateOffer(Long id, Double price) {
        Optional<Offer> response = offerRepository.findById(id);
        if (response.isPresent()) {
            Offer offer = response.get();
            offer.setPrice(price);
            offerRepository.save(offer);
        }
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<Offer> findByProperty(Long id) {
        return offerRepository.findByProperty(id);
    }

    @Override
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

    @Override
    public void createOffer(Long propertyId, Long clientId) {
        List<Offer> validOffers = offerRepository.findActiveOfferByUserAndProperty(propertyId, clientId);
        if (validOffers.isEmpty()) {
            Property property = propertyService.findById(propertyId);
            Offer offer = new Offer();
            offer.setProperty(property);
            offer.setUser(userService.getOne(clientId));
            offer.setPrice(property.getPrice());
            offer.setOfferStatus(OfferStatus.CLIENT_OFFER);
            offerRepository.save(offer);
        }
    }

    @Override
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

    private void setInactive(Long id) {
        List<Offer> offers = offerRepository.setInactive(id);
        if (offers != null) {
            for (Offer offer : offers) {
                offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
                offerRepository.save(offer);
            }
        }
    }

    private void clientResponse(Offer offer, String response) {
        if (offer.getOfferStatus().equals(OfferStatus.ENTITY_ACCEPTED) && response.equalsIgnoreCase("Accept") && offer.getProperty().getStatus().equals(PropertyStatus.FOR_SALE)) {
            propertyService.changeUser(offer.getProperty(), userService.getOne(offer.getUser().getId()));
            offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
            setInactive(offer.getProperty().getId());
        } else if (offer.getOfferStatus().equals(OfferStatus.ENTITY_ACCEPTED) && response.equalsIgnoreCase("Accept") && offer.getProperty().getStatus().equals(PropertyStatus.FOR_RENT)) {
            propertyService.rentProperty(offer.getProperty());
            offer.setOfferStatus(OfferStatus.CLIENT_ACCEPTED);
            setInactive(offer.getProperty().getId());
        } else {
            offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
        }
    }

    public void toggleActivePropertyAndOffers(Long id, boolean status) {
        Property property = propertyService.findById(id);
        property.setActive(Boolean.valueOf(status));
        propertyService.setPropertyState(property);
        for (Offer offer : property.getOffers()) {
            offer.setOfferStatus(OfferStatus.INACTIVE_OFFER);
            offerRepository.save(offer);
        }
    }

    @Override
    public void adminModifyUser(Long id, String name, Long dni, String role, String email, String status) {
        Optional<User> response = userService.findById(id);
        if (response.isPresent()) {
            User user = response.get();
            user.setName(name);
            user.setDni(dni);
            user.setEmail(email);
            if(user.isActive() != Boolean.valueOf(status)){
                for (Property property : user.getProperties()) {
                    toggleActivePropertyAndOffers(property.getId(), Boolean.valueOf(status));
                }
            }
            user.setActive(Boolean.valueOf(status));
            switch (role) {
                case "propietario":
                    user.setRole(Role.ENTITY);
                    break;
                case "admin":
                    user.setRole(Role.ADMIN);
                    break;
                default:
                    user.setRole(Role.CLIENT);
            }
            userService.updateUser(user);

        }
    }
}
