package org.neit.backend.mapper;

import org.neit.backend.dto.response.ResultPaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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
}
