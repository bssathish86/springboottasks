package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.employeeservice.entity.Address;

public interface AddressService {

	List<Address> findAll();

	Optional<Address> findAddressById(Long id);

	Optional<Address> findAddressByPincode(int pincode);

	Optional<Address> createAddress(Address address);

	Optional<Address> updateAddressById(Address address);

	void deleteAddressById(Long id);

	void deleteAllAddress();

	boolean isExist(Address address);

}
