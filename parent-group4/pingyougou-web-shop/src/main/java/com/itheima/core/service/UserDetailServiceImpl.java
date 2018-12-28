package com.itheima.core.service;

import com.itheima.core.SellerService;
import com.itheima.core.pojo.seller.Seller;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailServiceImpl implements UserDetailsService {
    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerService.findOne(username);
        if (null != seller) {
            if ("1".equals(seller.getStatus())) {   // 只有通过审核的商家才会授权
//                Set<GrantedAuthority> grantedAuthoritySet = (Set<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
                Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();    // 为什么这里直接new 而不是通过获取session域中的对象
                grantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_SELLER"));
                return new User(username, seller.getPassword(), grantedAuthoritySet);
            }
        }
        return null;
    }
}
