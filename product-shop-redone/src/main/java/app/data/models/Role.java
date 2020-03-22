package app.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

	@Column
	private String authority;
	
	public Role() {}

	
	public Role(String authority) {
		this.setAuthority(authority);
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

}
