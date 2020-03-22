package app.service.models;

import javax.validation.constraints.Pattern;

public class EditUserProfileModel {

	private String username;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPassword;
	
	@Pattern(regexp = "(^[\\W\\w]+)@{1}([\\W\\w]+)\\.([a-z]{2,3}$)", message = "invalid email format")
	private String email;
	
	public EditUserProfileModel() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
