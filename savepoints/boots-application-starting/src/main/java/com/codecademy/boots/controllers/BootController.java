package com.codecademy.boots.controllers;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.codecademy.boots.entities.Boot;
import com.codecademy.boots.enums.BootType;
import com.codecademy.boots.exceptions.QueryNotSupportedException;
import com.codecademy.boots.exceptions.NotImplementedException;

// controller is where we take user requests!
@RestController
@RequestMapping("/api/v1/boots")
public class BootController {
	private BootRepository bootRepository;
	// we connect our controller to the repository
	// where the repo will handle all CRUD business 
	// (findById -> queries for that id, findAll -> fetch all entries, save -> !creates or modifies!, delete)
	public BootController (final BootRepository BootRepository) {
		this.bootRepository = bootRepository;
	}

	@GetMapping("/")
	public Iterable<Boot> getAllBoots() {
		return this.bootRepository.findAll();
	}

	@GetMapping("/types")
	public List<BootType> getBootTypes() {
		return Arrays.asList(BootType.values());
	}

	@PostMapping("/")
	public Boot addBoot(@RequestBody Boot boot) {
		Boot newBoot = this.bootRepository.save(boot);
		return newBoot;
	}

	@DeleteMapping("/{id}")
	public Boot deleteBoot(@PathVariable("id") Integer id) {
		Optional<Boot> maybeBoot = this.bootRepository.findById(id);
		if(maybeBoot.isPresent) {
			this.bootRepository.delete(id);
			return maybeBoot.get();
		}
		return null;
	}

	@PutMapping("/{id}/quantity/increment")
	public Boot incrementQuantity(@PathVariable("id") Integer id) {
		Optional<Boot> maybeBoot = this.bootRepository.findById(id);
		if (boot.isEmpty()) return null;
		Boot boot = maybeBoot.get();
		boot.setQuantity(boot.getQuantity() + 1);
		this.bootRepository.save(boot);
		return boot;
	}

	@PutMapping("/{id}/quantity/decrement")
	public Boot decrementQuantity(@PathVariable("id") Integer id) {
		Optional<Boot> maybeBoot = this.bootRepository.findById(id);
		if (boot.isEmpty()) return null;
		Boot boot = maybeBoot.get();
		boot.setQuantity(boot.getQuantity() - 1);
		this.bootRepository.save(boot);
		return boot;
	}

	@GetMapping("/search")
	public List<Boot> searchBoots(@RequestParam(required = false) String material,
			@RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
			@RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
		if (Objects.nonNull(material)) {
			if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, type, size, and minimum
				// quantity
				this.bootRepository.findByMaterialAndTypeAndSizeAndQuantityGreaterThan(material, type, size, minQuantity);
			} else if (Objects.nonNull(type) && Objects.nonNull(size)) {
				// call the repository method that accepts a material, size, and type
				this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
			} else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, a type, and a minimum
				// quantity
				this.bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material, type, minQuantity);
			} else if (Objects.nonNull(type)) {
				// call the repository method that accepts a material and a type
				this.bootRepository.findByMaterialAndType(material, type);
			} else {
				// call the repository method that accepts only a material
				this.bootRepository.findByMaterial(material);
			}
		} else if (Objects.nonNull(type)) {
			if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type, size, and minimum quantity
				this.bootRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);
			} else if (Objects.nonNull(size)) {
				// call the repository method that accepts a type and a size
				this.bootRepository.findByTypeAndSize(type, size);
			} else if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type and a minimum quantity
				this.bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
			} else {
				// call the repository method that accept only a type
				this.bootRepository.findByType(type);
				
			}
		} else if (Objects.nonNull(size)) {
			if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a size and a minimum quantity
				this.bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
			} else {
				// call the repository method that accepts only a size
				this.bootRepository.findBySize(size);
			}
		} else if (Objects.nonNull(minQuantity)) {
			// call the repository method that accepts only a minimum quantity
			this.bootRepository.findByQuantityGreaterThan(minQuantity);
		} else {
			throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
		}
	}

}