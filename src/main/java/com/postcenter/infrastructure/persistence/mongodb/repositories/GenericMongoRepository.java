package com.postcenter.infrastructure.persistence.mongodb.repositories;

import java.net.UnknownHostException;

import org.jongo.Jongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public abstract class GenericMongoRepository {

	protected MongoClient cliente;
	protected DB database;
	protected Jongo jongo;

	protected GenericMongoRepository() {
		this("localhost", "postcenter");
	}
	
	protected GenericMongoRepository(String hostUri, String dbName) {
		this.configureDatabase(hostUri, dbName);
		loadCollections();
	}

	protected abstract void loadCollections();
	
	protected String createFilter(String fieldName, Object value) {
		return "{'" + fieldName + "' : '" + value.toString() + "'}";
	}
	
	protected String createFilter(String fieldName1, Object value1, String fieldName2, Object value2) {
		return "{'" + fieldName1 + "' : '" + value1.toString() + "' , '" + fieldName2 + "' : '" + value2.toString() + "'}";
	}

	
	protected String createSort(String fieldName, boolean asc){
		return "{" + fieldName + " : " + (asc ? "1" : "-1") + "}";
	}

	public void configureDatabase(String hostUri, String dbName) {
		try {

			cliente = new MongoClient(hostUri);
			database = cliente.getDB(dbName);
			jongo = new Jongo(database);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
