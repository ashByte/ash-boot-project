package com.codecademy.boots.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.enums.BootType;

// we enable this epository (data access / manipulation layer)
// crud operations by extending crudRepository. 

// the "...Integer>" here is the ID of each boot.
public interface BootRepository extends CrudRepository <Boot, Integer> {
    // we don't need to add anything here, because all the 
    // functionality is already in CrudRepository interface
    // we are inheriting all the good stuff :) 

    // here, as long as we follow the format of JPA query
    // creation, we don't have to do ANY heavy lifting. that's kinda magical lol

    // if you're lost we can go to spring docs -> query creation

    // and just like that we can find boots by the size, material, type...
    public List<Boot> findBySize(Float size);

    public List<Boot> findByMaterial(String material);

    public List<Boot> findByType(BootType type);

    public List<Boot> findByQuantityGreaterThan(Integer minQuantity);

    // remix of the above
    public List<Boot> findByMaterialAndTypeAndSizeAndQuantityGreaterThan(String material, BootType type, Float size, Integer minQuantity);
    
    public List<Boot> findByMaterialAndSizeAndType(String material, Float size, BootType type);

    public List<Boot> findByMaterialAndTypeAndQuantityGreaterThan(String material, BootType type, Integer minQuantity);

    public List<Boot> findByMaterialAndType(String material, BootType type);

    public List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size, Integer minQuantity);

    public List<Boot> findByTypeAndSize(BootType type, Float size);

    public List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer minQuantity);

    public List<Boot> findBySizeAndQuantityGreaterThan(Float size, Integer minQuantity);
    
    public List<Boot> findByIsBestsellerTrue();

}