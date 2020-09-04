package com.online.exam.controllers;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Base64;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.online.exam.beans.User;
import com.online.exam.service.DBUtility;
import com.online.exam.service.UserDaoService;
import com.online.exam.utility.ExamJSONUtility;

@Controller
public class AccessController {

	
	@RequestMapping(value="/Login", method = RequestMethod.GET)
    public String showLogin(@RequestParam(value ="message",required=false,defaultValue="") String mssg,  Model model){
		//loadSystemProperties();
		model.addAttribute("message", mssg);
		return "Login";
    }
	
	
	@RequestMapping(value="/Logout", method = RequestMethod.GET)
    public String logOut(@RequestParam(value ="message",required=false,defaultValue="") String mssg,  Model model,HttpServletRequest request){
		request.getSession().invalidate();
		if(mssg.equals(""))
			mssg = "Logged Out Successfully !!";
		model.addAttribute("message", mssg);
		return "Login";
    }
	
	/*@RequestMapping(value="/Login", method = RequestMethod.GET)
    public String showLogin(){
		return "Login";
    }*/
	
	 @RequestMapping(value="/access", method = RequestMethod.POST)
	    public String accessToPortal(HttpServletRequest request,
	            HttpServletResponse response, @RequestParam("role") String role
	            ,@RequestParam("enrollment") String enrollment,
	            @RequestParam(value = "accessid", required = false) String accessid){
		 if(enrollment != null && !enrollment.trim().equals("")){
			
			 User user = UserDaoService.getUserByName(enrollment);
			 
			 if(user.getPassword() !=null && !user.getPassword().isEmpty()) {
				 if(user.getPassword().equals(Base64.getEncoder().encodeToString(accessid.getBytes()))) {
					 request.getSession().setAttribute("USER",user);
					 System.out.println("Access granted to "+user);
					 return "redirect:ExamHome"; 
				 }else {
					 return "redirect:Login?message=Incorrect Username or password";
				 }
			 }
			 else if(role.equalsIgnoreCase("Admin")){

				 if(accessid.equalsIgnoreCase("admin")){
					 return "redirect:ExamHome"; 
				 }else{
					 return "redirect:Login?message=AccessDenied";
				 }

			 }else{
				 String examId =accessid;
				 //subject id hardcoded
				 if(ExamJSONUtility.getExamFromId(examId, "1")!=null){
					 return "redirect:startExam?examId="+examId+"&enrollId="+enrollment; 
				 }else{
					 return "redirect:Login?message=ExamIsNotActive"; 
				 }

			 }
		 }
			 
		 return "redirect:Login?message=LoginFailure";
	    }
	 
	
}
