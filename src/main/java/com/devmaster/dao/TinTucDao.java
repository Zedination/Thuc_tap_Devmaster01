package com.devmaster.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devmaster.entity.Datatables;
import com.devmaster.entity.TinTuc;
import com.devmaster.exception.BankTransactionException;

@Repository
@Transactional
public class TinTucDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Datatables getNews() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = " Select tt from " + TinTuc.class.getName() + " tt ";
		Query<TinTuc> query = session.createQuery(sql,TinTuc.class);
		List<TinTuc> temp = query.getResultList();
		List<List<String>> datatables = new ArrayList<List<String>>();
		Datatables data = new Datatables();
		temp.forEach(e->{
			List<String> a = Arrays.asList(String.valueOf(e.getMaTin()),e.getTieuDe(), e.getNoiDung());
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
	public String updateNews(long id, String title, String content){
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
}
