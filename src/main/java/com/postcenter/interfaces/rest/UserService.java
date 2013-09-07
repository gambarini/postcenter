package com.postcenter.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.postcenter.domain.model.authentication.Authentication;
import com.postcenter.domain.model.authentication.IAuthenticationRepository;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;
import com.postcenter.infrastructure.persistence.mongodb.validator.UserPersistenceValidator;
import com.postcenter.interfaces.rest.dto.NewUserDTO;
import com.postcenter.interfaces.rest.facade.UserFacade;
import com.postcenter.interfaces.rest.interceptors.Authenticate;

@Path("/user")
public class UserService {

	@Inject
	private IUserRepository userRepository;

	@Inject
	private IAuthenticationRepository authRepository;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("userId") String id) {

		User user = userRepository.findUserById(id);

		return Response.status(Status.OK).entity(UserFacade.toUserDTO(user)).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Authenticate
	public Response getUserByFilter(@CookieParam("email") String email){
		
		User user = userRepository.findUserByEmail(email);
		
		if (user == null) return Response.status(Status.NOT_FOUND).build();
		
		return Response.status(Status.OK).entity(UserFacade.toUserDTO(user)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(NewUserDTO newUserDTO) {

		User user = UserFacade.fromDTO(newUserDTO);

		if (!user.isValid(new UserPersistenceValidator(userRepository)))
			return Response.status(Status.BAD_REQUEST).build();

		userRepository.store(user);

		Authentication authentication = Authentication.createAuthentication(user.get_id(), newUserDTO.getPassword());

		if (!authentication.isValid(null)) {
			userRepository.remove(user);
			return Response.status(Status.BAD_REQUEST).build();
		}

		authRepository.store(authentication);

		return Response.status(Status.CREATED).entity(user).build();
	}

}
