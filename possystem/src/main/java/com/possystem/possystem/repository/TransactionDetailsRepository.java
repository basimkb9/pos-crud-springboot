package com.possystem.possystem.repository;
import com.possystem.possystem.domain.Product;
import com.possystem.possystem.domain.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails,Long>, JpaSpecificationExecutor<TransactionDetails> {

}
