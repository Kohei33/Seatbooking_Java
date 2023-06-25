package com.example.demo.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class User implements UserDetails{
    private BigInteger id;

    private String username;

    private String password;

    private LocalDateTime create_date;

    private LocalDateTime update_date;

    // 以下はログインで使用する
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return null;
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
