package com.example.BankSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test")
public class TestController {
    @GetMapping (consumes = "application/json", path = "/test")
    public ResponseEntity<Boolean> Test()
    {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}