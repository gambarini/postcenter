package com.postcenter.interfaces.rest.facade;

import com.postcenter.domain.model.user.User;
import com.postcenter.interfaces.rest.dto.NewUserDTO;
import com.postcenter.interfaces.rest.dto.UserDTO;

public class UserFacade {

	public static UserDTO toUserDTO(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.get_id());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		
		return userDTO;
	}

	public static User fromDTO(NewUserDTO newUserDTO) {
		User user = new User(newUserDTO.getName(), newUserDTO.getEmail());
		return user;
	}
}
