package com.kh.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {



  Long save(Product product);

  int update(Long pid,Product product);

  int delete(Long pid);

  int deleteParts(List<Long> pids);

  int deleteAll();

  Optional<Product> findById(Long pid);

  List<Product> findAll();

  boolean isExist(Long pid);

  int countOfRecord();

}
