package com.process.ebank.repository;

import com.process.ebank.entity.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

    // methodes qui permet de retourner une liste d'operation
    List<AccountOperation> findByBankAccountId(String accountId);

    //Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
