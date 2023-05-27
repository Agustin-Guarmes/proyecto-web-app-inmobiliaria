package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.user.id = ?1")
    List<Offer> findByUser(Long id);

    @Query("SELECT o FROM Offer o WHERE o.property.user.id = ?1")
    List<Offer> findByEntity(Long id);

    @Query("SELECT o FROM Offer o WHERE o.property.id = ?1")
    List<Offer> findByProperty(Long id);

    @Query("SELECT o FROM Offer o WHERE o.property.id = ?1 AND (o.offerStatus = 'CLIENT_OFFER' OR o.offerStatus = 'ENTITY_ACCEPTED')")
    List<Offer> setInactive(Long id);
}
