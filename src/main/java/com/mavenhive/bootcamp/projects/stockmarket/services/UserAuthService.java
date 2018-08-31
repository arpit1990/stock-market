package com.mavenhive.bootcamp.projects.stockmarket.services;

import com.mavenhive.bootcamp.projects.stockmarket.repositories.UsersRepository;
import com.mavenhive.bootcamp.projects.stockmarket.exceptions.IncorrectUserCredentialsException;
import com.mavenhive.bootcamp.projects.stockmarket.model.UserAuth;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserAuthService {
    @Inject
    private UsersRepository usersRepository;

    public UserAuth findByUsername(UserAuth userAuth) throws IncorrectUserCredentialsException {

        userAuth = usersRepository.findByUsernameAndPassword(userAuth);

        if (userAuth == null) {
            throw new IncorrectUserCredentialsException();
        }
        return userAuth;
    }
}