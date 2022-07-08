package com.emrekaraman.user.business.concretes;

import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.*;
import com.emrekaraman.user.dao.UserDao;
import com.emrekaraman.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private final UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Result save(UserDto userDto) {
        try {
            User user = dtoToModel(userDto);
            userDao.save(user);
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
            return new SuccessDataResult(userDao.findById(id),Messages.SUCCESS);
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

    public UserDto modelToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setFullname(user.getFullname());
        userDto.setId(user.getId());
        return userDto;
    }

    public User dtoToModel(UserDto userDto){
        User user = new User();
        user.setFullname(userDto.getFullname());
        user.setId(userDto.getId());
        return user;
    }
}
