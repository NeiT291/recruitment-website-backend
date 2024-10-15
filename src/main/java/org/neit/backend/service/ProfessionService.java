package org.neit.backend.service;

import org.neit.backend.dto.request.ProfessionRequest;
import org.neit.backend.dto.response.ProfessionResponse;
import org.neit.backend.entity.Profession;
import org.neit.backend.exception.AppException;
import org.neit.backend.exception.ErrorCode;
import org.neit.backend.mapper.ProfessionMapper;
import org.neit.backend.repository.ProfessionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionService {
    private final ProfessionMapper professionMapper;
    private final ProfessionRepository professionRepository;

    public ProfessionService(ProfessionMapper professionMapper, ProfessionRepository professionRepository) {
        this.professionMapper = professionMapper;
        this.professionRepository = professionRepository;
    }

    public ProfessionResponse create(ProfessionRequest request){
        Profession profession = new Profession();
        profession.setName(request.getName());
        return professionMapper.toProfessionResponse(professionRepository.save(profession));
    }
    public List<ProfessionResponse> getAll(String name){
        if(name == null){
            return professionRepository.findAll().stream().map(professionMapper::toProfessionResponse).toList();
        }
        return  professionRepository.findByNameContainingIgnoreCase(name).stream().map(professionMapper::toProfessionResponse).toList();
    }
}
