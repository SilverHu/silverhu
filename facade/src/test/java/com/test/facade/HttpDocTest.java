package com.test.facade;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.facade.entity.HttpDoc;
import com.facade.service.HttpDocService;

@Transactional
public class HttpDocTest extends BaseTest {

	@Autowired
	private HttpDocService httpDocService;
	
	@Test
	public void save() {
		System.out.println("------------save------------");
		HttpDoc entity = new HttpDoc();
		entity.setName("原子接口1");
		entity.setMethod("POST");
		entity = httpDocService.save(entity);
		System.out.println(entity);
		System.out.println("------------find all------------");
		Iterable<HttpDoc> list = httpDocService.findAll();
		list.forEach(obj -> {
			System.out.println(obj);
		});
	}
}
