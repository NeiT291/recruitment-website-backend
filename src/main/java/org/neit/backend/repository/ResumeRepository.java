package org.neit.backend.repository;

import org.neit.backend.entity.Company;
import org.neit.backend.entity.Resume;
import org.neit.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository <Resume,Integer>{
    Page<Resume> findAllByUser_Username(String username, Pageable pageable);
    Page<Resume> findAllByJob_Company(Company company, Pageable pageable);
}
