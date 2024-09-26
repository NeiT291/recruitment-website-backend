package org.neit.backend.dto.response;

import org.neit.backend.entity.Company;
import org.neit.backend.entity.User;

public class ResumeResponse {
    private User user;
    private Company company;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
