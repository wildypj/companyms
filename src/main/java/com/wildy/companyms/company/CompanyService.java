package com.wildy.companyms.company;

import com.wildy.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface CompanyService {
    // get all companies
    List<Company> getAllCompanies();
    //update company
    Boolean updateCompany(Company company, Long id);
    //create company
    void createCompany(Company company);
    // deleteCompany
    boolean deleteCompanyById(Long id);
    // get specific company
    Company getCompanyById(Long id);
    public void updateCompanyRating(ReviewMessage reviewMessage);
}
