package com.postcenter.infrastructure.persistence.mongodb.repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.jongo.MongoCollection;

import com.postcenter.domain.model.post.IPostRepository;
import com.postcenter.domain.model.post.Post;
import com.postcenter.domain.model.post.ReplyMessage;
import com.postcenter.domain.model.user.User;

public class PostMongoRepository extends GenericMongoRepository implements IPostRepository {

	private MongoCollection postCollection;
	private MongoCollection userCollection;
	private MongoCollection replyCollection;

	public PostMongoRepository() {
		super();
	}

	public PostMongoRepository(String hostUri, String dbName) {
		super(hostUri, dbName);
	}

	@Override
	protected void loadCollections() {
		postCollection = jongo.getCollection(Post.class.getSimpleName());
		userCollection = jongo.getCollection(User.class.getSimpleName());
		replyCollection = jongo.getCollection(ReplyMessage.class.getSimpleName());
	}

	@Override
	public void store(Post post) {
		postCollection.save(post);
		storeReplyMessage(post);
	}

	@Override
	public void remove(Post post) {
		for (ReplyMessage reply : post.getReplys()) {
			replyCollection.remove(createFilter("_id", reply.get_id()));
		}

		postCollection.remove(createFilter("_id", post.get_id()));
	}

	@Override
	public void removeAll() {
		postCollection.remove();
		replyCollection.remove();

	}

	protected void storeReplyMessage(Post post) {
		for (ReplyMessage rMessage : post.getReplys()) {
			replyCollection.save(rMessage);
		}
	}

	@Override
	public Post findPostById(String id) {
		if (id == null)
			return null;

		Post post = postCollection.findOne(createFilter("_id", id)).as(Post.class);
		if (post != null) {
			fetchUser(post);
			fetchReplyMessage(post);
		}

		return post;
	}

	@Override
	public Collection<Post> findTopPosts(int topQuantity) {
		Iterable<Post> posts = postCollection.find().limit(topQuantity).sort(this.createSort("_id", false)).as(Post.class);
		Collection<Post> postsList = new ArrayList<Post>();
		for (Post post : posts) {
			postsList.add(post);
			post.setUser(findUser(post.getUserId()));
		}

		return postsList;
	}

	@Override
	public void fetchUser(Post post) {
		post.setUser(findUser(post.getUserId()));
	}

	protected User findUser(String userId) {
		if (userId == null)
			return null;

		return userCollection.findOne(createFilter("_id", userId)).as(User.class);
	}

	public void fetchReplyMessage(Post post) {
		Iterable<ReplyMessage> replys = replyCollection.find(this.createFilter("postId", post.get_id())).sort(this.createSort("_id", true)).as(ReplyMessage.class);

		for (ReplyMessage reply : replys) {
			reply.setUser(findUser(reply.getUserId()));
			post.reply(reply);
		}
	}
	
	@Override
	public long findPostsTotal(){
		return postCollection.count();
	}
}
