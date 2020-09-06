package com.devmaster.model;

import java.util.List;

import lombok.Data;

@Data
public class Top4Product {
	private long id;
	private long productType;
	private String pathImage;
	private String name;
	private List<Integer> rate;
	private String description;
	private String price;
	private String formattedPrice;
}
