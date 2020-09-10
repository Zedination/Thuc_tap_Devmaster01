package com.devmaster.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.dao.SanPhamDao;
import com.devmaster.dao.TinTucDao;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.model.ModelProduct;
import com.devmaster.model.NewsInfo;
import com.devmaster.model.PhanTrangInfo;
import com.devmaster.model.ProductInfo;
import com.devmaster.model.Top4Product;

@Controller
public class BaseController {

	@Autowired
	private SanPhamDao sanPhamDao;

	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;

	@Autowired
	private TinTucDao tinTucDao;
	
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
	@GetMapping("/logoutSuccessful")
	public String get_logout(Model model) {
		model.addAttribute("title", "Đăng nhập quản trị");
		return "login";
	}

	@GetMapping("/tin-tuc")
	public String get_tin_tuc(Model model, @RequestParam(name = "p", defaultValue = "1") int position) {
		model.addAttribute("title", "Tin tức");
		List<NewsInfo> data = tinTucDao.getListNews();
		long skiper = 0;
		for(int i = 1;i<position;i++) {
			skiper=skiper+12;
		}
		long count = data.size();
		List<NewsInfo> afterFilter = data.stream().skip(skiper).limit(12).collect(Collectors.toList());
		model.addAttribute("listTinTuc", afterFilter);
		model.addAttribute("activePosition", position);
		List<Integer> lstPages = new ArrayList<Integer>();
		for(int i = 0; i<((int) count/12)+1;i++) {
			lstPages.add(1);
		}
		model.addAttribute("lstPages", lstPages);
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
		lst.forEach(e -> {
			ModelProduct temp = new ModelProduct();
			temp.setMaLoai(String.valueOf(e.getMaLoai()));
			temp.setTenLoai(e.getTenLoai());
			temp.setTop4(sanPhamDao.lstTop4Product(e.getMaLoai()));
			datas.add(temp);
		});
		model.addAttribute("listGroupProduct", datas);
		return "sanphams";
	}

	@GetMapping("/tat-ca-san-pham")
	public String get_all_san_pham(Model model, @RequestParam("maLoai") long maLoai,
			@RequestParam(name = "mucGia", defaultValue = "0") String mucGia,
			@RequestParam(name = "sortBy", defaultValue = "") String sortBy,
			@RequestParam(name = "position", defaultValue = "1") int position) {
		model.addAttribute("title", "Tất cả sản phẩm");
		LoaiSanPham loaiSanPham = loaiSanPhamDao.getLoaiSPByID(maLoai);
		ModelProduct temp = new ModelProduct();
		temp.setMaLoai(String.valueOf(loaiSanPham.getMaLoai()));
		temp.setTenLoai(loaiSanPham.getTenLoai());
		long totalRecords = sanPhamDao.listAllProductByType(loaiSanPham.getMaLoai())
				.stream()
				.filter(x->{
					if(mucGia.equals("0")) {
						return true;
					}
					else if(mucGia.equals("5")) {
						return Double.valueOf(x.getPrice())<Double.valueOf(5000000);
					}else if(mucGia.equals("5-10")) {
						return Double.valueOf(x.getPrice())>=Double.valueOf(5000000)&&Double.valueOf(x.getPrice())<Double.valueOf(10000000);
					}else if(mucGia.equals("10-15")) {
						return Double.valueOf(x.getPrice())>=Double.valueOf(10000000)&&Double.valueOf(x.getPrice())<Double.valueOf(15000000);
					}else {
						return Double.valueOf(x.getPrice())>=Double.valueOf(15000000);
					}
				}).sorted((s1,s2)-> {
					if(sortBy.equals("")) {
						return Double.valueOf(s2.getPrice()).compareTo(Double.valueOf(s1.getPrice()));
					}else {
						return Double.valueOf(s2.getPrice()).compareTo(Double.valueOf(s1.getPrice()));
					}
				})
				.count();
		long skiper = 0;
		for(int i = 1;i<position;i++) {
			skiper=skiper+12;
		}
		List<Top4Product> afterFilters = sanPhamDao.listAllProductByType(loaiSanPham.getMaLoai())
				.stream()
				.filter(x->{
					if(mucGia.equals("0")) {
						return true;
					}
					else if(mucGia.equals("5")) {
						return Double.valueOf(x.getPrice())<Double.valueOf(5000000);
					}else if(mucGia.equals("5-10")) {
						return Double.valueOf(x.getPrice())>=Double.valueOf(5000000)&&Double.valueOf(x.getPrice())<Double.valueOf(10000000);
					}else if(mucGia.equals("10-15")) {
						return Double.valueOf(x.getPrice())>=Double.valueOf(10000000)&&Double.valueOf(x.getPrice())<Double.valueOf(15000000);
					}else {
						return Double.valueOf(x.getPrice())>=Double.valueOf(15000000);
					}
				}).sorted((s1,s2)-> {
					if(sortBy.equals("")) {
						return Double.valueOf(s2.getPrice()).compareTo(Double.valueOf(s1.getPrice()));
					}else if(sortBy.equals("priceAsc")){
						return Double.valueOf(s1.getPrice()).compareTo(Double.valueOf(s2.getPrice()));
					}else if(sortBy.equals("priceDesc")){
						return Double.valueOf(s2.getPrice()).compareTo(Double.valueOf(s1.getPrice()));
					}else {
						return (int) (s1.getId() - s2.getId());
					}
				}).skip(skiper).limit(12)
				.collect(Collectors.toList());
		temp.setTop4(afterFilters);
		model.addAttribute("groupProduct", temp);
		PhanTrangInfo info = new PhanTrangInfo();
		info.setMaLoai(maLoai);
		info.setTenLoai(loaiSanPham.getTenLoai());
		info.setMucGia(mucGia);
		info.setSortBy(sortBy);
		info.setPosition(position);
		info.setTotalRecords((int)totalRecords);
		List<Integer> lstPages = new ArrayList<Integer>();
		for(int i = 0; i<((int) totalRecords/12)+1;i++) {
			lstPages.add(1);
		}
		model.addAttribute("lstPages", lstPages);
		model.addAttribute("phanTrangInfo", info);
		return "tatcasanphams";
	}

