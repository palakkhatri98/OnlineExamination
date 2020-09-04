package com.online.exam.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import com.online.exam.service.DBUtility;

public class User {

	private int id;
	private String username;
	private String password;
	private String role;
	private String email;
	private String newPassword;
	private String confirmNewPassword;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String gtInsertSQL() {
		return "INSERT INTO USER (USERNAME,ROLE,EMAIL,PASSWORD) VALUES "
				+"("
				+"'"+username+"','"+role+"','"+email+"','"
				+Base64.getEncoder().encodeToString(password.getBytes())+"'"
				+");";
	}
	
	public void executeUpdate() {
		Connection conn = DBUtility.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute("UPDATE USER SET USERNAME ='"+username+"',ROLE ='"+role+"',EMAIL='"+email+"',PASSWORD='"
			+Base64.getEncoder().encodeToString(password.getBytes())+"' WHERE ID="+id
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	public void executeInsert() {
		Connection conn = DBUtility.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(gtInsertSQL());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
