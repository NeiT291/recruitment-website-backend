package org.neit.backend.dto.request;

import org.neit.backend.entity.Company;
import org.neit.backend.entity.User;

import java.time.LocalDate;

public class ResumeRequest {

    private String company;


    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
