package com.devmaster.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.formdata.FormDataProduct;


@RestController
public class RestSanPham {
	
	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	
	@Autowired
	private SanPhamDao sanPhamDao;
	
	@GetMapping("/datatable-product")
	public Datatables getDataProduct() {
		return sanPhamDao.getDataProduct();
	}
	@GetMapping("/datatable-type-product")
	public Datatables getDataTypeProduct() {
		List<List<String>> datatables = new ArrayList<List<String>>();
		List<LoaiSanPham> temp = loaiSanPhamDao.getLoaiSP();
		Datatables data = new Datatables();
		temp.forEach(e->{
			List<String> arr = Arrays.asList(String.valueOf(e.getMaLoai()),e.getTenLoai());
			datatables.add(arr);
		});
		data.setData(datatables);
		return data;
	}
	@PostMapping(path = "/create-product",consumes = "multipart/form-data")
	public String createPro(FormDataProduct product) throws IOException {
		return sanPhamDao.createProduct(product);
	}
	@PostMapping(path = "/update-product",consumes = "multipart/form-data")
	public String updatePro(FormDataProduct product) throws IOException {
		return sanPhamDao.updateProduct(product);
	}
}
