package com.devmaster.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devmaster.entity.Datatables;
import com.devmaster.entity.TinTuc;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.model.NewsInfo;

@Repository
@Transactional
public class TinTucDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Datatables getNews() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = " Select tt from " + TinTuc.class.getName() + " tt ";
		Query<TinTuc> query = session.createQuery(sql, TinTuc.class);
		List<TinTuc> temp = query.getResultList();
		List<List<String>> datatables = new ArrayList<List<String>>();
		Datatables data = new Datatables();
		temp.forEach(e -> {
			List<String> a = Arrays.asList(String.valueOf(e.getMaTin()), e.getTieuDe(), e.getNoiDung());
			datatables.add(a);
		});
		data.setData(datatables);
		return data;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createNews(String title, String content) {
		Session session = this.sessionFactory.getCurrentSession();
		TinTuc newNews = new TinTuc();
		newNews.setTieuDe(title);
		newNews.setNoiDung(content);
		session.save(newNews);
		return "Lưu tin thành công!";
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String updateNews(long id, String title, String content) {
		Session session = this.sessionFactory.getCurrentSession();
		TinTuc news = session.get(TinTuc.class, id);
		session.evict(news);
		news.setTieuDe(title);
		news.setNoiDung(content);
		session.update(news);
		return "Cập nhật tin tức thành công!";
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deleteNews(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		TinTuc deletedNews = session.load(TinTuc.class, id);
		session.delete(deletedNews);
		return "Xóa tin thành công!";
	}

	public List<NewsInfo> getListNews() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select sp from TinTuc sp";
		Query<TinTuc> query = session.createQuery(sql, TinTuc.class);
		List<TinTuc> temp = new ArrayList<TinTuc>();
		List<NewsInfo> data = new ArrayList<NewsInfo>();
		temp = query.getResultList();
		temp.forEach(e -> {
			NewsInfo info = new NewsInfo();
			info.setId(e.getMaTin());
			info.setTitle(e.getTieuDe());
			Document doc = Jsoup.parse(e.getNoiDung());
			try {
				String pathImage = doc.select("img").get(0).attr("src");
				info.setPathImage(pathImage);
			} catch (Exception e2) {
				info.setPathImage(
						"https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png");
			}
			try {
				System.out.println(doc.text());
				String[] contents = doc.text().split("[.]+");
				for (String s : contents) {
					if(!s.isEmpty()&&s.length()>5) {
						info.setContent(s);
						break;
					}
				}
				if(info.getContent()==null||"".equals(info.getContent())) {
					info.setContent("Click để xem chi tiết!");
				}
			} catch (Exception e2) {
				info.setContent("Click để xem chi tiết!");
			}
			data.add(info);
		});
		return data;
	}
	public NewsInfo getNewsById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select sp from TinTuc sp where sp.maTin = "+id;
		Query<TinTuc> query = session.createQuery(sql, TinTuc.class);
		TinTuc data = query.getSingleResult();
		NewsInfo info = new NewsInfo();
		info.setId(data.getMaTin());
		info.setTitle(data.getTieuDe());
		info.setContent(data.getNoiDung());
		return info;
	}
}
