package com.spring.microservices.employeeservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.exception.AddressNotFoundException;
import com.spring.microservices.employeeservice.exception.CustomException;
import com.spring.microservices.employeeservice.service.AddressService;
import com.spring.microservices.employeeservice.service.EmployeeService;

@RestController
@Transactional
public class AddressController {

	@Autowired
	private AddressService service;

	@Autowired
	private EmployeeService empService;

	@GetMapping("/addresses")
	public List<Address> findAll() {
		return service.findAll();
	}

	@GetMapping("/addresses/{id}")
	public List<Address> findAddressById(@PathVariable Long... id) {

		List<Long> addressIds = Arrays.asList(id);

		List<Address> addresses = new ArrayList<>();

		for (Long addressId : addressIds) {

			Optional<Address> addressDB = service.findAddressById(addressId);

			if (!addressDB.isPresent())
				throw new AddressNotFoundException("id-" + addressId);
			addresses.add(addressDB.get());
		}
		return addresses;
	}

	@PostMapping("/addresses")
	public List<ResponseEntity<?>> createAddress(@Valid @RequestBody Address... address) {

		List<ResponseEntity<?>> response = new ArrayList<>();

		List<Address> addresses = Arrays.asList(address);
		URI location;

		for (Address addressTo : addresses) {

			if (service.isExist(addressTo)) {
				return Arrays.asList(new ResponseEntity<Object>(
						new CustomException(
								"Unable to create a address with pincode " + addressTo.getStreet() + " already exist."),
						HttpStatus.CONFLICT));
			}

			Optional<Address> addressDB = service.createAddress(addressTo);
			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(addressDB.get().getId()).toUri();
			response.add(ResponseEntity.created(location).build());
		}
		return response;
	}

	@PutMapping("/addresses/{id}")
	public ResponseEntity<Object> updateAddress(@PathVariable Long id, @Valid @RequestBody Address address) {

		Optional<Address> addressDB = service.findAddressById(id);

		if (!addressDB.isPresent())
			return ResponseEntity.notFound().build();

		address.setId(addressDB.get().getId());

		service.updateAddressById(address);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/addresses/{id}")
	public ResponseEntity<Object> deleteAddressById(@PathVariable Long... id) {

		List<Long> addressIds = Arrays.asList(id);

		for (Long addressId : addressIds) {

			Optional<Address> addressDB = service.findAddressById(addressId);

			if (!addressDB.isPresent())
				throw new AddressNotFoundException("id-" + addressId);

			empService.deleteEmployeeById(addressDB.get().getEmployee().getId());

			// service.deleteAddressById(addressId);
		}
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/addresses")
	public ResponseEntity<?> deleteAllAddress() {

		List<Address> addressList = service.findAll();

		if (addressList == null)
			return new ResponseEntity<>(new CustomException("Unable to delete. address not found."),
					HttpStatus.NOT_FOUND);

		service.deleteAllAddress();

		return ResponseEntity.noContent().build();
	}
}
