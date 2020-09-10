package com.devmaster.dao;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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
import org.springframework.web.multipart.MultipartFile;

import com.devmaster.entity.Anh;
import com.devmaster.entity.CTHoaDonNhap;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.entity.SanPham;
import com.devmaster.entity.Token;
import com.devmaster.entity.UserRole;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.formdata.FormDataProduct;
import com.devmaster.model.PhanTrangObj;
import com.devmaster.model.ProductInfo;
import com.devmaster.model.Top4Product;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;

@Repository
@Transactional
public class SanPhamDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Datatables getDataProduct() {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = " Select sp from " + SanPham.class.getName() + " sp ";
		Query<SanPham> query = session.createQuery(sql, SanPham.class);
		List<List<String>> datatables = new ArrayList<List<String>>();
		List<SanPham> temp = query.getResultList();
		Datatables data = new Datatables();
		temp.forEach(x -> {
			String tempData1 = "";
			String tempData2 = "";
			int i = 1;
			String sql2 = " Select a from " + Anh.class.getName() + " a where maSanPham ="+x.getMaSanPham();
			Query<Anh> query2 = session.createQuery(sql2, Anh.class);
			List<Anh> as = query2.getResultList();
			System.out.println(as);
			for (Anh a : as) {
				if(i==as.size()) {
					tempData1+= a.getMaAnh();
					tempData2+= a.getTenAnh();
				}else {
					tempData1+= a.getMaAnh()+"\n";
					tempData2+= a.getTenAnh()+"\n";
				}
				i++;
			}
			List<String> arr = Arrays.asList(String.valueOf(x.getMaSanPham()),
					x.getTenSanPham(),
					String.valueOf(x.getLoaiSanPham().getMaLoai()),
					x.getLoaiSanPham().getTenLoai(), 
					String.valueOf(x.getDanhGia()), 
					String.valueOf(x.getPhanTram()),
					x.getMoTa(), 
					tempData1,
					tempData2,
					String.valueOf(x.getSoLuong()),
					x.getDacTrung(),
					x.getThongSo());
			datatables.add(arr);
		});
		data.setData(datatables);
		return data;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String createProduct(FormDataProduct product) throws IOException, FirebaseMessagingException {
		Session session = this.sessionFactory.getCurrentSession();
		SanPham newProduct = new SanPham();
		newProduct.setTenSanPham(product.getTenSanPham());
		LoaiSanPham loaiSanPham = session.load(LoaiSanPham.class, product.getMaLoai());
		newProduct.setLoaiSanPham(loaiSanPham);
		newProduct.setDanhGia(product.getDanhGia());
		newProduct.setPhanTram(product.getPhanTram());
		newProduct.setMoTa(product.getMoTa());
		newProduct.setSoLuong(product.getSoLuong());
		newProduct.setDacTrung(product.getDacTrung());
		newProduct.setThongSo(product.getThongSo());
		Serializable id = session.save(newProduct);
		try {
			for(int i = 0; i< product.getFiles().length;i++) {
				saveFile(product.getFiles()[i]);
				Anh newAnh = new Anh();
				newAnh.setMaSanPham(Long.valueOf(id.toString()));
				newAnh.setTenAnh("src/main/resources/static/files/sanpham/" + product.getFiles()[i].getOriginalFilename());
				session.save(newAnh);
			}
		} catch (Exception e) {
			
		}
		return id.toString();
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String updateProduct(FormDataProduct updateForProduct) {
		Session session = this.sessionFactory.getCurrentSession();
		if(updateForProduct.getListImageForUpdate().split(",").length>0) {
			for(int i =0;i<updateForProduct.getListImageForUpdate().split(",").length;i++) {
				String sql = " Delete from Anh where maAnh=:maAnh";
				Query query = session.createQuery(sql);
				query.setParameter("maAnh", updateForProduct.getListImageForUpdate().split(",")[i]);
				query.executeUpdate();
			}
		}
		System.out.println(updateForProduct.getMaSanPham());
		
		SanPham sp = session.get(SanPham.class,Long.valueOf(updateForProduct.getMaSanPham()));
		session.evict(sp);
		sp.setTenSanPham(updateForProduct.getTenSanPham());
		LoaiSanPham loaiSanPham = session.load(LoaiSanPham.class, updateForProduct.getMaLoai());
		sp.setLoaiSanPham(loaiSanPham);
		sp.setDanhGia(updateForProduct.getDanhGia());
		sp.setPhanTram(updateForProduct.getPhanTram());
		sp.setMoTa(updateForProduct.getMoTa());
		sp.setSoLuong(updateForProduct.getSoLuong());
		sp.setDacTrung(updateForProduct.getDacTrung());
		sp.setThongSo(updateForProduct.getThongSo());
//		String temp[] = updateForProduct.getListImageForUpdate().split(",");
//		List<Anh> a = new ArrayList<Anh>();
//		if(temp.length>0) {
//			for(int i = 0; i<temp.length;i++) {
//				Anh anh = session.get(Anh.class, temp[i]);
//				session.evict(anh);
//				a.add(anh);
//			}
//		}
//		System.out.println(a.toString());
//		sp.setAnh(a);
		try {
			for(int i = 0; i< updateForProduct.getFiles().length;i++) {
				saveFile(updateForProduct.getFiles()[i]);
				Anh newAnh = new Anh();
				newAnh.setMaSanPham(Long.valueOf(updateForProduct.getMaSanPham()));
				newAnh.setTenAnh("src/main/resources/static/files/sanpham/" + updateForProduct.getFiles()[i].getOriginalFilename());
				session.save(newAnh);
			}
		} catch (Exception e) {
			
		}
		session.update(sp);
		return "Sửa sản phẩm thành công!";
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public String deleteProduct(long maSanPham) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = "Delete from CTHoaDonNhap where sanPham.maSanPham =:maSanPham";
		Query query = session.createQuery(sql);
		query.setParameter("maSanPham",maSanPham);
		query.executeUpdate();
		
		String sql2 = "Delete from CTHoaDonXuat where sanPham.maSanPham =:maSanPham";
		Query query2 = session.createQuery(sql2);
		query2.setParameter("maSanPham",maSanPham);
		query2.executeUpdate();
		
		String sql3 = "Delete from Anh where maSanPham =:maSanPham";
		Query query3 = session.createQuery(sql3);
		query3.setParameter("maSanPham", maSanPham);
		query3.executeUpdate();
		
		String sql4 = "Delete from SanPham where maSanPham =:maSanPham";
		Query query4 = session.createQuery(sql4);
		query4.setParameter("maSanPham", maSanPham);
		query4.executeUpdate();
		return "Xóa sản phẩm thành công!";
	}
	public List<Top4Product> lstTop4Product(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		List<Top4Product> data = new ArrayList<Top4Product>();
		String sql = " Select sp from SanPham sp join sp.loaiSanPham where sp.loaiSanPham.maLoai = "+id;
		Query<SanPham> query = session.createQuery(sql, SanPham.class).setMaxResults(4);
		query.getResultList().forEach(e->{
			Top4Product product = new Top4Product();
			product.setId(e.getMaSanPham());
			product.setName(e.getTenSanPham());
			product.setProductType(id);
			switch ((int)e.getDanhGia()) {
			case 1:
				product.setRate(Arrays.asList(1));
				break;
			case 2:
				product.setRate(Arrays.asList(1,2));
				break;
			case 3:
				product.setRate(Arrays.asList(1,2,3));
				break;
			case 4:
				product.setRate(Arrays.asList(1,2,3,4));
				break;
			default:
				product.setRate(Arrays.asList(1,2,3,4,5));
				break;
			}
			String sql2 = "Select a from Anh a where a.maSanPham = "+e.getMaSanPham();
			Query<Anh> query2 = session.createQuery(sql2, Anh.class).setMaxResults(1);
			try {
				String pathImage = query2.getSingleResult().getTenAnh().split("/")[6];
				product.setPathImage(pathImage);
			} catch (Exception e2) {
				product.setPathImage("pb-2.jpg");
			}
			product.setDescription(e.getMoTa());
			String sql4 = "Select ctn from CTHoaDonNhap ctn where ctn.sanPham.maSanPham ="+e.getMaSanPham()+" order by id desc";
			Query<CTHoaDonNhap> query3 = session.createQuery(sql4, CTHoaDonNhap.class);
			try {
				double price = query3.getResultList().get(0).getDonGiaNhap() + (query3.getResultList().get(0).getDonGiaNhap()*e.getPhanTram())/100;
				DecimalFormat formatter = new DecimalFormat("###,###,###.##");
				product.setPrice(String.valueOf(price));
				product.setFormattedPrice(formatter.format(price).toString());
			} catch (Exception e2) {
				product.setPrice("0");
				product.setFormattedPrice("0");
			}
			data.add(product);
		});
		return data;
	}
	public List<Top4Product> lstTop8Product() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Top4Product> data = new ArrayList<Top4Product>();
		String sql = " Select sp from SanPham sp order by danhGia desc";
		Query<SanPham> query = session.createQuery(sql, SanPham.class).setMaxResults(8);
		query.getResultList().forEach(e->{
			Top4Product product = new Top4Product();
			product.setId(e.getMaSanPham());
			product.setName(e.getTenSanPham());
			switch ((int)e.getDanhGia()) {
			case 1:
				product.setRate(Arrays.asList(1));
				break;
			case 2:
				product.setRate(Arrays.asList(1,2));
				break;
			case 3:
				product.setRate(Arrays.asList(1,2,3));
				break;
			case 4:
				product.setRate(Arrays.asList(1,2,3,4));
				break;
			default:
				product.setRate(Arrays.asList(1,2,3,4,5));
				break;
			}
			String sql2 = "Select a from Anh a where a.maSanPham = "+e.getMaSanPham();
			Query<Anh> query2 = session.createQuery(sql2, Anh.class).setMaxResults(1);
			try {
				String pathImage = query2.getSingleResult().getTenAnh().split("/")[6];
				product.setPathImage(pathImage);
			} catch (Exception e2) {
				product.setPathImage("pb-2.jpg");
			}
			product.setDescription(e.getMoTa());
			String sql4 = "Select ctn from CTHoaDonNhap ctn where ctn.sanPham.maSanPham ="+e.getMaSanPham()+" order by id desc";
			Query<CTHoaDonNhap> query3 = session.createQuery(sql4, CTHoaDonNhap.class);
			try {
				double price = query3.getResultList().get(0).getDonGiaNhap() + (query3.getResultList().get(0).getDonGiaNhap()*e.getPhanTram())/100;
				DecimalFormat formatter = new DecimalFormat("###,###,###.##");
				product.setPrice(String.valueOf(price));
				product.setFormattedPrice(formatter.format(price).toString());
			} catch (Exception e2) {
				product.setPrice("0");
				product.setFormattedPrice("0");
			}
			data.add(product);
		});
		return data;
	}
	public List<Top4Product> listAllProductByType(long id){
		Session session = this.sessionFactory.getCurrentSession();
		List<Top4Product> data = new ArrayList<Top4Product>();
		String sql = " Select sp from SanPham sp join sp.loaiSanPham where sp.loaiSanPham.maLoai = "+id;
		Query<SanPham> query = session.createQuery(sql, SanPham.class);
		query.getResultList().forEach(e->{
			Top4Product product = new Top4Product();
			product.setId(e.getMaSanPham());
			product.setName(e.getTenSanPham());
			product.setProductType(id);
			switch ((int)e.getDanhGia()) {
			case 1:
				product.setRate(Arrays.asList(1));
				break;
			case 2:
				product.setRate(Arrays.asList(1,2));
				break;
			case 3:
				product.setRate(Arrays.asList(1,2,3));
				break;
			case 4:
				product.setRate(Arrays.asList(1,2,3,4));
				break;
			default:
				product.setRate(Arrays.asList(1,2,3,4,5));
				break;
			}
			String sql2 = "Select a from Anh a where a.maSanPham = "+e.getMaSanPham();
			Query<Anh> query2 = session.createQuery(sql2, Anh.class).setMaxResults(1);
			try {
				String pathImage = query2.getSingleResult().getTenAnh().split("/")[6];
				product.setPathImage(pathImage);
			} catch (Exception e2) {
				product.setPathImage("pb-2.jpg");
			}
			product.setDescription(e.getMoTa());
			String sql4 = "Select ctn from CTHoaDonNhap ctn where ctn.sanPham.maSanPham ="+e.getMaSanPham()+" order by id desc";
			Query<CTHoaDonNhap> query3 = session.createQuery(sql4, CTHoaDonNhap.class);
			try {
				double price = query3.getResultList().get(0).getDonGiaNhap() + (query3.getResultList().get(0).getDonGiaNhap()*e.getPhanTram())/100;
				DecimalFormat formatter = new DecimalFormat("###,###,###.##");
				product.setPrice(String.valueOf(price));
				product.setFormattedPrice(formatter.format(price).toString());
			} catch (Exception e2) {
				product.setPrice("0");
				product.setFormattedPrice("0");
			}
			data.add(product);
		});
		return data;
	}
	public ProductInfo getProductInfo(long maSanPham) {
		Session session = this.sessionFactory.getCurrentSession();
		String sql = " Select sp from SanPham sp where sp.maSanPham = "+maSanPham;
		Query<SanPham> query = session.createQuery(sql, SanPham.class);
		SanPham product = query.getSingleResult();
		ProductInfo info = new ProductInfo();
		info.setMaSanPham(product.getMaSanPham());
		info.setTenSanPham(product.getTenSanPham());
		info.setDacTrung(product.getDacTrung());
		info.setThongSo(product.getThongSo());
		String sql2 = "Select ctn from CTHoaDonNhap ctn where ctn.sanPham.maSanPham ="+product.getMaSanPham()+" order by id desc";
		Query<CTHoaDonNhap> query2 = session.createQuery(sql2, CTHoaDonNhap.class);
		try {
			double price = query2.getResultList().get(0).getDonGiaNhap() + (query2.getResultList().get(0).getDonGiaNhap()*product.getPhanTram())/100;
			DecimalFormat formatter = new DecimalFormat("###,###,###.##");
			info.setPrice(formatter.format(price).toString());
			info.setNewPrice(String.valueOf(price*0.9));
		} catch (Exception e2) {
			info.setPrice(String.valueOf(0));
			info.setNewPrice("0");
		}
		
		List<Anh> anhs = new ArrayList<Anh>();
		String sql3 = "Select a from Anh a where a.maSanPham = "+maSanPham;
		Query<Anh> query3 = session.createQuery(sql3, Anh.class).setMaxResults(4);
		anhs = query3.getResultList();
		List<String> anhString = new ArrayList<String>();
		anhs.forEach(e->{
			try {
				String pathImage = e.getTenAnh().split("/")[6];
				anhString.add(pathImage);
			} catch (Exception e2) {
				anhString.add("pb-2.jpg");
			}
		});
		info.setAnhs(anhString);
		info.setMaLoai(String.valueOf(product.getLoaiSanPham().getMaLoai()));
		info.setTenLoai(product.getLoaiSanPham().getTenLoai());
		return info;
	}
	public void saveFile(MultipartFile file) throws IOException {
		String uploadFilePath =   "src/main/resources/static/files/sanpham/" + file.getOriginalFilename();
		Path path = Paths.get(uploadFilePath);
        Files.write(path, file.getBytes());
	}
}
