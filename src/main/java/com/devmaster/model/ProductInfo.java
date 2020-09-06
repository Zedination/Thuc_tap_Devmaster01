package com.devmaster.model;

import java.util.List;

import com.devmaster.entity.Anh;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfo {
	private long maSanPham;
	private String tenSanPham;
	private String maLoai;
	private String tenLoai;
	private String price;
	private String newPrice;
	private String dacTrung;
	private String thongSo;
	private List<String> anhs;
}