	@GetMapping(path = { "/", "/index" })
	public String index(Model model) {
		model.addAttribute("title", "Trang chủ");
		List<Top4Product> list1 = this.sanPhamDao.lstTop8Product().stream().limit(4).collect(Collectors.toList());
		List<Top4Product> list2 = this.sanPhamDao.lstTop8Product().stream().skip(4).limit(4).collect(Collectors.toList());
		model.addAttribute("list1", list1);
		model.addAttribute("list2", list2);
		List<NewsInfo> top3News = this.tinTucDao.getListNews().stream().sorted((s1,s2)->(int) (s2.getId() - s1.getId())).skip(1).limit(3).collect(Collectors.toList());
		model.addAttribute("top3News",top3News);
		try {
			NewsInfo firstNews = this.tinTucDao.getListNews().stream().sorted((s1,s2)->(int) (s2.getId() - s1.getId())).limit(1).collect(Collectors.toList()).get(0);
			model.addAttribute("firstNews",firstNews);
		} catch (Exception e) {
			model.addAttribute("firstNews","");
		}
		return "home";
	}

	@GetMapping("/bai-viet")
	public String get_bai_viet(Model model, @RequestParam("id") long id) {
		model.addAttribute("title", "Bài viết");
		model.addAttribute("tinTuc", this.tinTucDao.getNewsById(id));
		List<NewsInfo> top3News = this.tinTucDao.getListNews().stream().sorted((s1,s2)->(int) (s2.getId() - s1.getId())).limit(3).collect(Collectors.toList());
		model.addAttribute("top3News",top3News);
		return "baiviet";
	}

	@GetMapping("/chi-tiet-san-pham")
	public String get_chi_tiet_san_pham(Model model, @RequestParam("maSanPham") long maSanPham) {
		ProductInfo info = this.sanPhamDao.getProductInfo(maSanPham);
		model.addAttribute("product",info);
		model.addAttribute("title", "Chi tiết sản phẩm");
		ModelProduct data = new ModelProduct();
		List<LoaiSanPham> lst = loaiSanPhamDao.getLoaiSP();
		model.addAttribute("listLoaiSanPham", lst);
		data.setMaLoai(info.getMaLoai());
		data.setTenLoai(info.getTenLoai());
		data.setTop4(this.sanPhamDao.lstTop4Product(Long.valueOf(info.getMaLoai())));
		model.addAttribute("listGroupProduct", data);
		return "chitietsanpham";
	}
}
