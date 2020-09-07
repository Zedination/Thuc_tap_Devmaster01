package com.devmaster.restcontroller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.entity.Token;
import com.devmaster.exception.BankTransactionException;

@RestController
@Transactional
public class RestToken {
	@Autowired
	private SessionFactory sessionFactory;

	@PostMapping("/save-token")
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BankTransactionException.class)
	public void insertToken(String token) {
		Session session = this.sessionFactory.getCurrentSession();
		Token table = new Token();
		table.setToken(token);
		String sql = "Select tk from Token tk where tk.token =:param";
		Query<Token> query = session.createQuery(sql, Token.class).setParameter("param", token);
		if(query.getResultList().size()==0) {
			session.save(table);
		}
	}
}
