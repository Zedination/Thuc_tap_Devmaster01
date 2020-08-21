package com.devmaster.dao;

import javax.persistence.NoResultException;
 
import com.devmaster.entity.AppUser;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository
@Transactional
public class AppUserDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public AppUser findUserAccount(String userName) {
    	Session session = this.sessionFactory.getCurrentSession();
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.userName = :userName ";
 
            Query<AppUser> query = session.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);
 
            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
 
}