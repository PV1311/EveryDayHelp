package com.test.everyday.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.everyday.entity.Admin;
import com.test.everyday.entity.AdminNotice;
import com.test.everyday.entity.Booking;
import com.test.everyday.entity.Contacts;
import com.test.everyday.entity.Employee;
import com.test.everyday.entity.UserDetails;
import com.test.everyday.repository.BookingRepository;
import com.test.everyday.service.AdminService;

import jakarta.servlet.http.HttpSession;
//import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor // making parameterized constructor with lombok. lombok will make required arguments constructor for only those fields that are
//                          defined with final or are non-null(for non-null, we can define @NonNull annotation above the field);
public class AdminController {
	
	private final BookingRepository bookingRepository;
	private final AdminService adminService;
//	@NonNull
//	private String name;
	
	// Remember, lombok will make required arguments constructor for only those fields that are defined with final or are non-null;
	

//	public AdminController(AdminService adminService) {
//		super();
//		this.adminService = adminService;
//	}
	
    ////////////////////////////////////////// WORKING WITH ADMIN //////////////////////////////////////////
	@GetMapping("adminHome")
	public String adminHome(Model model, HttpSession session, RedirectAttributes rda) {
		
        // model.addAttribute("msg", "Easy Day Help Portal"); // key and value pairs.
		
		// On admin_home page, we are fetching email and displaying, so we have to get the session in which we set the admin object in the Login mapping
		// below, and from it it we get the adminKey attribute, which is the key to which we binded the admin object in the session, and we set that
		// key in model.addAttribute() and with it we bind the admin object. Without it we will get error when we access admin home page from this
		// GetMapping:
		
		// Moreover, we do so as below so no one can access the admin home page directly from the URL:
		
		Admin admin=(Admin)session.getAttribute("adminKey");
		
		if(admin!=null) {
			
		    model.addAttribute("adminInfo",admin); // set the same key as we set in login mapping below, because in html page too we are accessing value from
		    //                                        this key
		
		    return "admin/admin_home";
		}
		else {
			
			rda.addFlashAttribute("msg","Unauthorised access,login first");
			return "admin/admin_login";
		}

		
//		Admin admin = (Admin)session.getAttribute("adminKey");
//		
//		model.addAttribute("adminInfo", admin); // set the same key as we set in login mapping below, because in html page too we are accessing value from
//		//                                         this key
//		
//		
//		return "admin/admin_home";
	}
	
	
	@GetMapping("adminLogin")
	 public String login() {
		 return "admin/admin_login";
	 }
	 
	 
	 @PostMapping("adminLogin")
	 public String adminLogin(@RequestParam String email, @RequestParam("password") String pass, Model model, HttpSession session, RedirectAttributes rda) {
		 // here parameter name and the variable we are receiving it in, is same, so we can skip brackets() of @RequestParam, so, if it is
		 // like @RequestParam("email") String email, we can just write @RequestParam String email. This "email" inside @RequestParam() was
		 // the name of the input field in our form. If the field name is different from the variable we receive it in(let's say) "password"
		 // is received in pass, then we have to use brackets() with @RequestParam, so like @RequestParam("password") String pass
		 
		 Admin admin = adminService.adminLogin(email, pass); // this method will return object of that entity (i.e. Admin type)
		 
		 if(admin != null) {
			 // System.out.println(admin.getEmail());
			 
			 model.addAttribute("adminInfo", admin); // whenever admin logins, we want to greet him with his name, so we do this. Whatever attribute we set
			 //                                         here with model, it will only be fetched on admin home, so to fetch data on other pages, we set
			 //                                         session below, i.e. to trap admin for multiple requests.
			 
			 session.setAttribute("adminKey", admin); // we bind the admin object in session.
			 
			 return "admin/admin_home";
		 }
		 
		 rda.addFlashAttribute("msg", "Invalid Email/Password");
		 
		 return "redirect:/admin/adminLogin";
	 }
	 
	 
	 // For logout :
	 @GetMapping("adminLogout")
    public String adminHome(HttpSession session, RedirectAttributes rda) {
		 
		 session.removeAttribute("adminKey");
		 
		 session.invalidate();
		 rda.addFlashAttribute("msg", "Successfully logged out");
		 
		 return "redirect:/admin/adminLogin";
	 }
	
	 
    ////////////////////////////////////////// WORKING WITH USER //////////////////////////////////////////
	@GetMapping("allUsers")
	public String allUsers(Model model) {
		
		List<UserDetails> userList = adminService.allUsers();
		
		model.addAttribute("users", userList);
		
		return "admin/all_users";
	}
	
	
	@GetMapping("editUser")
	public String editUser(@RequestParam String email, Model model) {
	    UserDetails user = adminService.editUser(email);
	    model.addAttribute("user", user);
	    return "admin/edit_user";
	}
	
	
	@PostMapping("editUser")
	public String editUserFinal(@ModelAttribute UserDetails modifiedUser) {
		
		adminService.editUserFinal(modifiedUser);
		
		return "redirect:/admin/allUsers";
	}
	
	
	@GetMapping("deleteUser")
	public String deleteUser(@RequestParam String email, Model model)
	{
		adminService.deleteUser(email);
		return "redirect:/admin/allUsers";
	}
	
	
    ////////////////////////////////////////// WORKING WITH CONTACTS //////////////////////////////////////////
	@GetMapping("allContacts")
	public String allContacts(Model model) {
		
		List<Contacts> contactList = adminService.allContacts();
		
		model.addAttribute("contacts", contactList);
		
		return "admin/all_contacts";
	}
	
