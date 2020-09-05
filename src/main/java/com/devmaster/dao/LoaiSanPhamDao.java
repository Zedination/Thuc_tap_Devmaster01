package com.devmaster.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devmaster.entity.Anh;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.entity.SanPham;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.formdata.FormDataCTHDN;
import com.devmaster.formdata.FormDataHDN;
import com.devmaster.formdata.FormDataProduct;
import com.devmaster.formdata.FormDataType;

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
	
	public LoaiSanPham getLoaiSPByID(long maLoai) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select e from " + LoaiSanPham.class.getName() + " e where maLoai = "+maLoai;
 
        Query<LoaiSanPham> query = session.createQuery(sql, LoaiSanPham.class);
        return query.getResultList().get(0);
	}
	
//	public List<String> getListTenLoai(){
//		Session session = this.sessionFactory.getCurrentSession();
//		String sql = "Select e.tenLoai from " + LoaiSanPham.class.getName() + " e ";
//		Query<String> query = session.createQuery(sql, String.class);
//		return query.getResultList();
//	}
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createType(FormDataType type) throws IOException {
		Session session = this.sessionFactory.getCurrentSession();
		LoaiSanPham newType = new LoaiSanPham();
		newType.setTenLoai(type.getTenLoai());
		session.save(newType);
		return "Lưu loại sản phẩm thành công!";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String edittype(FormDataType type) throws IOException{
		Session session = this.sessionFactory.getCurrentSession();
		LoaiSanPham loaisp=session.get(LoaiSanPham.class, Long.valueOf(type.getMaLoai()));
		session.evict(loaisp);
		loaisp.setTenLoai(type.getTenLoai());;
		session.update(loaisp);
		return "Sửa thành công!";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deletetype(FormDataType type) throws IOException{
		Session session = this.sessionFactory.getCurrentSession();
		String sqlbase = " Select a from " + SanPham.class.getName() + " a where maLoai ="+Long.valueOf(type.getMaLoai());
		Query<SanPham> querybase = session.createQuery(sqlbase, SanPham.class);
		List<SanPham> as = querybase.getResultList();
		for (SanPham sp : as) {
			String sql = "Delete from CTHoaDonNhap where sanPham.maSanPham =:maSanPham";
			Query query = session.createQuery(sql);
			query.setParameter("maSanPham",sp.getMaSanPham());
			query.executeUpdate();
			
			String sql2 = "Delete from CTHoaDonXuat where sanPham.maSanPham =:maSanPham";
			Query query2 = session.createQuery(sql2);
			query2.setParameter("maSanPham",sp.getMaSanPham());
			query2.executeUpdate();
			
			String sql3 = "Delete from Anh where maSanPham =:maSanPham";
			Query query3 = session.createQuery(sql3);
			query3.setParameter("maSanPham", sp.getMaSanPham());
			query3.executeUpdate();
			
			String sql4 = "Delete from SanPham where maSanPham =:masanpham";
			Query query4 = session.createQuery(sql4);
			query4.setParameter("masanpham",sp.getMaSanPham());
			query4.executeUpdate();
		}
		
		LoaiSanPham loaisanpham = session.find(LoaiSanPham.class,Long.valueOf(type.getMaLoai()));
		session.remove(loaisanpham);
		return "success";
	}
	
	

}
