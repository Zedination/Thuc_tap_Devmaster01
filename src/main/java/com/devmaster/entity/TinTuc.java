package com.devmaster.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tblTinTuc")
@Getter
@Setter
public class TinTuc {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name = "MaTin")
	private long maTin;
	
	@Column(name = "TieuDe")
	private String tieuDe;
	
	@Column(name = "NoiDung")
	private String noiDung;
}
