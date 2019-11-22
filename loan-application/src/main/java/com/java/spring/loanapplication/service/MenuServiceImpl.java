package com.java.spring.loanapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.spring.loanapplication.entity.Menu;
import com.java.spring.loanapplication.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository repository;

	@Override
	public List<Menu> findAll() {
		return repository.findAll();
	}

}
