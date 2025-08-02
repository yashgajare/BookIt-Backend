package com.backend.controllers;

import com.backend.entities.ServiceProvider;
import com.backend.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class ServiceProviderController {

    @Autowired
    private ServiceProviderService service;

    @GetMapping("/topExperts")
    public ResponseEntity<List<ServiceProvider>> getTopExperts(){

    	ResponseEntity<List<ServiceProvider>> topExperts;
        return null;
    }
}
