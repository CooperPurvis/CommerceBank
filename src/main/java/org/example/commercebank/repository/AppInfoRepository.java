package org.example.commercebank.repository;

import org.example.commercebank.domain.AppInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoRepository extends JpaRepository<AppInfo, Long> {
}
