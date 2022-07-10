package com.emrekaraman.user.auth.userAuth;


import com.emrekaraman.user.auth.TokenManager;
import com.emrekaraman.user.business.concretes.BusinessRules;
import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.*;
import com.emrekaraman.user.dao.UserDao;
import com.emrekaraman.user.entity.Seller;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthManager implements UserAuthService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    public UserAuthManager(UserDao userDao, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, TokenManager tokenManager, AuthenticationManager authenticationManager) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenManager = tokenManager;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public DataResult<LoginResponseDto> login(LoginRequestDto loginRequestDto) {
        try {

            Result result = BusinessRules.run(
                    checkExistsByUsername(loginRequestDto.getEmail())
                    ,checkPasswordIsCorrect(loginRequestDto.getEmail(),loginRequestDto.getPassword())
            );

            if (result!=null){
                return new ErrorDataResult(result.getMessage());
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),loginRequestDto.getPassword()));
            LoginResponseDto loginResponseDto = modelMapper.map(loginRequestDto,LoginResponseDto.class);
            loginResponseDto.setUserId(userDao.findByEmail(loginRequestDto.getEmail()).getId());
            loginResponseDto.setToken(tokenManager.generateToken(loginRequestDto.getEmail()));
            return new SuccessDataResult(loginResponseDto, Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorDataResult(err, Messages.FAILED);
        }
    }

    private Result checkExistsByUsername(String email) {
        if (userDao.existsByEmail(email)==false) {
            return new ErrorResult(Messages.UNAUTHORIZE);
        }
        return new SuccessResult();
    }

    private Result checkPasswordIsCorrect(String email, String password) {
        Seller seller = userDao.findByEmail(email);
        bCryptPasswordEncoder.encode(password);
        if (seller!=null && !bCryptPasswordEncoder.matches(password,seller.getPassword())) {
            return new ErrorResult(Messages.UNAUTHORIZE);
        }
        return new SuccessResult();
    }


}
