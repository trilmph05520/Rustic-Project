//package com.vn.controller;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class LanguageController {
//	@Autowired
//	private MessageSource messageSource;
//
//	@RequestMapping("/")
//	public String index(Model model, HttpServletRequest request) {
//		String message = messageSource.getMessage("hello", null, "default message", request.getLocale());
//		model.addAttribute("message", message);
//		return "index";
//	}
//
//}
