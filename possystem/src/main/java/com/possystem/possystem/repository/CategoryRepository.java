package com.possystem.possystem.repository;

import com.possystem.possystem.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> , JpaSpecificationExecutor<Category> {
    Optional<Category> findByName(String name);
}
