package com.test.everyday.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.test.everyday.entity.Admin;
import com.test.everyday.entity.AdminNotice;
import com.test.everyday.entity.Booking;
import com.test.everyday.entity.Contacts;
import com.test.everyday.entity.Employee;
import com.test.everyday.entity.UserDetails;
import com.test.everyday.repository.AdminNoticeRepository;
import com.test.everyday.repository.AdminRepository;
import com.test.everyday.repository.BookingRepository;
import com.test.everyday.repository.ContactRepository;
import com.test.everyday.repository.EmployeeRepository;
import com.test.everyday.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final BookingRepository bookingRepository;
	private final AdminRepository adminRepo;
	private final EmployeeRepository employeeRepo;
	private final AdminNoticeRepository adminNoticeRepo;
	private final UserRepository userRepo;
	private final ContactRepository contactRepo;
	
	private final JdbcTemplate template;

	// BELOW IS COMMENTED BECAUSE CONSTRUCTOR WILL BE MADE BY LOMBOK:
//	public AdminService(AdminRepository adminRepo, EmployeeRepository employeeRepo) {
//		super();
//		this.adminRepo = adminRepo;
//		this.employeeRepo = employeeRepo;
//	}


public void addEmployee(Employee employee, MultipartFile file) {
		
//		// Check if employee ID is valid
//	    if (employee.getEmployee_id() == null || employee.getEmployee_id().isEmpty()) {
//	        rda.addFlashAttribute("msg", "Employee ID cannot be empty.");
//	        return;
//	    }
//
//	    // Check if the employee ID already exists in DB
//	    Optional<Employee> existingEmp = employeeRepo.findById(employee.getEmployee_id());
//	    if (existingEmp.isPresent()) {
//	        rda.addFlashAttribute("msg", "Employee ID already exists!");
//	        return;  // Do NOT proceed with saving
//	    }
		
		try {
			// 1) Get the root directory of running project:
			String projectRoot = System.getProperty("user.dir");
			
			// 2) Define folder name inside the project:
			String folderName = "employeePics";
			
			// 3) Construct full path for the upload directory:
			String uploadPath = projectRoot + File.separator + folderName;
			
			// 4) Create the upload directory if it doesn't exist:
			File uploadDir = new File(uploadPath);
			
			if(!uploadDir.exists()) {
				uploadDir.mkdirs(); // creates parent directories if needed
			}
			
			// 5) Get filename from the MultiPart:
			String originalFilename = file.getOriginalFilename();
			
			// 6) Generate a unique filename to avoid conflicts:
			String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
			
			// 7) Create destination file:
			File destinationFile = new File(uploadDir, uniqueFilename);
			
			// 8) Transfer the file from MultipartFile to the destination:
			file.transferTo(destinationFile);
			
			// 9) Save relative path(folderName/filename) to the database:
			String DB_PATH = folderName + "/" + uniqueFilename;
			
			employee.setEmployeePic(DB_PATH);						
			employeeRepo.save(employee);
			
		}catch(IOException io){
			
			System.err.println("Image upload failed!");
			io.printStackTrace();
		}
	}

	
	public void addNotice(AdminNotice notice) {
		
	    adminNoticeRepo.save(notice); 
	}


	public List<UserDetails> allUsers() {
	
		List<UserDetails> userList = userRepo.findAll();
		return userList;
	}
	
	
	public UserDetails editUser(String email) {
		
		return userRepo.findByEmail(email);
	}
	
	
	public void editUserFinal(UserDetails modifiedUser) {
		
		String email = modifiedUser.getEmail(); // getting email of the edited user
		
		UserDetails oldUser = userRepo.findByEmail(email); // finding current user from DB with the email
		
		oldUser.setName(modifiedUser.getName()); // setting new data
		oldUser.setPhone(modifiedUser.getPhone());
		oldUser.setAddress(modifiedUser.getAddress());
		
		userRepo.save(oldUser); // saving the user with new data back into the DB
		
	}
	
	
	@Transactional // It is a transaction task like in dbms
	public void deleteUser(String email) {
		
		userRepo.deleteByEmail(email);
		
	}


	public List<Contacts> allContacts() {
		
		List<Contacts> contactList = contactRepo.findAll();
		return contactList;
	}
	
	@Transactional // It is a transaction task like in dbms
	public void deleteContact(int contactId) {
	
		contactRepo.deleteByContactId(contactId);
		
	}


	public List<Employee> allEmployees() {
		
		List<Employee> employeeList = employeeRepo.findAll();
		return employeeList;
	}
	
	
	@Transactional // It is a transaction task like in dbms
	public void deleteEmployee(String employee_id) {
		
		employeeRepo.deleteByEmployeeId(employee_id);
		
	}


	public Admin adminLogin(String email, String pass) {
		
		Admin admin = adminRepo.findByEmailAndPassword(email, pass); // this method will either return the object, or null, but it will be checked by
		//                                                              controller
		
		return admin;
		
	}


	public List<AdminNotice> allNotices() {
		
		List<AdminNotice> noticeList = adminNoticeRepo.findAll();
		return noticeList;
	}


	public int deleteNotice(int noticeId) {
		
		String deleteQuery="delete from admin_notice where notice_id=?";
		
		int status=template.update(deleteQuery, noticeId);
		
		if(!(status > 0))
		    return status;
		
		else return 1;
		
	}


	public Employee editEmployee(String employee_id) {
		
		return employeeRepo.findByEmployeeId(employee_id);
	}


	public void editEmployeeFinal(Employee modifiedEmployee) {
		
        String id = modifiedEmployee.getEmployeeId(); // getting id of the edited employee
		
		Employee oldEmployee = employeeRepo.findByEmployeeId(id); // finding current employee from DB with the id
		
		oldEmployee.setExperience(modifiedEmployee.getExperience()); // setting new data
		oldEmployee.setPhone(modifiedEmployee.getPhone());
		oldEmployee.setQualification(modifiedEmployee.getQualification());
		
		employeeRepo.save(oldEmployee); // saving the user with new data back into the DB
		
	}


	public List<Booking> getAllBookings() {
	    return bookingRepository.findAll();
	}
	
	
	public void approveBooking(Integer id) {
	    Booking booking = bookingRepository.findById(id).orElse(null);
	    if (booking != null) {
	        booking.setStatus("approved");
	        bookingRepository.save(booking);
	    }
	}

	
	public void rejectBooking(Integer id) {
	    Booking booking = bookingRepository.findById(id).orElse(null);
	    if (booking != null) {
	        booking.setStatus("rejected");
	        bookingRepository.save(booking);
	    }
	}

}
