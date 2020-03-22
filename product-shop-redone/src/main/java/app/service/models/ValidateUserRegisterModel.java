package app.service.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import app.validation.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
})
public class ValidateUserRegisterModel {

	@NotEmpty(message = "username cannot be empty")
	private String username;
	
	@NotEmpty(message = "password cannot be empty")
	private String password;
	
	@NotEmpty(message = "confirm password cannot be empty")
	private String confirmPassword;
	
	@Pattern(regexp = "(^[\\W\\w]+)@{1}([\\W\\w]+)\\.([a-z]{2,3}$)")
	private String email;

	public ValidateUserRegisterModel() {}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confrirmPassword) {
		this.confirmPassword = confrirmPassword;
	}
	
}
