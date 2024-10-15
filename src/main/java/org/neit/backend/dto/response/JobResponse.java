package org.neit.backend.dto.response;

import org.neit.backend.entity.City;
import org.neit.backend.entity.Company;
import org.neit.backend.entity.Profession;

import java.time.LocalDate;
import java.util.Set;

public class JobResponse {
    private Integer id;
    private String name;
    private String description;
    private String address;
    private int experience;

    private float min_wage;
    private float max_wage;
    private float wage;
    private String profession;
    private LocalDate deadline;

    private String company;

    private String city;

    private String user_created;
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getUser_created() {
        return user_created;
    }

    public void setUser_created(String user_created) {
        this.user_created = user_created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getMin_wage() {
        return min_wage;
    }

    public void setMin_wage(float min_wage) {
        this.min_wage = min_wage;
    }

    public float getMax_wage() {
        return max_wage;
    }

    public void setMax_wage(float max_wage) {
        this.max_wage = max_wage;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
