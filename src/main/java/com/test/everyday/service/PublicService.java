package com.test.everyday.service;

import org.springframework.stereotype.Service;

import com.test.everyday.entity.Contacts;
import com.test.everyday.repository.ContactRepository;

@Service
public class PublicService {
	
	private final ContactRepository contactRepo;

	public PublicService(ContactRepository contactRepo) {
		super();
		this.contactRepo = contactRepo;
	}

	public void addContact(Contacts contacts) {
		
		contactRepo.save(contacts);
		
//		Contacts cnt = contactRepo.save(contacts);
		
//		if(cnt != null)
//			return 1;
		
		
	}
	
	
}
