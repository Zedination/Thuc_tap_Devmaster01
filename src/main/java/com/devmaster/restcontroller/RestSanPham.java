package com.devmaster.restcontroller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.Datatables;
import com.devmaster.formdata.FormDataProduct;
import com.devmaster.model.Top4Product;


@RestController
public class RestSanPham {
	
	
	@Autowired
	private SanPhamDao sanPhamDao;
	
	@GetMapping("/datatable-product")
	public Datatables getDataProduct() {
		return sanPhamDao.getDataProduct();
	}
	@PostMapping(path = "/create-product",consumes = "multipart/form-data")
	public String createPro(FormDataProduct product) throws IOException {
		return sanPhamDao.createProduct(product);
	}
	@PostMapping(path = "/update-product",consumes = "multipart/form-data")
	public String updatePro(FormDataProduct product) throws IOException {
		return sanPhamDao.updateProduct(product);
	}
	@DeleteMapping("/delete-product")
	public String deletePro(@RequestParam("id") long id) {
		return sanPhamDao.deleteProduct(id);
	}
	@GetMapping("/test-front")
	public List<Top4Product> test(@RequestParam("id") long id) {
		return sanPhamDao.lstTop4Product(id);
	}
}
