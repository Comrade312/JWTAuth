package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
	private static final Map<String, User> empMap = new HashMap<>();

	static {
		User emp1 = new User("E01", "Smith", new BCryptPasswordEncoder().encode("123"));
		User emp2 = new User("E02", "Allen", new BCryptPasswordEncoder().encode("123"));
		User emp3 = new User("E03", "Jones", new BCryptPasswordEncoder().encode("123"));

		empMap.put(emp1.getUsername(), emp1);
		empMap.put(emp2.getUsername(), emp2);
		empMap.put(emp3.getUsername(), emp3);
	}

	public Optional<User> findByUsername(String username) {
		return Optional.ofNullable(empMap.get(username));
	}

	public Boolean existsByUsername(String username) {
		return empMap.get(username) != null;
	}

	public User save(User emp) {
		empMap.put(emp.getUsername(), emp);
		return emp;
	}

}
