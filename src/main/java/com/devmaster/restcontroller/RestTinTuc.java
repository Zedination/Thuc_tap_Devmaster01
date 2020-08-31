package com.devmaster.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.dao.TinTucDao;
import com.devmaster.entity.Datatables;

@RestController
public class RestTinTuc {
	
	@Autowired
	private TinTucDao tinTucDao;
	
	@GetMapping("/list-news")
	public Datatables listNews() {
		return tinTucDao.getNews();
	}
	@PostMapping("/create-news")
	public String createNews(@RequestParam("title") String title, @RequestParam("content") String content) {
		return tinTucDao.createNews(title, content);
	}
	@PostMapping("/update-news")
	public String updateNews(@RequestParam("id") long id, @RequestParam("title") String title,
			@RequestParam("content") String content) {
		return tinTucDao.updateNews(id, title, content);
	}
	@DeleteMapping("/delete-news")
	public String deleteNews(@RequestParam("id") long id) {
		return tinTucDao.deleteNews(id);
	}
}
