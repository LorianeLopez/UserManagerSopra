package llop.com.servlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class User {
	
	private String email;
	private  String pwd;
	private String name;
	private String salt;
	
	public User(String email, String pwd, String name, String salt) {
		this.email = email;
		this.pwd = pwd;
		this.name = name;
		this.salt = salt;
	}
	
	public User() {
		super();
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String[] getUser(String email) {
		String[] params = {email, pwd, name};
		return params;
	}
	
	public String hashing (String str) throws NoSuchAlgorithmException {
		MessageDigest mdp = MessageDigest.getInstance("MD5");
	    mdp.update(str.getBytes());
	    byte[] digest = mdp.digest();
	    str = DatatypeConverter.printHexBinary(digest).toUpperCase();
	    return str;
	}
	
	

}
