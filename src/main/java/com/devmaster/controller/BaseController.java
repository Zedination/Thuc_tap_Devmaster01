package com.devmaster.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.model.ModelProduct;

@Controller
public class BaseController {

	@Autowired
	private SanPhamDao sanPhamDao;
	
	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	
	@GetMapping("/fragments")
	public String get_fragments() {
		return "fragments";
	}
	
	@GetMapping("/gioi-thieu")
	public String get_gioi_thieu(Model model) {
		model.addAttribute("title", "Giới thiệu");
		return "gioi-thieu";
	}
	@GetMapping("/lien-he")
	public String get_lien_he(Model model) {
		model.addAttribute("title", "Liên hệ với chúng tôi");
		return "lien-he";
	}
	@GetMapping("/login")
	public String get_login(Model model) {
		model.addAttribute("title", "Đăng nhập quản trị");
		return "login";
	}
	@GetMapping("/tin-tuc")
	public String get_tin_tuc(Model model) {
		model.addAttribute("title", "Tin tức");
		return "tin-tuc";
	}
	@GetMapping("/doi-tac")
	public String get_doi_tac(Model model) {
		model.addAttribute("title", "Đối tác");
		return "doi-tac";
	}
	@GetMapping("/san-pham")
	public String get_san_pham(Model model) {
		model.addAttribute("title", "Sản phẩm");
		List<ModelProduct> datas = new ArrayList<ModelProduct>();
		List<LoaiSanPham> lst = loaiSanPhamDao.getLoaiSP();
		model.addAttribute("listLoaiSanPham", lst);
		lst.forEach(e->{
			ModelProduct temp = new ModelProduct();
			temp.setTenLoai(e.getTenLoai());
			temp.setTop4(sanPhamDao.lstTop4Product(e.getMaLoai()));
			datas.add(temp);
		});
		model.addAttribute("listGroupProduct",datas);
		return "sanphams";
	}
	@GetMapping("/tat-ca-san-pham")
	public String get_all_san_pham(Model model) {
		model.addAttribute("title", "Tất cả sản phẩm");
		return "tatcasanphams";
	}
	@GetMapping(path = {"/","/index"})
	public String index(Model model) {
		model.addAttribute("title", "Trang chủ");
		return "home";
	}
	@GetMapping("/bai-viet")
	public String get_bai_viet(Model model) {
		model.addAttribute("title", "Bài viết");
		return "baiviet";
	}
	@GetMapping("/chi-tiet-san-pham")
	public String get_chi_tiet_san_pham(Model model) {
		model.addAttribute("title", "Chi tiết sản phẩm");
		return "chitietsp";
	}
}