	@GetMapping("deleteContact")
	public String deleteContact(@RequestParam  int contactId, Model model)
	{
		adminService.deleteContact(contactId);
		return "redirect:/admin/allContacts";
	}
	
	
    ////////////////////////////////////////// WORKING WITH EMPLOYEES //////////////////////////////////////////
	@GetMapping("addEmployee")
	 public String addEmployee() {
		 
		 // TESTING LOMBOK:
		 /*
		 AdminNotice n = new AdminNotice();
		 n.setTitle("for offer");
		 System.out.println(n); // with this we can see default toString() is overriden, but this will happens if we do @Data annotation, which is
		 //                        equivalent to all annotations getting added
		 */
		 		 
	     return "admin/employee";
	 }
	 
	 @PostMapping("addEmployee")
	 public String addEmployee(Employee employee, @RequestParam("profileImage") MultipartFile file, RedirectAttributes rda){
		 
	     adminService.addEmployee(employee, file);
	     
	     rda.addFlashAttribute("msg", "Employee added");
	     
	     return "redirect:/admin/addEmployee";
	 }
	 
	 
	@GetMapping("allEmployees")
	public String allEmployees(Model model) {
		
		List<Employee> employeeList = adminService.allEmployees();
		
		model.addAttribute("employees", employeeList);
		
		return "admin/all_employees";
	}
	
	
	@GetMapping("editEmployee")
	public String editEmployee(@RequestParam String employee_id, Model model) {
		
		System.out.println(employee_id);
		
	    Employee employee = adminService.editEmployee(employee_id);
	    
	    model.addAttribute("employee", employee);
	    
	    return "admin/edit_employee";
	}
	
	
	@PostMapping("editEmployee")
	public String editEmployeeFinal(@ModelAttribute Employee modifiedEmployee) {
		
		adminService.editEmployeeFinal(modifiedEmployee);
		
		return "redirect:/admin/allEmployees";
	}
	
	
	@GetMapping("deleteEmployee")
	public String deleteEmployee(@RequestParam String employee_id, Model model)
	{
		adminService.deleteEmployee(employee_id);
		return "redirect:/admin/allEmployees";
	}
	
	
    ////////////////////////////////////////// WORKING WITH NOTICES //////////////////////////////////////////
	@GetMapping("addNotice")
	 public String addNotice(Model model) {
		 AdminNotice notice = new AdminNotice(); // thymeleaf says, when you take data from page to controller, give it an object and it will bind data to it,
		 //                                         So we made the object here. So when the view is returned, this object also goes there and in this object
		 //                                         data is binded. Then when request comes to PostMapping below on clicking on Submit button, the data is
		 //                                         extracted from it. Below the notice variable of AdminNotice type is that object containing the data that
		 //                                         comes from frontend. We put @ModelAttribute there for that.
		 
		 model.addAttribute("noticeObj", notice); // we bind the notice object in this noticeObj key and then use this key in the form th:object
		 
		 return "admin/admin_notice";
	 }
	 
	 
	 @PostMapping("postNotice")
	 public String addNotice(@ModelAttribute AdminNotice notice, RedirectAttributes rda) {
		
	     adminService.addNotice(notice);
	     rda.addFlashAttribute("msg", "Notice added successfully");
	     return "redirect:/admin/addNotice"; // in redirect we put the endpoint of GetMapping
	 }
	 
	
	 @GetMapping("allNotices")
	 public String allNotices(Model model) {
		 
		 List<AdminNotice> noticeList = adminService.allNotices();
			
		 model.addAttribute("notices", noticeList);
			
		 return "admin/all_notices";
		 
	 }
	 
	 
	 @GetMapping("deleteNotice")
	 public String deleteNotice(@RequestParam int noticeId, RedirectAttributes rda) {
		 
		int status = adminService.deleteNotice(noticeId);
		
		if(status != 1)
		{
			rda.addFlashAttribute("message", "There was an error deleting notice");
			return "redirect:/admin/allNotices";
		}
			return "redirect:/admin/allNotices";
	 }
	 
	 
     ////////////////////////////////////////// WORKING WITH BOOKINGS //////////////////////////////////////////
	 @GetMapping("/bookings")
	 public String viewBookings(Model model) {
	     List<Booking> bookings = adminService.getAllBookings();
		 model.addAttribute("bookings", bookings);
		 return "admin/admin_bookings";
	 }

	 
	 @GetMapping("/booking/actionForm/{id}")
	 public String showActionForm(@PathVariable("id") Integer id, Model model) {
	     Booking booking = bookingRepository.findById(id).orElse(null);
	     if (booking == null) {
	         return "redirect:/admin/bookings"; // fallback if booking not found
	     }
	     model.addAttribute("booking", booking);
	     return "admin/booking_action_form";
	 }

	 @PostMapping("/booking/process")
	 public String processBookingAction(@RequestParam("id") Integer id,
	                                    @RequestParam("adminMessage") String adminMessage,
	                                    @RequestParam("action") String action) {
	     Booking booking = bookingRepository.findById(id).orElse(null);
	     if (booking != null) {
	         booking.setAdminMessage(adminMessage);
	         booking.setStatus(action.equals("approve") ? "approved" : "rejected");
	         bookingRepository.save(booking);
	     }
	     return "redirect:/admin/bookings";
	 }
	    
}
