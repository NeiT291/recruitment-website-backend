package org.neit.backend.mapper;

import org.neit.backend.dto.response.ResultPaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResultPaginationMapper {
    public ResultPaginationResponse toResultPaginationResponse(Page<?> resultPage) {
        ResultPaginationResponse response = new ResultPaginationResponse();
        response.setTotal_records(resultPage.getTotalElements());
        response.setTotal_records_page(resultPage.getNumberOfElements());
        response.setCurrent_page(resultPage.getNumber());
        response.setTotal_pages(resultPage.getTotalPages());
        response.setData(resultPage.getContent());
        return response;
    }
    public Pageable toPageAble(Optional<String> page, Optional<String> pageSize){
        String sPage = page.orElse("1");
        String sPageSize = pageSize.orElse("10");

        return PageRequest.of(Integer.parseInt(sPage) - 1, Integer.parseInt(sPageSize));
    }
}
