package org.neit.backend.service;

import org.neit.backend.dto.request.JobCreateRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.entity.City;
import org.neit.backend.entity.Job;
import org.neit.backend.mapper.JobMapper;
import org.neit.backend.repository.CityRepository;
import org.neit.backend.repository.CompanyRepository;
import org.neit.backend.repository.JobRepository;
import org.neit.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JobService {
    public final JobRepository jobRepository;
    public final JobMapper jobMapper;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;


    public JobService(JobRepository jobRepository, JobMapper jobMapper, CompanyRepository companyRepository, CityRepository cityRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.companyRepository = companyRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
    }

    public JobResponse create(JobCreateRequest request) {
        Job job = jobMapper.toJob(request);
        job.setCompany(companyRepository.findByName(request.getCompany()).orElseThrow());
        Set<City> cities = new HashSet<>();
        request.getCities().forEach(city -> {
            cities.add(cityRepository.findByName(city));
        });
        job.setCities(cities);
        job.setUser(userRepository.findByUsername(request.getUser()).orElseThrow());
        return jobMapper.toJobResponse(jobRepository.save(job));
    }
}
