package com.devmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "tblLoaiSanPham")

public class LoaiSanPham {
	@Id
	@GeneratedValue(generator = "MaLoai", strategy = GenerationType.IDENTITY)
	@Column(name = "MaLoai", nullable = false)
	private long maLoai;
	
	@Column(name = "TenLoai", nullable = false)
	private String tenLoai;
}
