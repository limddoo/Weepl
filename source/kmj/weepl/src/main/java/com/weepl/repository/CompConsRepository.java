package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weepl.entity.CompCons;

@Repository
public interface CompConsRepository extends JpaRepository<CompCons, Long>{

}
