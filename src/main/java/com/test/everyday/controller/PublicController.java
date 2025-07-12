package com.test.everyday.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.everyday.entity.Contacts;
import com.test.everyday.service.PublicService;

@Controller
public class PublicController {
	
	@Value("${app.phone}") // accessing the app.phone variable's value that we wrote in application.properties file, which will get stored in phone
	//                        variable just below. We want this to display on home page, so we set it in model.addAttribute() in "/" mapping
	String phone;
	
	private final PublicService publicService;
	
	public PublicController(PublicService publicService) {
		super();
		this.publicService = publicService;
	}

	
	// If we set title on each page dynamically using model, then on each mapping we have to add an attribute. We don't do this. So we want to use some
	// global data and for this we use a class called ModalAttribute for this where we set it once and access it anywhere we want(so for global data.
	// Another use of it is to send form data to Controller)
			
	// Now we can create one ModelAttribute method for each controller but better will be to create a single method for all controllers
			
	// controller -> View -> Model
	// View -> Controller -> ModelAttribute(To create global data access method which is executed before any request reaches the controller and we can
	//                                      also send data using it(like sending form data to controller))
	
	
	// Below is the ModelAttribute but here it is specific for this PublicController, but to make it global for all controller, we create a GlobalController
	// class in controller package and there we place it:
	
//	@ModelAttribute
//	public void setTitle(Model model) {
//		model.addAttribute("title", "EveryDayHelp");
//	}
	

	//	@GetMapping({"/", "/home", "/index"}) // we can create multiple endPoints for a mapping like this but it is not recommended
	@GetMapping("/")
	public String home(Model model) { // Model class is required to send data from Controller to view
		
		model.addAttribute("msg", "Easy Day Help Portal"); // key and value pairs.
		model.addAttribute("phone", phone); // we set the phone variable that we received at top
		
		return "public/home"; // SpringBoot internally knows dynamic pages are in templates folder so we don't need to write that in the beginning. Here this
		//                       home is view name, which is a .html page. We don't need to write .html explicitly. If a page of the view name specified here
		//                       is not found, an error is raised.
	}
	
	@GetMapping("/aboutUs")
	public String aboutUs() {
		return "public/about_us";
	}
	
	@GetMapping("/contactUs")
	public String contactUs() {
		return "public/contact_us";
	}
	
	@PostMapping("/contactUs")
	public String addContact(Contacts contacts, RedirectAttributes rda) { 
		
		publicService.addContact(contacts);
		
//		model.addAttribute("msg", "Thank you for contacting us");
		
		rda.addFlashAttribute("msg", "Thank you for contacting us");
		
//		return "public/contact_us";
		
		// Here the post mapping is the same as view mapping just above, in this case once we submit the form, then data will be inserted in DB, but if we
	    // refresh the page, then we will get confirm submission alert and data will be inserted again if we confirm. This is because forwarding like this
		// (i.e. returning the same page), will store data in cache and it will be submitted again. So solution is to redirect instead of returning same
		// view as just above. So we could either redirect to a separat page and from there give an option to go back, or redirect to the same view. So here
		// we will redirect to the same view:
		
		return "redirect:/contactUs"; // here, to redirect, after redirect:/, we write the endpoint of the @GetMapping for the contact_us view to redirect
		//                               to it, browser generates a new request and forward() carries same old request. Also, in this case, we don't do
		//                               model.addAttribute(), and in its place, we use flash to carry a message when redirecting. So for that we remove
		//                               Model model from arguments of this addContact() method and add RedirectAttributes rda and on it we use
		//                               addFlashAttribute() method
		
	}
}
