package com.spring.microservices.employeeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.employeeservice.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findAddressByPincode(int pincode);

}
