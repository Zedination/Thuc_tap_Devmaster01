package com.devmaster.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "tblPhieuNhap")
public class HoaDonNhap {
    @Id
    @Column(name = "SoPN", nullable = false)
 	private long soPn;
    
    @Column(name = "NgayNhap")
    private String ngayNhap;

}
