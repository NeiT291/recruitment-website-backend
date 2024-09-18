package org.neit.backend.mapper;

import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        company.setAddress(request.getAddress());
        company.setDescription(request.getDescription());
        company.setWebsiteUrl(request.getWebsiteUrl());
        return company;
    }
    public CompanyResponse toCompanyResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(company.getId());
        companyResponse.setName(company.getName());
        companyResponse.setAddress(company.getAddress());
        companyResponse.setDescription(company.getDescription());
        companyResponse.setWebsiteUrl(company.getWebsiteUrl());
        return companyResponse;
    }
    public void updateCompany(Company oldCompany, CompanyRequest request) {
        oldCompany.setName(request.getName());
        oldCompany.setAddress(request.getAddress());
        oldCompany.setDescription(request.getDescription());
        oldCompany.setWebsiteUrl(request.getWebsiteUrl());
    }
}
