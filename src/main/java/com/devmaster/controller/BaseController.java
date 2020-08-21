package com.devmaster.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

	@GetMapping("/fragments")
	public String get_fragments() {
		return "fragments";
	}
	@GetMapping("/gioi-thieu")
	public String get_gioi_thieu() {
		return "gioi-thieu";
	}
	@GetMapping("/lien-he")
	public String get_lien_he() {
		return "lien-he";
	}
	@GetMapping("/login")
	public String get_login() {
		return "login";
	}
}
