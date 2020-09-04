package com.online.exam.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.online.exam.beans.Exam;
import com.online.exam.beans.ExamResult;
import com.online.exam.beans.Question;
import com.online.exam.beans.User;
import com.online.exam.service.DBUtility;
import com.online.exam.service.UserDaoService;
import com.online.exam.utility.ExamJSONUtility;


@Controller
//@SessionAttributes("name")
//@RestController
public class ExamController {
	
	private static List<String> options = new ArrayList<>();
	private static List<String> answers = new ArrayList<>(1);
	
	static{
		for(int i=0;i<4;i++){
			options.add("");
		}
		answers.add("");
	}
	 @RequestMapping(value="/ExamHome", method = RequestMethod.GET)
	    public String showExamHome(ModelMap model){
		 System.out.println("Request to Examiner's Home");
	        return "ExaminerHome";
	    }
	 
	 @RequestMapping(value="/About", method = RequestMethod.GET)
	    public String aboutPage(ModelMap model){
		 System.out.println("Request to About Page");
	        return "about";
	    }
	 
	 @RequestMapping(value="/createUser", method = RequestMethod.GET)
	    public ModelAndView createUser(ModelMap model){
		 System.out.println("Request to Create User page");
		 User user = new User();
		 user.setRole("Admin");
		 return new ModelAndView("CreateUser" , "user", user);
	    }
	 
	 @RequestMapping(value="/createQuestion", method = RequestMethod.GET)
	    public ModelAndView createQuestion(ModelMap model){
		 System.out.println("Request to Create Question page");
		 Question question = new Question();
		 question.setOptions(options);
		 question.setAnswers(answers);
		 return new ModelAndView("CreateQuestion" , "question", question);
	    }
	 
	 @RequestMapping(value="/createExam", method = RequestMethod.GET)
	    public ModelAndView createExam(ModelMap model){
		 System.out.println("Request to create Exam Page");
		 //Subject id hardcoded
		 List<Question> questions = ExamJSONUtility.getQuestionsFromDir("1");
		 Exam exam = new Exam();
		 exam.setQuestions(questions);
	        return new ModelAndView("CreateExam" , "exam", exam);
	    }
	 
	 @RequestMapping(value="/showResults", method = RequestMethod.GET)
	    public ModelAndView showResults(ModelMap model){
		 System.out.println("Request to Result Summary Page");
		 Map<String, Integer> examResults = ExamJSONUtility.prepareResultSummary();
	     return new ModelAndView("Results" , "examResults", examResults);
	    }
	 
	 
	 @RequestMapping(value="/listExams", method = RequestMethod.GET)
	    public ModelAndView listExams(ModelMap model){
		 //subject hardcoded
		 System.out.println("Request to get list of Exams");
		 Map<String,String> exams = ExamJSONUtility.getListOfExamsInDir("1");
	     return new ModelAndView("ListOfExams" , "exams", exams);
	    }
	 
	 @RequestMapping(value="/resetPassword", method = RequestMethod.GET)
	    public ModelAndView resetPassword(ModelMap model,HttpServletRequest request){
		 //subject hardcoded
		 System.out.println("Request to reset password");
		 User user = (User) request.getSession().getAttribute("USER");
		 user.setPassword("");
	     return new ModelAndView("ResetPassword" , "user", user);
	    }
	 
	 
	 @RequestMapping(value = "/addUser", method = RequestMethod.POST)
	    public ModelAndView addUser(@Valid @ModelAttribute("user")User user, 
	    		BindingResult result, ModelMap model) {
		 String message = "";
		 if (result.hasErrors()) {
			 ModelAndView modelView = new ModelAndView("error" , "message", "Error occured while saving user");
			 return modelView;
		 }
		 System.out.println("Request to Save a User");
		
		 if((user.getUsername() !=null && !user.getUsername().trim().isEmpty())
				 && (user.getPassword() !=null && !user.getPassword().trim().isEmpty())
				 && (user.getEmail() != null && !user.getEmail().trim().isEmpty())) {
			 
			User existingUser = UserDaoService.getUserByName(user.getUsername());
			 
			if(existingUser != null 
					&& ((existingUser.getUsername() !=null && existingUser.getUsername().equals(user.getUsername()))
							|| (existingUser.getEmail()!=null && existingUser.getEmail().equals(user.getEmail())))) {
				 message = "User with given details already exists !!";
				 System.out.println(message);
				 ModelAndView modelView = new ModelAndView("CreateUser" , "message", message);
				 return modelView;
			}
			 user.executeInsert();
			 message = "User Added successfully";
			 System.out.println(message);
			 ModelAndView modelView = new ModelAndView("ExaminerHome" , "message", message);
			 return modelView;
			 
		 }else {
			 message = "User can not be saved with blank fields. All fields are mandatory !!";
			 System.out.println(message);
			 ModelAndView modelView = new ModelAndView("CreateUser" , "message", message);
			 return modelView;
		 }
		 
		
	 }
	 
