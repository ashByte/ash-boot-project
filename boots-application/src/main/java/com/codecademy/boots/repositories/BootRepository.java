package com.codecademy.boots.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.boots.entities.Boot;

public interface BootRepository extends CrudRepository<Boot, Integer> {
}