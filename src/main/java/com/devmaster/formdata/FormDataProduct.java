package com.devmaster.formdata;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FormDataProduct {
	private String maSanPham;
	private String tenSanPham;
	private long maLoai;
	private long danhGia;
	private double phanTram;
	private String moTa;
	private long soLuong;
	private String dacTrung;
	private String thongSo;
	private String listImageForUpdate;
	private MultipartFile[] files;
}
