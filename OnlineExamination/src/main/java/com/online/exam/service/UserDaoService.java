package com.online.exam.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.online.exam.beans.User;

public class UserDaoService {

	public static User getUserByName(String userName) {
		User user =null;
		Statement stmnt = null;
		ResultSet rs = null;
		Connection conn = DBUtility.getConnection();
		try {
			String query = "select * from USER where USERNAME ='"+userName+"'";
			stmnt = conn.createStatement();
			rs = stmnt.executeQuery(query);
			user = new User();
			while(rs.next()) {
				user.setId(rs.getInt("ID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setRole(rs.getString("ROLE"));
				user.setEmail(rs.getString("EMAIL"));
				user.setPassword(rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				try {
					if(rs != null)
						rs.close();
					if(stmnt != null)
						stmnt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return user;
	}
}
