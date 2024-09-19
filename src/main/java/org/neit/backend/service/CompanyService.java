package org.neit.backend.service;

import org.neit.backend.dto.request.CompanyRequest;
import org.neit.backend.dto.response.CompanyResponse;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.entity.Company;
import org.neit.backend.mapper.CompanyMapper;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final ResultPaginationMapper resultPaginationMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper, ResultPaginationMapper resultPaginationMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.resultPaginationMapper = resultPaginationMapper;
    }
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResultPaginationResponse getByName(String request, Optional<String> page, Optional<String> pageSize){
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);

        String[] words = request.split(" ");
        request = String.join(" ", words);

        Page<Company> companyPage = companyRepository.findByNameIgnoreCaseContaining(request, pageable);

        return resultPaginationMapper.toResultPaginationResponse(companyPage);
    }
    public ResultPaginationResponse getAll(Optional<String> page, Optional<String> pageSize){
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<Company> companyPage = companyRepository.findAll(pageable);

        return resultPaginationMapper.toResultPaginationResponse(companyPage);
    }
}
