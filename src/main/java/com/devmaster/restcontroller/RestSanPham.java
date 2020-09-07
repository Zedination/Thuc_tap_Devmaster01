package com.devmaster.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.devmaster.dao.SanPhamDao;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.Token;
import com.devmaster.formdata.FormDataProduct;
import com.devmaster.model.Top4Product;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;

@Transactional
@RestController
public class RestSanPham {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SanPhamDao sanPhamDao;
	
	@GetMapping("/datatable-product")
	public Datatables getDataProduct() {
		return sanPhamDao.getDataProduct();
	}
	@PostMapping(path = "/create-product",consumes = "multipart/form-data")
	public String createPro(FormDataProduct product) throws IOException, FirebaseMessagingException {
		Session session = this.sessionFactory.getCurrentSession();
		String result = sanPhamDao.createProduct(product);
		String sql = "Select tk from Token tk";
		Query<Token> query = session.createQuery(sql, Token.class);
		List<String> listToken = new ArrayList<String>();
		query.getResultList().forEach(e->{
			listToken.add(e.getToken());
		});
		MulticastMessage message = MulticastMessage.builder().putData("title", "Hoàng Hoan")
				.putData("content", "Có sản phẩm mới, hãy xem ngay!")
				.putData("link", "http://localhost:8080/chi-tiet-san-pham?maSanPham="+result)
				.addAllTokens(listToken)
				.build();
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		return "Thêm sản phẩm thành công!";
	}
	@PostMapping(path = "/update-product",consumes = "multipart/form-data")
	public String updatePro(FormDataProduct product) throws IOException {
		return sanPhamDao.updateProduct(product);
	}
	@DeleteMapping("/delete-product")
	public String deletePro(@RequestParam("id") long id) {
		return sanPhamDao.deleteProduct(id);
	}
	@GetMapping("/test-front")
	public List<Top4Product> test(@RequestParam("id") long id) {
		return sanPhamDao.lstTop4Product(id);
	}
}
