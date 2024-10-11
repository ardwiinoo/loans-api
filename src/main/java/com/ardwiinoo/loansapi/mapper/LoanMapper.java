package com.ardwiinoo.loansapi.mapper;

import com.ardwiinoo.loansapi.model.dto.loan.LoanDto;
import com.ardwiinoo.loansapi.model.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanMapper {

    LoanMapper MAPPER = Mappers.getMapper(LoanMapper.class);
    LoanDto toDto(Loan loan);
}
