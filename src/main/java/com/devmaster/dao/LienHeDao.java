package com.devmaster.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devmaster.entity.HoaDonNhap;
import com.devmaster.entity.LienHe;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.formdata.FormDataLienHe;

@Repository
@Transactional
public class LienHeDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<LienHe> getLienHe(){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select e from " + LienHe.class.getName() + " e ";
 
        Query<LienHe> query = session.createQuery(sql, LienHe.class);
        return query.getResultList();
	} 
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createLienHe(FormDataLienHe dataLienHe) {
		Session session = this.sessionFactory.getCurrentSession();
		LienHe newLienHe = new LienHe();
		newLienHe.setHoTen(dataLienHe.getHoTen());
		newLienHe.setEmail(dataLienHe.getEmail());
		newLienHe.setSoDienThoai(dataLienHe.getSoDienThoai());
		newLienHe.setNoiDung(dataLienHe.getNoiDung());
		newLienHe.setThoiGian(dataLienHe.getThoiGian());
		session.save(newLienHe);
		return "Lưu tin thành công!";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deleteLienHe(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		LienHe lienHe=session.get(LienHe.class, id);
		session.remove(lienHe);
		return "success";
	}
}
