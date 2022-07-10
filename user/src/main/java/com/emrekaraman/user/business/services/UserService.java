package com.emrekaraman.user.business.services;

import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.core.utilities.DataResult;
import com.emrekaraman.user.core.utilities.Result;

public interface UserService {

    Result save(UserDto userDto);
    Result delete(Long id);
    Result update(UserDto productDto);
    DataResult<UserDto> getById(Long id);
    DataResult<UserDto> getAll();

}
