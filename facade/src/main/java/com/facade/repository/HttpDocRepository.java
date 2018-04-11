package com.facade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.facade.entity.HttpDoc;

public interface HttpDocRepository extends PagingAndSortingRepository<HttpDoc, Long> {

}
