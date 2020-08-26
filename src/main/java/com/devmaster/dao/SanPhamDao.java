package com.devmaster.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.devmaster.entity.Anh;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.entity.SanPham;
import com.devmaster.entity.UserRole;
import com.devmaster.exception.BankTransactionException;
import com.devmaster.formdata.FormDataProduct;

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
			for (Anh a : x.getAnh()) {
				if(i==x.getAnh().size()) {
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
	public String createProduct(FormDataProduct product) throws IOException {
		Session session = this.sessionFactory.getCurrentSession();
		SanPham newProduct = new SanPham();
		newProduct.setMaSanPham(product.getMaSanPham());
		newProduct.setTenSanPham(product.getTenSanPham());
		LoaiSanPham loaiSanPham = session.load(LoaiSanPham.class, product.getMaLoai());
		newProduct.setLoaiSanPham(loaiSanPham);
		newProduct.setDanhGia(product.getDanhGia());
		newProduct.setPhanTram(product.getPhanTram());
		newProduct.setMoTa(product.getMoTa());
		newProduct.setSoLuong(product.getSoLuong());
		newProduct.setDacTrung(product.getDacTrung());
		newProduct.setThongSo(product.getThongSo());
		session.save(newProduct);
		for(int i = 0; i< product.getFiles().length;i++) {
			saveFile(product.getFiles()[i]);
			Anh newAnh = new Anh();
			newAnh.setMaSanPham(product.getMaSanPham());
			newAnh.setTenAnh("src/main/resources/static/files/sanpham/" + product.getFiles()[i].getOriginalFilename());
			session.save(newAnh);
		}
		return "success";
	}
	public void saveFile(MultipartFile file) throws IOException {
		String uploadFilePath =   "src/main/resources/static/files/sanpham/" + file.getOriginalFilename();
		Path path = Paths.get(uploadFilePath);
        Files.write(path, file.getBytes());
	}
}
