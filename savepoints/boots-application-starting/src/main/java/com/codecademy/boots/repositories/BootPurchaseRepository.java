package com.codecademy.boots.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.boots.entities.BootPurchase;
import com.codecademy.boots.enums.BootType;

public interface BootPurchaseRepository extends CrudRepository <BootPurchase, Integer> {
}