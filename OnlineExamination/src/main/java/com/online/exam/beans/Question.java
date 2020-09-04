package com.online.exam.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.online.exam.service.DBUtility;

public class Question {

	private int questionId;
	private String questionDesc;
	private int subjectId;
	private String questionType;
	private List<String> options;
	private List<String> answers;
	private double marks;
	//in seconds
	private int time;
	
	
	
	public Question() {
		super();
	}
	public Question(int questionId, String questionDesc, int subjectId,
			String questionType, List<String> options, List<String> answers,
			double marks, int time) {
		super();
		this.questionId = questionId;
		this.questionDesc = questionDesc;
		this.subjectId = subjectId;
		this.questionType = questionType;
		this.options = options;
		this.answers = answers;
		this.marks = marks;
		this.time = time;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getQuestionDesc() {
		return questionDesc;
	}
	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		List<String> trimedOptions = new ArrayList<String>();
		for(String option: options){
			option = option.trim();
			option.replace(",", "");
			trimedOptions.add(option);
		}
		this.options = trimedOptions;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public void setAnswers(List<String> answers) {
		List<String> trimedAns = new ArrayList<String>();
		for(String ans: answers){
			ans = ans.trim();
			ans.replace(",", "");
			trimedAns.add(ans);
		}
		this.answers = trimedAns;
	}
	public double getMarks() {
		return marks;
	}
	public void setMarks(double marks) {
		this.marks = marks;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	public String gtInsertSQL() {
		return "INSERT INTO QUESTION (QUESTION,QUESTION_TYPE,OPTIONS,ANSWER,TIME_ALLOTED,MARKS,SUBJECT_ID) VALUES "
				+"("
				+"'"+questionDesc+"','"+questionType+"','"+options+"','"+answers+"',"+time+","+marks+","+subjectId
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
