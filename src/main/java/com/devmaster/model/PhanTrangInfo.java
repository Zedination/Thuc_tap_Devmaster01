package com.devmaster.model;

import lombok.Data;

@Data
public class PhanTrangInfo {
	private long maLoai;
	private String tenLoai;
	private int position;
	private int totalRecords;
	private String mucGia;
	private String sortBy;
}
