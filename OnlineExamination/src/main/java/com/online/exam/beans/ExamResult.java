package com.online.exam.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.online.exam.service.DBUtility;

public class ExamResult {

	private String enrollmentId;
	private String examId;
	private int subjectId;
	private int numberOfAttemptedQuest;
	private int numberOfCorrectAns;
	private double marksObtained;
	private List<Question> questions;
	Map<String,String> questAnsMap;
	
	
	public ExamResult() {
		super();
	}
	
	public ExamResult(String enrollmentId, String examId, int numberOfAttemptedQuest,
			int numberOfCorrectAns, double marksObtained,
			List<Question> questions, Map<String, String> questAnsMap) {
		super();
		this.enrollmentId = enrollmentId;
		this.examId = examId;
		this.numberOfAttemptedQuest = numberOfAttemptedQuest;
		this.numberOfCorrectAns = numberOfCorrectAns;
		this.marksObtained = marksObtained;
		this.questions = questions;
		this.questAnsMap = questAnsMap;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public int getNumberOfAttemptedQuest() {
		return numberOfAttemptedQuest;
	}
	public void setNumberOfAttemptedQuest(int numberOfAttemptedQuest) {
		this.numberOfAttemptedQuest = numberOfAttemptedQuest;
	}
	public int getNumberOfCorrectAns() {
		return numberOfCorrectAns;
	}
	public void setNumberOfCorrectAns(int numberOfCorrectAns) {
		this.numberOfCorrectAns = numberOfCorrectAns;
	}
	public double getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(double marksObtained) {
		this.marksObtained = marksObtained;
	}
	public Map<String, String> getQuestAnsMap() {
		return questAnsMap;
	}
	public void setQuestAnsMap(Map<String, String> questAnsMap) {
		for(String key: questAnsMap.keySet()){
			questAnsMap.put(key, questAnsMap.get(key).replace(",", ""));
		}
		this.questAnsMap = questAnsMap;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public String gtInsertSQL() {
		return "INSERT INTO RESULT (USER_ID,EXAM_ID,MARKS_OBTAINED,QUEST_ATTEMPT,CORRECT_ATTEMPT) VALUES "
				+"("
				+enrollmentId+","+examId+","+marksObtained+","+numberOfAttemptedQuest+","+numberOfCorrectAns
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
