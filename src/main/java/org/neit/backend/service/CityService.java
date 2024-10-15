package org.neit.backend.service;

import org.neit.backend.dto.response.CityResponse;
import org.neit.backend.mapper.CityMapper;
import org.neit.backend.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public List<CityResponse> getAll(){
        return cityRepository.findAll().stream().map(cityMapper::toCityResponse).toList();
    }
}
