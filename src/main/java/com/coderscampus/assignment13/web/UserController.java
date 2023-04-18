package com.coderscampus.assignment13.web;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.Transaction;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.TransactionService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private TransactionService transService;
	@Autowired
	private AccountService acctService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {

		model.put("user", new User());

		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findByIdWithAccounts(userId);
		if (user.getAddress() == null) {
			Address address = new Address();
			address.setUser(user);
			address.setUserId(userId);
			user.setAddress(address);
		}

		model.put("users", Arrays.asList(user));
		model.put("user", user);
		model.put("address", user.getAddress());
		return "users";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser(@PathVariable Long userId, User user) {
		User oldUser = userService.findByIdWithAccounts(userId);
		user.setAccounts(oldUser.getAccounts());
		user.setAddress(user.getAddress());
		userService.saveUser(user);

		return "redirect:/users/" + user.getUserId();
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		User currentUser = userService.findById(userId);
		List<Account> accounts = currentUser.getAccounts();
		for (Account account : accounts) {
			if(account.getUsers().size() > 1) {
				account.getUsers().remove(currentUser);
				currentUser.getAccounts();
			}else {
			List<Transaction> transactions = account.getTransactions();
			for (Transaction transaction : transactions)
			transService.deleteById(transaction.getTransactionId());
			}
			acctService.delete(account);
		
		}
	
		userService.delete(userId);
		return "redirect:/users";
	}

}
