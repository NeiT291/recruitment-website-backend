package org.neit.backend.repository;

import org.neit.backend.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
    Optional<Profession> findByName(String professionName);
    List<Profession> findByNameContainingIgnoreCase(String professionName);
}
