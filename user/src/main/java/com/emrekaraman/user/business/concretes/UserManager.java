package com.emrekaraman.user.business.concretes;

import com.emrekaraman.user.auth.TokenManager;
import com.emrekaraman.user.auth.WebSecurityConfiguration;
import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.*;
import com.emrekaraman.user.dao.UserDao;
import com.emrekaraman.user.entity.Seller;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final TokenManager tokenManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManager(UserDao userDao, ModelMapper modelMapper, TokenManager tokenManager, BCryptPasswordEncoder bCryptPasswordEncoder, WebSecurityConfiguration webSecurityConfiguration) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.tokenManager = tokenManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public Result save(UserDto userDto) {
        try {
            Seller seller = modelMapper.map(userDto,Seller.class);
            seller.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userDao.save(seller);
            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorResult(err.getMessage());
        }
    }

    @Override
    public Result delete(Long id) {
        try {

            if (!userDao.existsById(id)){return new ErrorResult(Messages.NOT_FOUND);}

            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorResult(err.getMessage());
        }
    }

    @Override
    public Result update(UserDto productDto) {

        if (!userDao.existsById(productDto.getId())){return new ErrorResult(Messages.NOT_FOUND);}

        try {
            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorResult(err.getMessage());
        }
    }

    @Override
    public DataResult<UserDto> getById(Long id) {

        if (!userDao.existsById(id)){return new ErrorDataResult(Messages.NOT_FOUND);}

        try {
            return new SuccessDataResult(modelToDto(userDao.findById(id).get()),Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorDataResult(err.getMessage());
        }
    }

    @Override
    public DataResult<UserDto> getAll() {
        try {
            return new SuccessDataResult(userDao.findAll(),Messages.SUCCESS);
        }catch (Exception err){
            return new ErrorDataResult(err.getMessage());
        }
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

            LoginResponseDto loginResponseDto = modelMapper.map(loginRequestDto,LoginResponseDto.class);
            loginResponseDto.setUserId(userDao.findByEmail(loginRequestDto.getEmail()).getId());
            loginResponseDto.setToken(tokenManager.generateToken(loginRequestDto.getEmail()));
            return new SuccessDataResult(loginResponseDto,Messages.SUCCESS);
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


    public UserDto modelToDto(Seller seller){
        UserDto userDto = new UserDto();
        userDto.setFullname(seller.getFullname());
        userDto.setId(seller.getId());
        userDto.setEmail(seller.getEmail());
        return userDto;
    }

    public Seller dtoToModel(UserDto userDto){
        Seller seller = new Seller();
        seller.setEmail(userDto.getEmail());
        seller.setFullname(userDto.getFullname());
        seller.setId(userDto.getId());
        seller.setPassword(userDto.getPassword());
        return seller;
    }
}
