package org.example.commercebank.repository;

import org.example.commercebank.domain.IpEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpEntryRepository extends JpaRepository<IpEntry, Long> {
}
