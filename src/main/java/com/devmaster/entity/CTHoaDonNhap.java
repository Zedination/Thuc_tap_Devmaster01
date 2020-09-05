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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tblCTPhieuNhap")
public class CTHoaDonNhap {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;
	
	@ManyToOne
	@JoinColumn(name = "SoPn", nullable = false)
	private HoaDonNhap hoaDonNhap;
	
	@ManyToOne
    @JoinColumn(name = "MaSanPham", nullable = false)
	private SanPham sanPham;
	
	@Column(name = "SoLuongNhap")
	private long soLuongNhap;
	
	@Column(name = "DonGiaNhap")
	private double donGiaNhap;
}
