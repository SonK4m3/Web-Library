package controllers;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import daos.UserDAO;
import models.User;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin
public class AuthController {
	
	UserDAO userDAO = new UserDAO();
	
	@RequestMapping("/test")
	public String test() {
		return "hello";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(HttpEntity<String> httpEntity) throws IOException {
	    String body = httpEntity.getBody();

	    User registerUser = new GsonBuilder().setLenient().create().fromJson(body, User.class);
	    
	    System.out.println(registerUser.toString() + " is login.");
	    User existingUser = userDAO.findUserByEmail(registerUser.getEmail());
	    if(existingUser != null) {
	    	User loginUser = userDAO.loginUser(registerUser.getEmail(), registerUser.getPassword());
	    	if(loginUser == null) {
		    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\":\"Password is not correct!\"}");
	    	}
	    	else if(loginUser.getRoleName() == User.UNKNOWN) {
		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Something is wrong, please re-login!\"}");
	    	}
	    	return ResponseEntity.ok("{\"message\":\"Login Successfully!\", \"user\":" + new Gson().toJson(loginUser) + "}");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Email is not existed!\"}");
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(HttpEntity<String> httpEntity) throws IOException {
	    String body = httpEntity.getBody();
	    	    
	    User registerUser = new GsonBuilder().setLenient().create().fromJson(body, User.class);
	    
	    System.out.println(registerUser.toString() + " is register");
	    
	    User existingUser = userDAO.findUserByEmail(registerUser.getEmail());
	    if(existingUser == null) {
	    	int registerUserId = userDAO.registerUser(registerUser.getEmail(), registerUser.getPassword(), registerUser.getFullname());
	    	if(registerUserId == -1) {
		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\":\"Something is wrong, please re-register!\"}");
	    	} 
	    	registerUser.setPassword("");
	    	registerUser.setId(registerUserId);
	    	registerUser.setRoleName("user");
	    	return ResponseEntity.ok("{\"message\":\"Register Successfully!\", \"user\":"+ new Gson().toJson(registerUser) + "}");
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"Email is existed!\"}");
	}
	
	@GetMapping("/user-login")
	public ResponseEntity<?> getCurrentUser(HttpEntity<String> httpEntity) {
	    String body = httpEntity.getBody();

	    JsonObject jObject = new Gson().fromJson(body, JsonObject.class);
		
		int id = jObject.get("id").getAsInt();
	    
		boolean isLogin = userDAO.isLogin(id);
		
		return ResponseEntity.ok().body("{\"isLogin\":\"" + isLogin + "\"}");
	}
	
}
