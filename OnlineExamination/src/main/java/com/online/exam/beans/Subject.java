package com.online.exam.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.online.exam.service.DBUtility;

public class Subject {

	private int subjectId;
	private String subjectName;
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public String gtInsertSQL() {
		return "INSERT INTO SUBJECT (SUBJECT_NAME) VALUES "
				+"("
				+"'"+subjectName+"'"
				+");";
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
