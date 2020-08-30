package com.devmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tblCTPhieuXuat")
public class CTHoaDonXuat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private long id;
	
	@ManyToOne
	@JoinColumn(name = "SoPX", nullable = false)
	private HoaDonXuat hoaDonXuat;
	
	@ManyToOne
	@JoinColumn(name = "MaSanPham", nullable = false)
	private SanPham sanPham;
	
	@Column(name = "SoLuongXuat")
	private long soLuongXuat;
	
	@Column(name = "DonGiaXuat")
	private double donGiaXuat;
}
