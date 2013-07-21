package com.postcenter.domain.model.post;

import java.util.Collection;




public interface IPostRepository {

	 void store(Post post);
	 void remove(Post post);
	 void removeAll();
	 Post findPostById(String id);
	 public Collection<Post> findTopPosts(int topQuantity);
	 long findPostsTotal();

}
