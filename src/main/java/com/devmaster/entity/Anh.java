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
@Table(name = "Anh")
public class Anh {
	
	@Id
	@GeneratedValue(generator = "MaAnh",strategy = GenerationType.IDENTITY)
	@Column(name = "MaAnh", nullable = false)
	private String maAnh;
	
	@Column(name = "MaSanPham", nullable = false)
	private long maSanPham;
	
	@Column(name = "TenAnh", nullable = true)
	private String tenAnh;
}
