package com.test.everyday.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.everyday.entity.Admin;
import com.test.everyday.entity.Booking;
import com.test.everyday.entity.Employee;
import com.test.everyday.entity.Feedback;
import com.test.everyday.entity.UserDetails;
import com.test.everyday.repository.BookingRepository;
import com.test.everyday.repository.EmployeeRepository;
import com.test.everyday.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	private final BookingRepository bookingRepository;
	private final EmployeeRepository employeeRepository;
	private final UserService userService;
	 
	 
	 public UserController(BookingRepository bookingRepository, EmployeeRepository employeeRepository, UserService userService) {
		super();
		this.bookingRepository = bookingRepository;
		this.userService = userService;
		this.employeeRepository = employeeRepository;
	}


	@GetMapping("userHome")
		public String userHome(Model model, HttpSession session, RedirectAttributes rda) {
			
			UserDetails userDetails = (UserDetails)session.getAttribute("userKey"); // session was set in @PostMapping("userLogin") below
			
			if(userDetails != null) {
				
				model.addAttribute("user", userDetails); 
				
				return "user/user_home";
			}
			else {
				
				rda.addFlashAttribute("msg","Unauthorised access,login first");
				
				return "user/user_login";
			}
			
		}
	 
	
	 ////////////////////////////////// REGISTER //////////////////////////////////
	 @GetMapping("/userRegister")
	 public String register() {
		 return "user/user_registration";
	 }
	
	 
	@PostMapping("/userRegister")
	public String addUser(UserDetails userDetails, RedirectAttributes rda, @RequestParam("profilepic") MultipartFile pic) {
		// We use @RequestParam to take file upload. In (), we write the value of name attribute that we set in html. Then variable pic(which can be
		// anything not just pic) of type MultipartFile is set
		
		// When we take data from form, model attribute works internally, here we didn't use it cause there was no need
		
		userService.addUser(userDetails, pic);
		
		rda.addFlashAttribute("msg", "Registered Successfully");
		
		return "redirect:/userRegister";
	}
	
	
    ////////////////////////////////// LOGIN //////////////////////////////////
	@GetMapping("userLogin")
	 public String login() {
		 return "user/user_login";
	 }
	 
	
	 @PostMapping("userLogin")
	 public String userLogin(@RequestParam String email, @RequestParam("password") String pass, Model model, HttpSession session, RedirectAttributes rda) {
		 
		 UserDetails userDetails = userService.userLogin(email, pass); 
		 
		 if(userDetails != null) {
			 
			 model.addAttribute("user", userDetails); 
			 
			 session.setAttribute("userKey", userDetails); 
			 
			 return "user/user_home";
		 }
		 
		 rda.addFlashAttribute("msg", "Invalid Email/Password");
		 
		 return "redirect:/userLogin";
	 }
	 
	 
     ////////////////////////////////// EDIT PROFILE //////////////////////////////////
	 @GetMapping("/editProfile")
	 public String showEditProfile(HttpSession session, Model model, RedirectAttributes rda) {
	     UserDetails user = (UserDetails) session.getAttribute("userKey");

	     if (user == null) {
	         rda.addFlashAttribute("msg", "Unauthorized access. Please login first.");
	         return "redirect:/userLogin";
	     }

	     model.addAttribute("user", user); // Pre-fill values
	     return "user/edit_profile";
	 }

	 @PostMapping("/editProfile")
	 public String updateProfile(@RequestParam("name") String name,
	                             @RequestParam("phone") String phone,
	                             @RequestParam("address") String address,
	                             @RequestParam("email") String email,
	                             @RequestParam("profilepic") MultipartFile file,
	                             HttpSession session,
	                             RedirectAttributes rda) {
	     userService.updateUser(email, name, phone, address, file);
	     
	     // Update session object as well
	     UserDetails updatedUser = userService.getUserByEmail(email);
	     session.setAttribute("userKey", updatedUser);

	     rda.addFlashAttribute("msg", "Profile updated successfully");
	     return "redirect:/userHome";
	 }

	 
     ////////////////////////////////// FEEDBACK //////////////////////////////////
	 @GetMapping("userFeedback")
		public String userFeedback(HttpSession session, Model model, RedirectAttributes rda)
		{
			 UserDetails user = (UserDetails)session.getAttribute("userKey");
			 if(user!=null)
			 {
				 model.addAttribute("userInfo", user);
				 return "user/user_feedback";
			 }
			 else
			 {
				 rda.addFlashAttribute("msg", "Unauthorized access, Please login first");
					return "redirect:/userLogin";
			 }
				 
		}
		
		@PostMapping("userFeedback")
		public String addFeedback(@ModelAttribute Feedback feedback, RedirectAttributes rda)
		{
			userService.addFeedback(feedback);
			rda.addFlashAttribute("msg", "Thank you for your feedback");
			
			return "redirect:/userHome";
		}
	
	 
     ////////////////////////////////// LOGOUT //////////////////////////////////
     @GetMapping("userLogout")
	 public String adminHome(HttpSession session, RedirectAttributes rda) {
			 
         session.removeAttribute("userKey");
			 
		 session.invalidate();
		 
		 rda.addFlashAttribute("msg", "Successfully logged out");
			 
		 return "redirect:/userLogin";
     }
     
     
     ////////////////////////////////// BOOKINGS //////////////////////////////////
     @GetMapping("booking")
 	 public String showBookingForm(Model model, HttpSession session, RedirectAttributes rda) {
    	 
    	 UserDetails userDetails = (UserDetails)session.getAttribute("userKey"); // session was set in @PostMapping("userLogin") below
			
			if(userDetails != null) {
				
				model.addAttribute("user", userDetails); 
				model.addAttribute("booking", new Booking());
		        return "user/booking";
			}
			else {
				
				rda.addFlashAttribute("msg","Unauthorised access,login first");
				
				return "user/user_login";
			}
     }
     
     @GetMapping("/employeeInfo")
     @ResponseBody
     public Employee getEmployeeInfo(@RequestParam String serviceType,
                                     @RequestParam String employeeType) {
         return employeeRepository.findByServiceTypeAndEmployeeType(serviceType, employeeType);
     }


 	 @PostMapping("booking")
 	    public String submitBooking(@ModelAttribute("booking") Booking booking) {
 	        userService.saveBooking(booking);
 	        return "redirect:/userHome";
 	  }
 	 
 	 
 	 @GetMapping("myBookings")
 	 public String userBookings(Model model) {
 	     List<Booking> bookings = bookingRepository.findAll(); // Filter by user if login session exists
 	     model.addAttribute("bookings", bookings);
 	     return "user/user_bookings";
 	 }
		 
}
