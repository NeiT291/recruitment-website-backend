package org.neit.backend.mapper;

import org.neit.backend.dto.response.ProfessionResponse;
import org.neit.backend.entity.Profession;
import org.springframework.stereotype.Component;

@Component
public class ProfessionMapper {
    public ProfessionResponse toProfessionResponse(Profession profession) {
        ProfessionResponse response = new ProfessionResponse();
        response.setId(profession.getId());
        response.setName(profession.getName());
        return response;
    }
}
