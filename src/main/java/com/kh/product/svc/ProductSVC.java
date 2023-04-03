package com.kh.product.svc;

import com.kh.product.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSVC {

  Long save(Product product);

  int update(Long pid,Product product);

  int delete(Long pid);

  int deleteParts(List<Long> pids);

  Optional<Product> findById(Long pid);

  List<Product> findAll();

  boolean isExist(Long pid);
}
