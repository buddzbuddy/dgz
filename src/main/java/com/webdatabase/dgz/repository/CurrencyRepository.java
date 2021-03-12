package com.webdatabase.dgz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webdatabase.dgz.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
