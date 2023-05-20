package Inmobilaria.GyL.repository;

import Inmobilaria.GyL.entity.Offer;
import Inmobilaria.GyL.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.user.id = ?1")
    List<Offer> findByUser(Long id);
    @Query("SELECT o FROM Offer o WHERE o.property.user.id = ?1")
    List<Offer> findByEntity(Long id);
}
