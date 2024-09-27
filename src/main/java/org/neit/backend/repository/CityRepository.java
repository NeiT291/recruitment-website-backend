package org.neit.backend.repository;

import org.neit.backend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByName(String name);
}
