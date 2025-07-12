package com.test.everyday.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice // so that we don't have to write in each controller
public class GlobalController {
	
	@Value("${app.title}") // here we get the value written in app.title variable in aaplication.properties file
	String webSiteTitle; // whatever value we get from app.title, is stored in webSiteTitle variable. We then pass this in the model.addAttribute() in
	//                      setTitle() method below:
	
	// Note that all variables set in application.properties are String, so we store them in String variables(like String webSiteTitle)
	
	@ModelAttribute
	public void setTitle(Model model) {
		model.addAttribute("title", webSiteTitle);
	}
	
}
