package com.postcenter.infrastructure.persistence.mongodb.validator;

import com.postcenter.domain.model.IPersistenceValidator;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;

public class UserPersistenceValidator implements IPersistenceValidator<User> {

	private IUserRepository userRepository;
	
	public UserPersistenceValidator(IUserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public boolean isPersistenceValid(User user) {
		if ((user.get_id() != null)) return true;
		
		return (userRepository.findUserByEmail(user.getEmail()) == null);
	}

}
