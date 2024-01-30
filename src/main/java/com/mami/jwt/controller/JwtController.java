package com.mami.jwt.controller;

import com.mami.jwt.entity.JwtRequest;
import com.mami.jwt.entity.JwtResponse;
import com.mami.jwt.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    private JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        logger.info("JwtController#createJwtToken(): {}",jwtRequest);
        return jwtService.createJwtToken(jwtRequest);
    }
}
