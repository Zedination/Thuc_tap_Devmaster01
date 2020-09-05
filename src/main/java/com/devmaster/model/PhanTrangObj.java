package com.devmaster.model;

import java.util.List;

import lombok.Data;

@Data
public class PhanTrangObj {
	private PhanTrangInfo info;
	private List<Top4Product> top4Products;
}
