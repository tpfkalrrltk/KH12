package com.kh.spring21.dao;

import java.util.List;

import com.kh.spring21.dto.ProductDto;

public interface ProductDao {
	List<ProductDto> selectList();
	ProductDto selectOne(int productNo);
}
