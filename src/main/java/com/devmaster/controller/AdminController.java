package com.devmaster.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devmaster.dao.HoaDonNhapDao;
import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.HoaDonNhap;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.entity.SanPham;
import com.devmaster.model.ModelProduct;

@Controller
public class AdminController {

	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	@Autowired
	private HoaDonNhapDao hoaDonNhapDao;
	@Autowired
	private SanPhamDao sanPhamDao;

	@GetMapping("/admin-fragments")
	public String admin_fragment() {
		return "admin/fragments";
	}

	@GetMapping("/admin-index")
	public String admin_index(Model model) {
		model.addAttribute("title", "Trang chủ");
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
		model.addAttribute("title", "Quản lý tin tức");
		return "admin/tin-tuc";
	}

	@GetMapping("/admin-hdn")
	public String get_hdn(Model model) {
		List<HoaDonNhap> lst = hoaDonNhapDao.getHDN();
		model.addAttribute("listHoaDonNhap", lst);
		List<SanPham> lstsanpham = hoaDonNhapDao.getcbSanPham();
		model.addAttribute("listSanPham", lstsanpham);
		return "admin/hoa-don-nhap";
	}

	@GetMapping("/admin-lien-he")
	public String lien_he(Model model) {
		model.addAttribute("title", "Liên hệ");
		return "admin/lien-he";
	}
}
