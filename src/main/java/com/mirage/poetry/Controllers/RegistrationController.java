package com.mirage.poetry.Controllers;


import com.mirage.poetry.Domains.Authority;
import com.mirage.poetry.Domains.Poet;
import com.mirage.poetry.Domains.RegistrationPojo;
import com.mirage.poetry.Domains.User;
import com.mirage.poetry.Repo.AuthRepo;
import com.mirage.poetry.Repo.PoetRepo;
import com.mirage.poetry.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RegistrationController {

    private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final PoetRepo poetRepo;
    private final AuthRepo authRepo;

    @PostMapping(value = "/register")
    @Transactional
    public ResponseEntity<String> registerUser(@RequestBody RegistrationPojo pojo) {
        User u = pojo.getUser();
        User newUser = new User(u.getUsername() , encoder.encode(u.getPassword()));
        ArrayList<Authority> list = new ArrayList<>();
        list.add(new Authority(newUser,"ROLE_USER"));
        newUser.setAuthorities(list);
        userRepo.save(newUser);
        Poet newPoet = new Poet(pojo.getPenName(),newUser);
        poetRepo.save(newPoet);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Created !");
    }


}
