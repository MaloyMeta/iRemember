package com.maloy.iremember.repositories.user;

import com.maloy.iremember.entity.user.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    boolean existsByCompanyName(String companyName);
}