	 @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	    public ModelAndView updatePassowrd(@Valid @ModelAttribute("user")User user, 
	    		BindingResult result, ModelMap model) {
		 String message = "";
		 if (result.hasErrors()) {
			 ModelAndView modelView = new ModelAndView("error" , "message", "Error occured while saving user");
			 return modelView;
		 }
		 System.out.println("Request to Update password");
		
		 if((user.getUsername() !=null && !user.getUsername().trim().isEmpty())
				 && (user.getPassword() !=null && !user.getPassword().trim().isEmpty())) {
			 
			User existingUser = UserDaoService.getUserByName(user.getUsername());
			 
			if(!user.getNewPassword().equals(user.getConfirmNewPassword())) {
				 message = "Newly added Password did not match. Please re-try";
				 System.out.println(message);
				 ModelAndView modelView = new ModelAndView("ResetPassword" , "message", message);
				 return modelView;
			}else if(existingUser != null 
					&& (existingUser.getUsername() !=null && existingUser.getUsername().equals(user.getUsername())
							&& existingUser.getPassword()!=null 
							&& !existingUser.getPassword().equals(
									Base64.getEncoder().encodeToString(user.getPassword().getBytes())))) {
				 message = "Incorrect Password!!";
				 System.out.println(message);
				 ModelAndView modelView = new ModelAndView("ResetPassword" , "message", message);
				 return modelView;
			}
			existingUser.setPassword(user.getConfirmNewPassword());
			existingUser.executeUpdate();
			 message = "Password updated successfully";
			 System.out.println(message);
			 ModelAndView modelView = new ModelAndView("ExaminerHome" , "message", message);
			 return modelView;
			 
		 }else {
			 message = "User can not be updated with blank fields. All fields are mandatory !!";
			 System.out.println(message);
			 ModelAndView modelView = new ModelAndView("ResetPassword" , "message", message);
			 return modelView;
		 }
		 
		
	 }
	 
	 @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	    public ModelAndView addQuestion(@Valid @ModelAttribute("question")Question question, 
	      BindingResult result, ModelMap model) {
	        if (result.hasErrors()) {
	        	 ModelAndView modelView = new ModelAndView("error" , "message", "Error occured");
				 return modelView;
	        }
	      System.out.println("Request to Submit a question");
	      question.setQuestionId(ExamJSONUtility.getQuestionId());
	  
	      for(int i=0;i<question.getAnswers().size();i++){
			String ans = question.getAnswers().get(i).trim();
			ans=ans.replace(",", "");
			question.getAnswers().set(i, ans);
	      }
	      System.out.println(question.getQuestionDesc());
	      System.out.println(question.getMarks());
	      System.out.println(question.getTime());
	      ExamJSONUtility.writeQuestionObjectToFile(question);
	      question.executeInsert();
	      ModelAndView modelView = new ModelAndView("ExaminerHome" , "message", "Question successfully added to Set");
			 return modelView;
	    }
	 
	 
	 @RequestMapping(value = "/addExam", method = RequestMethod.POST)
	    public ModelAndView addExam(@Valid @ModelAttribute("exam")Exam exam, 
	      BindingResult result, ModelMap model) {
	        if (result.hasErrors()) {
	        	 ModelAndView modelView = new ModelAndView("error" , "message", "Error occured");
				 return modelView;
	        }
	      exam.setExamId(exam.getExamName().replace(" ", "_")+"_"+ExamJSONUtility.getExamId());
	      exam.setCreateDt(new Date(System.currentTimeMillis()));
	      System.out.println(exam.getQuestinIds().size());
	      System.out.println(exam.getTotalMarks());
	      System.out.println(exam.getTotalTime());
	      ExamJSONUtility.writeExamObjectToFile(exam);
	      exam.executeInsert();
	      System.out.println("Exam created successfully");
	      ModelAndView modelView = new ModelAndView("ExaminerHome" , "message", "Exam created successfully");
			 return modelView;
	    }
	 
	 
	 
