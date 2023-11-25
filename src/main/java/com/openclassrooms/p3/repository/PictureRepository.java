package com.openclassrooms.p3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.p3.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByRentalId(Long rentalId);
}