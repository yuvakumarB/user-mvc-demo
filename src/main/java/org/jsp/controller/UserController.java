package org.jsp.controller;

import org.jsp.dao.UserDao;
import org.jsp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes(names= {"u"})
public class UserController {
@Autowired private UserDao dao;
 @PostMapping(value = "/save")
 public ModelAndView saveUser(@ModelAttribute User user, ModelAndView view) {
	 user=dao.saveUser(user);
	 view.setViewName("print.jsp");
	 view.addObject("msg", "User saved with Id:" + user.getId());
	 return view;
 }
 
 @PostMapping("/update")
 public ModelAndView updateUser(User user, ModelAndView view) {
	 user=dao.updatUser(user);
	 view.addObject("u", user);
	 view.addObject("msg", "user saved with Id:"+ user.getId());
	 view.setViewName("print.jsp");
	 return view;
 }
 
 @PostMapping(value = "/login")
 public ModelAndView login(@RequestParam long phone, @RequestParam String password) {
	 User user=dao.verifyUser(phone,password);
	 ModelAndView view=new ModelAndView();
	 if(user !=null) {
		 view.addObject("u", user);
		 view.setViewName("home.jsp");
		 return view;
	 }else {
		 view.addObject("msg", "Invalid Phone Number Or Password");
		 view.setViewName("login.jsp");
		 return view;
	 }
 }
 
 @GetMapping("/delete")
 public ModelAndView deleteUser(@RequestParam int id, ModelAndView view){
	 boolean isDeleted =dao.deleteUser(id);
	 if(isDeleted) {
		 view.setViewName("login.jsp");
		 view.addObject("msg", "Your Account is Deleted");
		 return view;
	 }else {
		 view.setViewName("login.jsp");
		 view.addObject("msg", "You must login to delete");
		 return view;
	 }
 }
}
