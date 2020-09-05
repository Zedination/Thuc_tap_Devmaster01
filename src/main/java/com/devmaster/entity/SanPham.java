package com.devmaster.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tblSanPham")
public class SanPham {
	@Id
	@GeneratedValue(generator = "MaSanPham", strategy = GenerationType.IDENTITY)
	@Column(name = "MaSanPham", nullable = false)
	private long maSanPham;
	
	@Column(name = "TenSanPham", nullable = false)
	private String tenSanPham;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaLoai", nullable = false)
	private LoaiSanPham loaiSanPham;
	
	@Column(name = "DanhGia")
	private long danhGia;
	
	@Column(name = "PhanTram")
	private double phanTram;
	
	@Column(name = "MoTa")
	private String moTa;
	
//	@OneToMany
//	private List<Anh> anh;
	
	@Column(name = "SoLuong")
	private long soLuong;
	
	@Column(name = "DacTrung")
	private String dacTrung;
	
	@Column(name = "ThongSo")
	private String thongSo;
}
