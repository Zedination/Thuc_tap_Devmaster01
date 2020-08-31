package com.devmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.entity.LoaiSanPham;

@Controller
public class AdminController {
	
	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	
	@GetMapping("/admin-fragments")
	public String admin_fragment() {
		return "admin/fragments";
	}
	@GetMapping("/admin-index")
	public String admin_index() {
		return "admin/index";
	}
	@GetMapping("/admin-loai-san-pham")
	public String get_loai_sp() {
		return "admin/loai-san-pham";
	}
	@GetMapping("/admin-san-pham")
	public String get_product(Model model) {
		List<LoaiSanPham> lst = loaiSanPhamDao.getLoaiSP();
		model.addAttribute("listLoaiSanPham", lst);
		return "admin/san-pham";
	}
	@GetMapping("/admin-tin-tuc")
	public String tin_tuc(Model model) {
		model.addAttribute("title","Quản lý tin tức");
		return "admin/tin-tuc";
	}
}
