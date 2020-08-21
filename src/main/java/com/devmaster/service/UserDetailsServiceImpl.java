package com.devmaster.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import com.devmaster.dao.AppUserDAO;
import com.devmaster.entity.AppUser;
import com.devmaster.dao.AppRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private AppUserDAO appUserDAO;
 
    @Autowired
    private AppRoleDAO appRoleDAO;
 
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);
 
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        System.out.println("Found User: " + appUser);
 
        // [ROLE_USER, ROLE_ADMIN,..]
       List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
 
        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);
 
        return userDetails;
    }
//	@Override
//	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
//		List<String> roleNames = Arrays.asList("ROLE_USER","ROLE_ADMIN");
//		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
//      if (roleNames != null) {
//          for (String role : roleNames) {
//              // ROLE_USER, ROLE_ADMIN,..
//              GrantedAuthority authority = new SimpleGrantedAuthority(role);
//              grantList.add(authority);
//          }
//      }
//      UserDetails userDetails = (UserDetails) new User("dbadmin1", //
//            "slhfilashdflashdasd", grantList);
//
//      return userDetails;
//	}
 
}
