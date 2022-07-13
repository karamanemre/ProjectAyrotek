package com.emrekaraman.user.ws;

import com.emrekaraman.user.auth.userAuth.UserAuthService;
import com.emrekaraman.user.business.dtos.LoginRequestDto;
import com.emrekaraman.user.business.dtos.LoginResponseDto;
import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.constants.Messages;
import com.emrekaraman.user.core.utilities.DataResult;
import com.emrekaraman.user.core.utilities.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userws")
public class UserWs {

    private final UserService userService;
    private final UserAuthService userAuthService;

    public UserWs(UserService userService, UserAuthService userAuthService) {
        this.userService = userService;
        this.userAuthService = userAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<DataResult<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto){
        DataResult<LoginResponseDto> res = userAuthService.login(loginRequestDto);
        if (res.isSuccess()){
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @PostMapping("/save")
    public ResponseEntity<Result> save(@RequestBody UserDto userDto){
        Result result = userService.save(userDto);
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return result.getMessage() == Messages.MUST_BE_UNIQUE ?
                ResponseEntity.status(HttpStatus.CONFLICT).body(result) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<DataResult<UserDto>> getById(@PathVariable String id){

        DataResult<UserDto> result = userService.getById(Long.parseLong(id));
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/getAll")
    //@PreAuthorize("#userUpdateDto.getId() == #userDetailsManager.user.id") //SpEL(Spring Expression Language) (userDetailsManager yerine "principal.username" denebilir)
    public ResponseEntity<DataResult<UserDto>> getAll(){

        DataResult<UserDto> result = userService.getAll();
        if (result.isSuccess()){
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
