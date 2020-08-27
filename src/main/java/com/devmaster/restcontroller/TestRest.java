package com.devmaster.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.entity.SanPham;

@RestController
public class TestRest {

	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	@Autowired
	private SanPhamDao sp;
	@GetMapping("/get-loaisp-test")
	public List<LoaiSanPham> get01() {
		return loaiSanPhamDao.getLoaiSP();
	}
}
