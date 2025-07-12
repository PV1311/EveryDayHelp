package com.test.everyday.service;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.everyday.entity.Admin;
import com.test.everyday.entity.Booking;
import com.test.everyday.entity.Feedback;
import com.test.everyday.entity.UserDetails;
import com.test.everyday.repository.BookingRepository;
import com.test.everyday.repository.FeedbackRepository;
import com.test.everyday.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepo;
	private final BookingRepository bookingRepository;
	private final FeedbackRepository feedbackRepository;
	
	@Autowired
    JdbcTemplate template;
	
	private final BCryptPasswordEncoder passwordEncoder; // this won't be injected in constructor below from parameter but it's object will be created there
	//                                                      in the constructor


	public UserService(UserRepository userRepo, BookingRepository bookingRepository, FeedbackRepository feedbackRepository) {
		super();
		this.userRepo = userRepo;
		this.bookingRepository = bookingRepository;
		this.feedbackRepository = feedbackRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}




	public void addUser(UserDetails userDetails, MultipartFile file) {
		// we send the pic variable in addUser() in controller but here we receive file variable in arguments. So it is just to show that the pic was
		// not the field variable that we set in UserDetails.java entity class
		
		try {
			// 1) Get the root directory of running project:
			String projectRoot = System.getProperty("user.dir");
			
			// 2) Define folder name inside the project:
			String folderName = "pfpUploads";
			
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
			
			userDetails.setPic(DB_PATH);		
			
			// Encrypting Password:
			String encyptedPassword = passwordEncoder.encode(userDetails.getPassword());
			System.out.println("password after encryption : "+ encyptedPassword);
			
			userDetails.setPassword(encyptedPassword); // setting the encrypted password in user
			
			// Saving user to DB:
			userRepo.save(userDetails);
			
		}catch(IOException io){
			
			System.err.println("Image upload failed!");
			io.printStackTrace();
			
		}
		
	}


	public UserDetails userLogin(String email, String rawpassword) {
		
		// THROUGH JPA (IN THIS CASE, WE ACCEPT String email, String pass, AS EXPECTED):
		// UserDetails userDetails = userRepo.findByEmailAndPassword(email, pass);
		// return userDetails;
		
		// FOR PASSWORD ENCRYPTION, WE HAVE TO DO THROUGH JDBC (IN THIS CASE, INSTEAD OF String pass, WE ACCEPT String rawpassword):
		UserDetails user=null;
		String sql="select * from user_details where email = ?";
		try
		{
			 user=template.queryForObject(sql, new RowMapper<UserDetails>() {

				@Override
				public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserDetails ud=new UserDetails();
					//email, address, name, password, phone, pic
					ud.setEmail(rs.getString("email"));
					ud.setAddress(rs.getString("address"));
					ud.setName(rs.getString("name"));
					ud.setPhone(rs.getString("phone"));
					ud.setPic(rs.getString("pic"));
					ud.setPassword(rs.getString("password"));
					
					return ud;//it might be null if email and password does not match
				}
			}, email);
			 
			 //Verify password using BCrypt
			 BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			 boolean passwordMatches=encoder.matches(rawpassword, user.getPassword());
			 
			 if(passwordMatches)
			 {
				 return user;
			 }
			 else {
				 return null;
			 }
		}
		catch(EmptyResultDataAccessException erda)
		{
			erda.printStackTrace();
		}
		
		return user;

	}
	
	
	public void updateUser(String email, String name, String phone, String address, MultipartFile file) {
	    UserDetails existingUser = userRepo.findByEmail(email);

	    if (existingUser != null) {
	        existingUser.setName(name);
	        existingUser.setPhone(phone);
	        existingUser.setAddress(address);

	        if (file != null && !file.isEmpty()) {
	            try {
	                // 1) Delete old image
	                String oldPath = existingUser.getPic();
	                if (oldPath != null) {
	                    File oldFile = new File(System.getProperty("user.dir") + File.separator + oldPath);
	                    if (oldFile.exists()) oldFile.delete();
	                }

	                // 2) Upload new image
	                String projectRoot = System.getProperty("user.dir");
	                String folderName = "pfpUploads";
	                String uploadPath = projectRoot + File.separator + folderName;
	                File uploadDir = new File(uploadPath);
	                if (!uploadDir.exists()) uploadDir.mkdirs();

	                String originalFilename = file.getOriginalFilename();
	                String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;
	                File destinationFile = new File(uploadDir, uniqueFilename);
	                file.transferTo(destinationFile);

	                // 3) Save new path
	                existingUser.setPic(folderName + "/" + uniqueFilename);

	            } catch (IOException e) {
	                System.err.println("Profile picture update failed.");
	                e.printStackTrace();
	            }
	        }

	        userRepo.save(existingUser);
	    }
	}

	public UserDetails getUserByEmail(String email) {
	    return userRepo.findByEmail(email);
	}

	
	
	public void saveBooking(Booking booking) {
        booking.setStatus("pending");
        booking.setDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }
	
	
	public void addFeedback(Feedback feedback) 
	{
		Feedback f = feedbackRepository.findByEmail(feedback.getEmail());
		if (f != null) {
	        // Update the existing feedback with new values
	        f.setRemarks(feedback.getRemarks());
	        f.setRating(feedback.getRating());
	        // Add any other fields that need to be updated
	        feedbackRepository.save(f);
	    } else {
	        // No existing feedback; save new one
	        feedbackRepository.save(feedback);
	    }				
	}

}
