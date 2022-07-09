package com.emrekaraman.user.ws;

import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.DataResult;
import com.emrekaraman.user.core.utilities.ErrorDataResult;
import com.emrekaraman.user.core.utilities.Result;
import com.emrekaraman.user.core.utilities.SuccessDataResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userws")
public class UserWs {

    private final UserService userService;


    public UserWs(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok( userService.login(loginRequestDto));
    }

    @PostMapping("/save")
    public ResponseEntity<Result> save(@RequestBody UserDto userDto){
        Result result = userService.save(userDto);
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DataResult<UserDto>> getById(@PathVariable Long id){

        DataResult<UserDto> result = userService.getById(id);
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/getAll")
    public ResponseEntity<DataResult<UserDto>> getAll(){

        DataResult<UserDto> result = userService.getAll();
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
