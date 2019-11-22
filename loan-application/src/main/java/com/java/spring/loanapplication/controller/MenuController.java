package com.java.spring.loanapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.java.spring.loanapplication.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService service;

	@GetMapping(name = "/menus")
	public RedirectView findAll(RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("menus", service.findAll());
		return new RedirectView("/home", true);
	}
}
