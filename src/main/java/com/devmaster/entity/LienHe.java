package com.devmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tblLienHe")
public class LienHe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name = "HoTen")
    private String hoTen;
	
	@Column(name = "Email")
    private String email;
	
	@Column(name = "SoDienThoai")
    private String soDienThoai;
	
	@Column(name = "NoiDung")
    private String noiDung;
	
	@Column(name = "ThoiGian")
    private String thoiGian;
}
