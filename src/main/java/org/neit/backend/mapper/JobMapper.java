package org.neit.backend.mapper;

import org.neit.backend.dto.request.JobRequest;
import org.neit.backend.dto.response.JobResponse;
import org.neit.backend.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public Job toJob(JobRequest request){
        Job job = new Job();
        job.setName(request.getName());
        job.setDescription(request.getDescription());
        job.setAddress(request.getAddress());
        job.setExperience(request.getExperience());
        job.setMin_wage(request.getMin_wage());
        job.setMax_wage(request.getMax_wage());
        job.setWage(request.getWage());
        job.setDeadline(request.getDeadline());
        return job;
    }
    public Job updateJob(Job job,JobRequest request){
        job.setName(request.getName());
        job.setDescription(request.getDescription());
        job.setAddress(request.getAddress());
        job.setExperience(request.getExperience());
        job.setMin_wage(request.getMin_wage());
        job.setMax_wage(request.getMax_wage());
        job.setWage(request.getWage());
        job.setDeadline(request.getDeadline());
        return job;
    }
    public JobResponse toJobResponse(Job job){
        JobResponse jobResponse = new JobResponse();
        jobResponse.setId(job.getId());
        jobResponse.setName(job.getName());
        jobResponse.setDescription(job.getDescription());
        jobResponse.setAddress(job.getAddress());
        jobResponse.setExperience(job.getExperience());
        jobResponse.setMin_wage(job.getMin_wage());
        jobResponse.setMax_wage(job.getMax_wage());
        jobResponse.setWage(job.getWage());
        jobResponse.setDeadline(job.getDeadline());
        jobResponse.setCompany(job.getCompany());
        jobResponse.setCities(job.getCities());
        jobResponse.setUser_created(job.getUser().getUsername());
        jobResponse.setActive(job.isActive());
        return jobResponse;
    }
}
