package org.neit.backend.repository;

import org.neit.backend.entity.City;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.Job;
import org.neit.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    Page<Job> findByUser(User user, Pageable pageable);
    List<Job> findByNameIgnoreCaseContaining(String name);
    Page<Job> findByCitiesIgnoreCaseContaining(Set<City> cities, Pageable pageable);
    Page<Job> findByNameAndCitiesIgnoreCaseContaining(String name, Set<City> cities, Pageable pageable);
    Page<Job> findByNameContainingIgnoreCaseAndCitiesContainingAndCompany(String name, Set<City> cities, Company company, Pageable pageable);
    List<Job> findByNameIgnoreCase(String name);
}
