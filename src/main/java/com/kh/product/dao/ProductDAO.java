package com.kh.product.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDAO {



  Long save(Product product);

  int update(Long pid,Product product);

  int delete(Long pid);

  Optional<Product> findById(Long pid);

  List<Product> findAll();

}
