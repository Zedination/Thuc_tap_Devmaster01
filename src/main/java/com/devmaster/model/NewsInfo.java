package com.devmaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsInfo {
	private long id;
	private String title;
	private String content;
	private String pathImage;
}
