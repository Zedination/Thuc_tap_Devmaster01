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

import com.devmaster.entity.CTHoaDonNhap;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.HoaDonNhap;
import com.devmaster.entity.SanPham;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.formdata.FormDataCTHDN;
import com.devmaster.formdata.FormDataHDN;

@Repository
@Transactional
public class HoaDonNhapDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<HoaDonNhap> getHDN(){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select e from " + HoaDonNhap.class.getName() + " e ";
 
        Query<HoaDonNhap> query = session.createQuery(sql, HoaDonNhap.class);
        return query.getResultList();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createhdn(FormDataHDN hdn) {
		Session session = this.sessionFactory.getCurrentSession();
		HoaDonNhap hoaDonNhap=new HoaDonNhap();
		hoaDonNhap.setSoPn(hdn.getSoPn());
		hoaDonNhap.setNgayNhap(hdn.getNgayNhap());
		session.save(hoaDonNhap);
		return "success";
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deletehdn(FormDataHDN hdn) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql="Delete from CTHoaDonNhap where soPn=:soPn ";	
		Query query = session.createQuery(sql);
		query.setParameter("soPn", hdn.getSoPn());
//		int rowCount = query.executeUpdate();
//		if (rowCount == 0) {
//		System.out.println("No data found to delete");
//		} else {
//		System.out.println("Your record is deleted");
//		}
        
		String sql2="SELECT e from " + CTHoaDonNhap.class.getName() + " e where soPn=:spnct ";
		Query query1 = session.createQuery(sql2);
		query1.setParameter("spnct", hdn.getSoPn());
		List<CTHoaDonNhap> list = query1.getResultList();
		for (CTHoaDonNhap a : list) {
			SanPham sanPham=session.find(SanPham.class, a.getSanPham().getMaSanPham());
	        long sl;
	        sl=sanPham.getSoLuong()-a.getSoLuongNhap();
	        session.evict(sanPham);
	        sanPham.setSoLuong(sl);
			session.update(sanPham);     
		}
		int rowCount = query.executeUpdate();
		HoaDonNhap hoadonnhap = session.find(HoaDonNhap.class, hdn.getSoPn());
		session.remove(hoadonnhap);
		return "success";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String edithdn(FormDataHDN hdn) {
		Session session = this.sessionFactory.getCurrentSession();
		HoaDonNhap hoaDonNhap=session.get(HoaDonNhap.class, hdn.getSoPn());
		session.evict(hoaDonNhap);
		hoaDonNhap.setNgayNhap(hdn.getNgayNhap());
		session.update(hoaDonNhap);
		return "success";
	}
	public List<SanPham> getcbSanPham(){
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Select e from " + SanPham.class.getName() + " e ";
 
        Query<SanPham> query = session.createQuery(sql, SanPham.class);
        return query.getResultList();
	}
	
	public Datatables getCTHDN(String a){
		Session session = this.sessionFactory.getCurrentSession();
		String sql;
		Query<CTHoaDonNhap> query;
		if(!a.equals("")) {
			sql = "Select e from " + CTHoaDonNhap.class.getName() + " e where soPn=:a";
			query = session.createQuery(sql, CTHoaDonNhap.class);
	        query.setParameter("a", Long.valueOf(a));
		}else {
			sql = "Select e from " + CTHoaDonNhap.class.getName() + " e ";
			query = session.createQuery(sql, CTHoaDonNhap.class);
		}
        List<CTHoaDonNhap> temp = query.getResultList(); 
        List<List<String>> datatables = new ArrayList<List<String>>();
        Datatables data = new Datatables();
        temp.forEach(e->{
			List<String> arr = Arrays.asList(String.valueOf(e.getId()),String.valueOf(e.getHoaDonNhap().getSoPn()),String.valueOf(e.getSanPham().getMaSanPham()),String.valueOf(e.getSoLuongNhap()),String.valueOf(e.getDonGiaNhap()));
			datatables.add(arr);
		});
		data.setData(datatables);
		return data;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createcthdn(FormDataCTHDN cthdn){
		Session session = this.sessionFactory.getCurrentSession();
		SanPham sanPham = session.get(SanPham.class, cthdn.getMaSanPham());
		long a;
		a=cthdn.getSoLuongNhap()+sanPham.getSoLuong();
		session.evict(sanPham);
		sanPham.setSoLuong(a);
		session.update(sanPham);
		
		CTHoaDonNhap ctHoaDonNhap=new CTHoaDonNhap();
		HoaDonNhap hoaDonNhap = session.get(HoaDonNhap.class, cthdn.getSoPn());
		ctHoaDonNhap.setHoaDonNhap(hoaDonNhap);		
		ctHoaDonNhap.setSanPham(sanPham);
		ctHoaDonNhap.setSoLuongNhap(cthdn.getSoLuongNhap());
		ctHoaDonNhap.setDonGiaNhap(cthdn.getDonGiaNhap());	
		session.save(ctHoaDonNhap);
		return "success";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String editcthdn(FormDataCTHDN cthdn){
		Session session = this.sessionFactory.getCurrentSession();
		SanPham sanPham = session.get(SanPham.class, cthdn.getMaSanPham());
		CTHoaDonNhap ctHDN = session.get(CTHoaDonNhap.class, cthdn.getID());
		long a;
		a=sanPham.getSoLuong()+cthdn.getSoLuongNhap()-ctHDN.getSoLuongNhap();
		session.evict(sanPham);
		sanPham.setSoLuong(a);
		session.update(sanPham);
		
		
		HoaDonNhap hdn=session.get(HoaDonNhap.class, cthdn.getSoPn());
		session.evict(hdn);
//		ctHDN.setId(cthdn.getID());
		ctHDN.setHoaDonNhap(hdn);
		ctHDN.setSanPham(sanPham);
		ctHDN.setSoLuongNhap(cthdn.getSoLuongNhap());
		ctHDN.setDonGiaNhap(cthdn.getDonGiaNhap());
		
		session.update(ctHDN);
		return "success";
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deletecthdn(FormDataCTHDN cthdn){
		Session session = this.sessionFactory.getCurrentSession();
		SanPham sanPham = session.get(SanPham.class, cthdn.getMaSanPham());
		CTHoaDonNhap ctHDN = session.get(CTHoaDonNhap.class, cthdn.getID());
		long a;
		a=sanPham.getSoLuong()-cthdn.getSoLuongNhap();
		session.evict(sanPham);
		sanPham.setSoLuong(a);
		session.update(sanPham);
        
		session.remove(ctHDN);
		return "success";
	}
	
	
}
