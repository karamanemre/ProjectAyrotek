package com.emrekaraman.user.auth.userAuth;

import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.core.utilities.DataResult;

public interface UserAuthService {
    DataResult<LoginResponseDto> login(LoginRequestDto loginRequestDto);
}
