package com.devmaster.dao;

import java.util.List;

 
import com.devmaster.entity.UserRole;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
@Repository
@Transactional
public class AppRoleDAO {
 
    @Autowired
    private SessionFactory sessionFactory;
 
    public List<String> getRoleNames(Long userId) {
    	Session session = this.sessionFactory.getCurrentSession();
        String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " //
                + " where ur.appUser.userId = :userId ";
 
        Query<String> query = session.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
 
}
