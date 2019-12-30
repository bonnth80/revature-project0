package com.userBO;

import java.util.List;

import com.bank.exception.BusinessException;
import com.bank.to.User;

public interface UserBO {
	User getUserByCredentials(String username, String password) throws BusinessException;
	User getUserById(int id);
	int getMaxIdUsed() throws BusinessException;
	List<User> getUsersByFullName(String firstName, String lastName);
	List<User> getUsersByArchetype(int archetype);
	User getUserBySSN(String ssn);
	List<User> getUsersByHomePhone(String phoneNumber);
	List<User> getUsersByMobilePhone(String phoneNumber);
	List<User> getUsersByEmail(String email);
	List<User> getUsersByCityState(String city, String state);
	List<User> getUsersByState(String city);
	List<User> getUsersByCountry(String country);
	List<User> getUsersByZip(String zip);
	User getUserByUsername(String username);
	List<User> getUsersByDateCreated();
	
	boolean addNewUser(User user) throws BusinessException;
}
