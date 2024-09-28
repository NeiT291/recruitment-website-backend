package org.neit.backend.service;

import org.neit.backend.dto.request.JobRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.dto.response.ResultPaginationResponse;
import org.neit.backend.entity.City;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.Job;
import org.neit.backend.entity.User;
import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.neit.backend.mapper.JobMapper;
import org.neit.backend.mapper.ResultPaginationMapper;
import org.neit.backend.repository.CityRepository;
import org.neit.backend.repository.CompanyRepository;
import org.neit.backend.repository.JobRepository;
import org.neit.backend.repository.UserRepository;
import org.neit.backend.utils.TokenInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JobService {
    public final JobRepository jobRepository;
    public final JobMapper jobMapper;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final ResultPaginationMapper resultPaginationMapper;
    private final TokenInfo tokenInfo;


    public JobService(JobRepository jobRepository, JobMapper jobMapper, CompanyRepository companyRepository, CityRepository cityRepository, UserRepository userRepository, ResultPaginationMapper resultPaginationMapper, TokenInfo tokenInfo) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.resultPaginationMapper = resultPaginationMapper;
        this.tokenInfo = tokenInfo;
    }

    public JobResponse create(JobRequest request) {
        Job job = jobMapper.toJob(request);

        if(job.getDeadline().isBefore(LocalDate.now())){
            throw new AppException(ErrorCode.DEADLINE_EXPIRED);
        }

        job.setCompany(companyRepository.findByName(request.getCompany()).orElseThrow());
        Set<City> cities = new HashSet<>();
        request.getCities().forEach(city -> {
            cities.add(cityRepository.findByName(city).orElseThrow(() ->
                    new AppException(ErrorCode.CITY_NOT_FOUND)));
        });
        job.setCities(cities);
        System.out.println(tokenInfo.getUsername());
        job.setUser(userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
        return jobMapper.toJobResponse(jobRepository.save(job));
    }
    public JobResponse update(Integer id, JobRequest request) {
        Job job = jobRepository.findById(id).orElseThrow();
        jobMapper.updateJob(job, request);

        if(job.getDeadline().isBefore(LocalDate.now())){
            throw new RuntimeException();
        }
        Set<City> cities = new HashSet<>();
        request.getCities().forEach(city -> {
            cities.add(cityRepository.findByName(city).orElseThrow(() ->
                    new AppException(ErrorCode.CITY_NOT_FOUND)));
        });
        job.setCities(cities);
        job.setUser(userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow());
        return jobMapper.toJobResponse(jobRepository.save(job));
    }
    public void delete(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.JOB_NOT_FOUND)
        );

        job.setActive(false);
        jobRepository.save(job);
    }

    public ResultPaginationResponse getAllByUser(Optional<String> page, Optional<String> pageSize){
        User user = userRepository.findByUsername(tokenInfo.getUsername()).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND)
        );
        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);
        Page<Job> jobPage = jobRepository.findByUser(user, pageable);
        return resultPaginationMapper.toResultPaginationResponse(jobPage.map(jobMapper::toJobResponse));
    }

    public ResultPaginationResponse getAll(String name,
                                           String city,
                                           String company,
                                           Integer min_wage,
                                           Integer max_wage,
                                           Integer wage,
                                           Optional<String> page,
                                           Optional<String> pageSize){

        List<Job> jobs = jobRepository.findAll();
        if(name != null){
            String finalName = String.join(" ", name.split(" "));
            jobs.removeIf(job ->
               !job.getName().toLowerCase().contains(finalName.toLowerCase())
            );
        }
        if(city != null){
            String finalCity = String.join(" ", city.split(" "));
            jobs.removeIf(job ->
                    !job.getCities().contains(cityRepository.findByName(finalCity).orElseThrow(() -> new AppException(ErrorCode.CITY_NOT_FOUND)))
            );
        }
        if(company != null){
            String finalCompany = String.join(" ", company.split(" "));
            jobs.removeIf(job ->
                    !job.getCompany().getName().toLowerCase().contains(finalCompany.toLowerCase())
            );
        }
        if(wage != null){
            jobs.removeIf(job ->
                    job.getWage() != wage
            );
        }
        if(min_wage != null){
            jobs.removeIf(job ->
                    job.getMin_wage() < min_wage
            );
        }
        if(max_wage != null){
            jobs.removeIf(job ->
                    job.getMax_wage() > max_wage
            );
        }


        Pageable pageable = resultPaginationMapper.toPageAble(page, pageSize);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), jobs.size());
        final Page<Job> jobPage = new PageImpl<>(jobs.subList(start, end), pageable, jobs.size());

        return resultPaginationMapper.toResultPaginationResponse(jobPage.map(jobMapper::toJobResponse));
    }
}
