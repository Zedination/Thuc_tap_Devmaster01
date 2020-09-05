package com.devmaster.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.dao.LoaiSanPhamDao;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.formdata.FormDataType;


@RestController
public class RestLoai {
	@Autowired
	private LoaiSanPhamDao loaiSanPhamDao;
	
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
	@PostMapping(path = "/create-type",consumes = "multipart/form-data")
	public String test(FormDataType type) throws IOException {
		return loaiSanPhamDao.createType(type);
	}
	
	@PostMapping(path = "/edit-type",consumes = "multipart/form-data")
	public String edit(FormDataType type) throws IOException {
		return loaiSanPhamDao.edittype(type);
	}
	
	@PostMapping(path = "/delete-type",consumes = "multipart/form-data")
	public String delete(FormDataType type) throws IOException {
		return loaiSanPhamDao.deletetype(type);
	}
	
}
