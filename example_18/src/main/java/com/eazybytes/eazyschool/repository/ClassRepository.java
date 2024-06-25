package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.EazyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<EazyClass,Integer> {
}
