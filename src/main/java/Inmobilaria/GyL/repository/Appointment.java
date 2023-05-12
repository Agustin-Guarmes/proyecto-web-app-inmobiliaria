package Inmobilaria.GyL.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Appointment extends JpaRepository<Appointment, Long> {
}
