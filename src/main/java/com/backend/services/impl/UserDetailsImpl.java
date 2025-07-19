package com.backend.services.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.entities.Customer;
import com.backend.entities.ServiceProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String email;
	@JsonIgnore
	private String password;
	private String mobileNumber;
	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(Customer customer) {
		Set<GrantedAuthority> authorities = customer.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toSet());
		
		return new UserDetailsImpl(customer.getCustomerId(), customer.getEmail(), customer.getPassword(), customer.getMobileNumber(), authorities);
	}
	
	public static UserDetailsImpl build(ServiceProvider provider) {
		Set<GrantedAuthority> authorities = provider.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toSet());
		
		return new UserDetailsImpl(provider.getProviderId(), provider.getEmail(), provider.getPassword(), provider.getMobileNumber(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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
