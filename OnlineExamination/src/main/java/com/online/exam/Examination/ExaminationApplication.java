package com.online.exam.Examination;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.online.exam.service.DBUtility;

@SpringBootApplication
@ComponentScan(basePackages={"com.online.exam.controllers"})
public class ExaminationApplication {

	static String defaultUser = "admin";
	public static void main(String[] args) {
		SpringApplication.run(ExaminationApplication.class, args);
		checkAndUpdateDB();
	}

	public static void checkAndUpdateDB() {
		Statement stmnt =null;
		try {
		Connection conn = DBUtility.getConnection();

		List<String> tables = new ArrayList<>();
		DatabaseMetaData meta = conn.getMetaData();
		ResultSet res = meta.getTables(null, null, null, 
		     new String[] {"TABLE"});
		  while (res.next()) {
			  tables.add(res.getString("TABLE_NAME"));
		  }
		  
		 
		 stmnt = conn.createStatement();
		  
		 if(!tables.contains("USER")) {
			 stmnt.execute(getStringContentFromFile("SQLS/USER.sql"));
				 System.out.println("Table created - "+"USER");
				 stmnt.execute("INSERT INTO USER (USERNAME,EMAIL,ROLE,PASSWORD) VALUES" 
						 +"('"+defaultUser+"','palakkhatri@gmail.com','admin','"
						 +Base64.getEncoder().encodeToString("admin1234".getBytes())+"')"
						 );
		 }
		 
		 if(!tables.contains("SUBJECT")) {
			 stmnt.execute(getStringContentFromFile("SQLS/SUBJECT.sql"));
			 stmnt.execute("INSERT INTO SUBJECT (SUBJECT_NAME) VALUES "
				+"("
				+"'ComputerScience'"
				+")");
				 System.out.println("Table created - "+"SUBJECT");
		 }
		 
		 if(!tables.contains("QUESTION")) {
			 stmnt.execute(getStringContentFromFile("SQLS/QUESTION.sql"));
				 System.out.println("Table created - "+"QUESTION");
		 }
		 
		 if(!tables.contains("EXAM")) {
			 stmnt.execute(getStringContentFromFile("SQLS/EXAM.sql"));
				 System.out.println("Table created - "+"EXAM");
		 }
		 
		 if(!tables.contains("EXAM_QUEST_JOIN")) {
			 stmnt.execute(getStringContentFromFile("SQLS/EXAM_QUEST_JOIN.sql"));
				 System.out.println("Table created - "+"EXAM_QUEST_JOIN");
		 }
		 
		 if(!tables.contains("RESULT")) {
			stmnt.execute( getStringContentFromFile("SQLS/RESULT.sql"));
				 System.out.println("Table created - "+"RESULT");
		 }
		}catch(SQLException  | IOException e) {
			e.printStackTrace();
		}finally {
			if(stmnt !=null)
				try {
					stmnt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		 
	}
	
	
	public static String getStringContentFromFile(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		String ls = System.getProperty("line.separator");
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}
		// delete the last new line separator
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		reader.close();
	
		return stringBuilder.toString();
	}
	
	/*private static void loadSystemProperties() {
		
		 FileReader reader;
		try {
			reader = new FileReader("instance.properties");
			  Properties p=new Properties();  
			  p.load(reader);  
			  for(Object key: p.keySet()) {
				  System.setProperty((String)key, (String)p.get((String)key));
			  }
			  System.setProperties(p);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}*/
}

