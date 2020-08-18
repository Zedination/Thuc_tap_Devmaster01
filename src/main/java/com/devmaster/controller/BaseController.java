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
}