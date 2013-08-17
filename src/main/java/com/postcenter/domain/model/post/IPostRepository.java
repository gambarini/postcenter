package com.postcenter.domain.model.post;

import java.util.Collection;

public interface IPostRepository {

	public abstract void store(Post post);

	public abstract void remove(Post post);

	public abstract void removeAll();

	public abstract Post findPostById(String id);

	public abstract Collection<Post> findTopPosts(int topQuantity);

	public abstract long findPostsTotal();

}
