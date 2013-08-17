package com.postcenter.interfaces.rest.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.postcenter.domain.model.post.Post;
import com.postcenter.domain.model.post.ReplyMessage;
import com.postcenter.domain.model.user.IUserRepository;
import com.postcenter.domain.model.user.User;
import com.postcenter.interfaces.rest.dto.PostDTO;
import com.postcenter.interfaces.rest.dto.ReplyDTO;

public class PostFacade {

	public static List<PostDTO> toPostDTO(Collection<Post> posts, IUserRepository userRepository) {
		List<PostDTO> postsDTO = new ArrayList<PostDTO>();

		for (Post post : posts) {
			PostDTO postDTO = toPostDTO(post, userRepository);

			postsDTO.add(postDTO);

		}
		return postsDTO;
	}

	public static PostDTO toPostDTO(Post post, IUserRepository userRepository) {
		PostDTO postDTO = new PostDTO();

		postDTO.setId(post.get_id());
		postDTO.setTitle(post.getTitle());
		postDTO.setMessage(post.getMessage().getText());
		postDTO.setCreateDate(post.getMessage().getDate());
		postDTO.setTotalReplys(post.getReplys().size());

		for (ReplyMessage reply : post.getReplys()) {
			ReplyDTO replyDTO = new ReplyDTO();

			replyDTO.setId(reply.get_id());
			replyDTO.setMessage(reply.getText());
			replyDTO.setCreateDate(reply.getDate());

			User userReply = userRepository.findUserById(reply.getUserId());
			replyDTO.setUserName(userReply.getName());

			postDTO.getReplys().add(replyDTO);
		}

		User user = userRepository.findUserById(post.getUserId());
		postDTO.setUserId(user.get_id());
		postDTO.setUserName(user.getName());
		return postDTO;
	}

}
