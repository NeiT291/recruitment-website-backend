package org.neit.backend.mapper;

import org.neit.backend.dto.response.CityResponse;
import org.neit.backend.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityResponse toCityResponse(City city) {
        CityResponse cityResponse = new CityResponse();
        cityResponse.setName(city.getName());
        return cityResponse;
    }
}
