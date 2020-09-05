package com.devmaster.model;

import java.util.List;

import lombok.Data;

@Data
public class ModelProduct {
	private String maLoai;
	private String tenLoai;
	private List<Top4Product> top4;
}
