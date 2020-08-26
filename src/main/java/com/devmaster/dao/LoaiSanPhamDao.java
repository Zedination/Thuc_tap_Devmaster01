package com.devmaster.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devmaster.entity.LoaiSanPham;

@Repository
@Transactional
public class LoaiSanPhamDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public List<LoaiSanPham> getLoaiSP(){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select e from " + LoaiSanPham.class.getName() + " e ";
 
        Query<LoaiSanPham> query = session.createQuery(sql, LoaiSanPham.class);
        return query.getResultList();
	}
}
