package com.emrekaraman.user.ws;

import com.emrekaraman.user.business.dtos.UserDto;
import com.emrekaraman.user.business.services.UserService;
import com.emrekaraman.user.core.utilities.DataResult;
import com.emrekaraman.user.core.utilities.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userws")
public class UserWs {

    private final UserService userService;

    public UserWs(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserDto> getById(@PathVariable Long id){

        DataResult<UserDto> result = userService.getById(id);
        if (result.isSuccess()){
            return ResponseEntity.ok(result.getData());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getData());
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
