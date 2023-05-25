package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.Offer;

import java.util.List;

public interface IOfferService {
    Offer getOne(Long id);

    void updateOffer(Long id, Double price);

    void deleteOffer(Long id);

    List<Offer> findByProperty(Long id);

    List<Offer> findByUser(Long id);

    void createOffer(Long propertyId, Long clientId);


    /*Ente */
    public void offerResponse(Long userId, Long offerId, String response);
}
