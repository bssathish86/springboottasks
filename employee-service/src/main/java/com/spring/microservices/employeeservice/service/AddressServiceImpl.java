package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addRepository;

	@Override
	public List<Address> findAll() {
		return addRepository.findAll();
	}

	@Override
	public Optional<Address> findAddressById(Long id) {
		return addRepository.findById(id);
	}

	@Override
	public Optional<Address> findAddressByPincode(int pincode) {
		return addRepository.findAddressByPincode(pincode);
	}

	@Override
	public Optional<Address> createAddress(Address address) {
		Address addressOptional = addRepository.save(address);
		return Optional.of(addressOptional);
	}

	@Override
	public Optional<Address> updateAddressById(Address address) {
		Address addressOptional = addRepository.saveAndFlush(address);
		return Optional.of(addressOptional);
	}

	@Override
	public void deleteAddressById(Long id) {
		addRepository.deleteById(id);
	}

	@Override
	public void deleteAllAddress() {
		addRepository.deleteAll();
	}

	@Override
	public boolean isExist(Address address) {

		Optional<Address> addressDB = findAddressByPincode(address.getPincode());

		if (addressDB.isPresent())
			return address.equals(addressDB.get());

		return false;
	}

}
