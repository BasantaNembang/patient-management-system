package com.patent.repository;

import com.patent.entity.Patent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatentRepository extends JpaRepository<Patent, String> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, String id);


}
