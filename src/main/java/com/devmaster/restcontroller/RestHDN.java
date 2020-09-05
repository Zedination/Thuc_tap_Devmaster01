package com.devmaster.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.dao.HoaDonNhapDao;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.HoaDonNhap;
import com.devmaster.formdata.FormDataCTHDN;
import com.devmaster.formdata.FormDataHDN;

@RestController
public class RestHDN {

	@Autowired
	private HoaDonNhapDao hoaDonNhapDao;

	@GetMapping("/datatable-hdn")
	public Datatables getDataHDN() {
		List<List<String>> datatables = new ArrayList<List<String>>();
		List<HoaDonNhap> temp = hoaDonNhapDao.getHDN();
		Datatables data = new Datatables();
		temp.forEach(e -> {
			List<String> arr = Arrays.asList(String.valueOf(e.getSoPn()), e.getNgayNhap());
			datatables.add(arr);
		});
		data.setData(datatables);
		return data;
	}

	@PostMapping(path = "/create-hdn", consumes = "multipart/form-data")
	public String createhdn(FormDataHDN hdn) {
		return hoaDonNhapDao.createhdn(hdn);
	}

	@PostMapping(path = "/delete-hdn", consumes = "multipart/form-data")
	public String delete(FormDataHDN hdn) {
		return hoaDonNhapDao.deletehdn(hdn);
	}

	@PostMapping(path = "/edit-hdn", consumes = "multipart/form-data")
	public String edithdn(FormDataHDN hdn) {
		return hoaDonNhapDao.edithdn(hdn);
	}

	@GetMapping("/datatable-cthdn")
	public Datatables getDataCTHDN(@RequestParam("sopn") String sopn) {
		System.out.println(sopn);
		return hoaDonNhapDao.getCTHDN(sopn);
	}

	@PostMapping(path = "/create-cthdn", consumes = "multipart/form-data")
	public String create_cthdn(FormDataCTHDN formcthdn) {
		return hoaDonNhapDao.createcthdn(formcthdn);
	}

	@PostMapping(path = "/edit-cthdn", consumes = "multipart/form-data")
	public String edit_cthdn(FormDataCTHDN formcthdn) {
		return hoaDonNhapDao.editcthdn(formcthdn);
	}

	@PostMapping(path = "/delete-cthdn", consumes = "multipart/form-data")
	public String delete_cthdn(FormDataCTHDN formcthdn) {
		return hoaDonNhapDao.deletecthdn(formcthdn);
	}
}
