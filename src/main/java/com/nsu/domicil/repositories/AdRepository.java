package com.nsu.domicil.repositories;

import com.nsu.domicil.entities.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {
    boolean existsBySourceUrl(String sourceUrl);

    Ad findFirstBySourceUrl(String sourceUrl);
}
