package com.emrekaraman.ayrotek.configuration.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user",url = "http://localhost:5555")
public class UserServiceClient {
}
