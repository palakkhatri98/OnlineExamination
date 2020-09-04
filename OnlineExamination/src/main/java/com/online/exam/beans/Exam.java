package com.online.exam.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.online.exam.service.DBUtility;

public class Exam {

	private String examId;
	private int subjectId;
	private String examName;
	private double totalMarks;
	private int totalTime;
	private List<Question> questions;
	private List<String> questinIds;
	private String createdBy;
	private Date createDt;
	
	
	
	public Exam() {
		super();
	}
	public Exam(String examId, int subjectId, String examName, double totalMarks,
			int totalTime, List<Question> questions, String createdBy,
			Date createDt) {
		super();
		this.examId = examId;
		this.subjectId = subjectId;
		this.examName = examName;
		this.totalMarks = totalMarks;
		this.totalTime = totalTime;
		this.questions = questions;
		this.createdBy = createdBy;
		this.createDt = createDt;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public double getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public List<String> getQuestinIds() {
		return questinIds;
	}
	public void setQuestinIds(List<String> questinIds) {
		this.questinIds = questinIds;
	}
	
	public String gtExamInsertSQL() {
		return "INSERT INTO EXAM  (EXAM_DESC,SUBJECT_ID) VALUES "
				+"("
				+"'"+examName+"',"+subjectId
				+");";
	}
	
	public void executeInsert() {
		Connection conn = DBUtility.getConnection();
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.execute(gtExamInsertSQL());
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
