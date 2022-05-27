package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserProfile;

@RestController
public class RestResource {
	@GetMapping("/api/users/me")
	public ResponseEntity<UserProfile> profile()
	{
		User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email=user.getUsername() + "@poineeritconsulting.com";
		UserProfile profile=new UserProfile();
		profile.setName(user.getUsername());
		profile.setEmail(email);
		if(user.isEnabled())
		{
			profile.setStatus("ACTIVE");
		}else {
			profile.setStatus("INACTIVE");
		}
		return ResponseEntity.ok(profile);
	}

}
