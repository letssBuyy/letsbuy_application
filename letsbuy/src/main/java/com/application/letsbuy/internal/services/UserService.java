package com.application.letsbuy.internal.services;

import com.application.letsbuy.api.usecase.UserInterface;
import com.application.letsbuy.internal.entities.User;
import com.application.letsbuy.internal.enums.ActiveInactiveEnum;
import com.application.letsbuy.internal.exceptions.UserConflictException;
import com.application.letsbuy.internal.exceptions.UserNotFoundException;
import com.application.letsbuy.internal.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserInterface {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        Optional<User> isUserExist = userRepository.findByEmail(user.getEmail());
        if (!(isUserExist.isPresent())) {
            userRepository.save(user);
        } else {
            throw new UserConflictException();
        }
    }

    @Override
    public User findByName(String name) {
        Optional<User> retrieveUserByName = userRepository.findByName(name);
        if (retrieveUserByName.isPresent()) {
            return retrieveUserByName.get();
        }
        throw new UserNotFoundException();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException();
    }
    @Override
    public void deleteById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            user.setIsActive(ActiveInactiveEnum.INACTIVE);
        } else {
            throw new UserNotFoundException();
        }
    }
}
