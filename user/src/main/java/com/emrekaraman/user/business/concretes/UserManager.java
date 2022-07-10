package com.emrekaraman.user.business.concretes;

import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.*;
import com.emrekaraman.user.dao.UserDao;
import com.emrekaraman.user.entity.Seller;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManager(UserDao userDao, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public Result save(UserDto userDto) {
        try {
            if (userDao.existsByEmail(userDto.getEmail())) {
                return new ErrorDataResult(Messages.MUST_BE_UNIQUE);
            }
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

    public UserDto modelToDto(Seller seller){
        UserDto userDto = new UserDto();
        userDto.setFullname(seller.getFullname());
        userDto.setId(seller.getId());
        userDto.setEmail(seller.getEmail());
        return userDto;
    }
}
