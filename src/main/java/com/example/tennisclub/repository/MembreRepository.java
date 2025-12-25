package com.example.tennisclub.repository;

import com.example.tennisclub.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembreRepository extends JpaRepository<Membre, Long> {}
