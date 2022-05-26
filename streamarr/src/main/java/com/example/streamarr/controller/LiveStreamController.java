package com.example.streamarr.controller;

import com.example.streamarr.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/live")
public class LiveStreamController {

    private final AccountService accountService;

    public LiveStreamController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/auth")
    public ResponseEntity AuthLive(@RequestBody String body) {
        String token = body.split("name=")[1].split("&")[0].trim(); // TODO: grappy thing
        if (!accountService.isValidToken(token)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}
