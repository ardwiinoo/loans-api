package com.ardwiinoo.loansapi.mapper;

import com.ardwiinoo.loansapi.model.dto.user.UserDto;
import com.ardwiinoo.loansapi.model.dto.user.UserLoanDto;
import com.ardwiinoo.loansapi.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    UserDto toUserDto(User user);
    UserLoanDto toUserLoanDto(User user);
}
