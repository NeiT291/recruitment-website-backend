package org.neit.backend.service;

import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.entity.Company;
import org.neit.backend.mapper.CompanyMapper;
import org.neit.backend.repository.CompanyRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyResponse create(CompanyRequest request){
        Company company = companyMapper.toCompany(request);
        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public CompanyResponse update(Integer id, CompanyRequest request){

        Company company = companyRepository.findById(id).orElseThrow();
        companyMapper.updateCompany(company, request);
        return companyMapper.toCompanyResponse(companyRepository.save(company));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer id){
        companyRepository.deleteById(id);
    }
    public List<CompanyResponse> getByName(String request){
        String[] words = request.split(" ");
        request = String.join(" ", words);
        return companyRepository.findByNameIgnoreCaseContaining(request).stream().map(companyMapper::toCompanyResponse).toList();
    }
    public List<CompanyResponse> getAll(){
        return companyRepository.findAll().stream().map(companyMapper::toCompanyResponse).toList();
    }
}
