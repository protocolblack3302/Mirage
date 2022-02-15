package com.mirage.poetry.Services;


import com.mirage.poetry.Domains.User;
import com.mirage.poetry.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        var userFound = userRepo.findByUsername(username);
       return userFound.orElseThrow(()->new UsernameNotFoundException("User not found!"));

    }
}
