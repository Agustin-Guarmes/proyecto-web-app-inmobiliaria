package Inmobilaria.GyL.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Appointment extends JpaRepository<Appointment, Long> {
}
