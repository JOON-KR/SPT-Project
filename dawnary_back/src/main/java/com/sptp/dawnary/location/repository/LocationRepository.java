package com.sptp.dawnary.location.repository;

import com.sptp.dawnary.location.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
