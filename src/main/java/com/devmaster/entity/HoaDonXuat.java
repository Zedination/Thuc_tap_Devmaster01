package com.devmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tblPhieuXuat")
public class HoaDonXuat {
	@Id
	@Column(name = "SoPX")
	private String soPX;
	
	@Column(name = "NgayXuat")
	private String ngayXuat;
	
	@Column(name = "TenKH")
	private String tenKH;
}
