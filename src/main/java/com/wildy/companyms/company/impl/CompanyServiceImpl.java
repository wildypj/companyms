package com.wildy.companyms.company.impl;

import com.wildy.companyms.company.Company;
import com.wildy.companyms.company.CompanyRepository;
import com.wildy.companyms.company.CompanyService;
import com.wildy.companyms.company.clients.ReviewClient;
import com.wildy.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    //inject repository
    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean updateCompany(Company company, Long id) {
        //check if job exist
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isEmpty()){
            return false;
        }
        Company companyToUpdate = companyOptional.get();

        companyToUpdate.setName(company.getName());
        companyToUpdate.setDescription(company.getDescription());
//        companyToUpdate.setJobs(company.getJobs());
        companyRepository.save(companyToUpdate);
        return true;
    }

    @Override
    public void createCompany(Company company) {
        //check if an existing company exists by name something to add later
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {

        //check if company exists
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
       return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        //make a call to review service and pass in companyid
        System.out.println(reviewMessage.getDescription());
        //first get company from database && get average rating against that company from the review service
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() ->new NotFoundException("Company not found" + reviewMessage.getCompanyId()));

        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        //update average rating against company
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