	 @RequestMapping(value="/startExam", method = RequestMethod.GET)
	    public ModelAndView startExam(@RequestParam(value ="examId",required=true) String examId,
	    		@RequestParam(value ="enrollId",required=true) String enrollId,
	    		ModelMap model){
		 //Subject id hardcoded
		 Exam exam = ExamJSONUtility.readExamObjectFromFile("1", examId);
		 List<String> questionIds = exam.getQuestinIds();
		 List<Question> questions = new ArrayList<Question>();
		 Map<String, String>  questnAnsMap = new HashMap<>();

		 for(String questionId: questionIds){
			Question question = ExamJSONUtility.readQuestionObjectFromFile("1",questionId);
			questions.add(question);
			questnAnsMap.put(questionId, "");
		 }
		 
		 ExamResult result = new ExamResult();
		 result.setExamId(exam.getExamId());
		 result.setSubjectId(exam.getSubjectId());
		 result.setEnrollmentId(enrollId);
		 result.setQuestions(questions);
		 result.setQuestAnsMap(questnAnsMap);
		 
		 ModelAndView modelView = new ModelAndView("RunningExam" , "examResult", result);
		 modelView.addObject("totalTime", exam.getTotalTime());
	     return modelView;
	    }
	 
	 @RequestMapping(value = "/generateResult", method = RequestMethod.POST)
	    public String generateResult(@Valid @ModelAttribute("examResult")ExamResult examResult, 
	      BindingResult result, ModelMap model) {
	        if (result.hasErrors()) {
	            return "error";
	        }
	     examResult.setNumberOfAttemptedQuest(examResult.getQuestAnsMap().size());
	     ExamJSONUtility.processResult(examResult);
	      ExamJSONUtility.writeResultObjectToFile(examResult);
	      examResult.executeInsert();
	      System.out.println("Result Generated successfully");
	      model.addAttribute("message", "Exam Completed Successfully");
	        return "Login";
	    }
	 
	 @RequestMapping(value = "/getResultForExam", method = RequestMethod.GET)
	   public ModelAndView generateResultOfExam(@RequestParam(value ="examId",required=true)String examId) {
		 Map<String, Double> examResults = ExamJSONUtility.prepareExamResultSummary(examId);
		 ModelAndView modelNView = new ModelAndView("ExamResults" , "examResults", examResults);
		 modelNView.addObject("examId", examId);
		 return modelNView;
	    }
	 
	 
	 @RequestMapping(value = "/getExamDetail", method = RequestMethod.GET)
	   public ModelAndView getExamDetail(@RequestParam(value ="examId",required=true)String examId) {
		//Subject id hardcoded
		 Exam exam = ExamJSONUtility.getExamDetail("1",examId);
		 ModelAndView modelNView = new ModelAndView("CreateExam" , "exam", exam);
		 modelNView.addObject("examId", examId);
		 return modelNView;
	    }
	 
	 
	 @RequestMapping(value = "/getResultOfEnrollment", method = RequestMethod.GET)
	   public ModelAndView getResultOfEnrollment(@RequestParam(value ="examId",required=true)String examId,
			   @RequestParam(value ="enrollmentId",required=true)String enrollmentId) {
		 ExamResult result = ExamJSONUtility.getExamDetailForEnrollment(examId,enrollmentId);
		 return new ModelAndView("StudentResult" , "enrollmentResult", result);
	    }
}
