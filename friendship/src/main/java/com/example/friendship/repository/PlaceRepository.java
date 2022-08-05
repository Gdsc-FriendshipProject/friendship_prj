package com.example.friendship.repository;

import com.example.friendship.entity.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Plans, Long> {
}
