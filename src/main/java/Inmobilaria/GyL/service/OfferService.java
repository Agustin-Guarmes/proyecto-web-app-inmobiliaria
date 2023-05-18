package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.OfferRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OfferService {

    private OfferRepository offerRepository;

    @Transactional
    public void createOffer(Property property, User user, Double price) {

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

}
