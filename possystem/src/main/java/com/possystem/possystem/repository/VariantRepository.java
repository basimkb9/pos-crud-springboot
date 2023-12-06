package com.possystem.possystem.repository;

import com.possystem.possystem.domain.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant,Long> , JpaSpecificationExecutor<Variant> {
    Optional<Variant> findByName(String name);

    Optional<Variant> findByNameAndProductId(String name, Long productId);

}
