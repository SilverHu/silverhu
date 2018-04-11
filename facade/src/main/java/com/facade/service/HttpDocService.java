package com.facade.service;


import com.facade.entity.HttpDoc;

public interface HttpDocService {

	public HttpDoc save(HttpDoc entity);
	
	public HttpDoc getById(Long id);
	
	public Iterable<HttpDoc> findAll();
	
}
