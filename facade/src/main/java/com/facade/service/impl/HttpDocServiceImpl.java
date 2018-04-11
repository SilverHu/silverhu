package com.facade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facade.entity.HttpDoc;
import com.facade.repository.HttpDocRepository;
import com.facade.service.HttpDocService;

@Service
@Transactional
public class HttpDocServiceImpl implements HttpDocService {

	@Autowired
	private HttpDocRepository httpDocRepository;

	@Override
	public HttpDoc save(HttpDoc entity) {
		return httpDocRepository.save(entity);
	}

	@Override
	public HttpDoc getById(Long id) {
		return httpDocRepository.findOne(id);
	}

	@Override
	public Iterable<HttpDoc> findAll() {
		return httpDocRepository.findAll();
	}

}
