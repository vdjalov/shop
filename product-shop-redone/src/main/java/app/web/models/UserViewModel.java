package app.web.models;

import java.util.Set;

import app.data.models.Role;

public class UserViewModel {

	private String username;
	private String email;
	private Set<Role> authorities;
	public UserViewModel() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}
	
	
	
	
	
}
