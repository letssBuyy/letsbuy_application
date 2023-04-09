package com.application.letsbuy.internal.services;

import com.application.letsbuy.api.usecase.AnnouncementInterface;
import com.application.letsbuy.internal.entities.User;
import com.application.letsbuy.internal.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService implements AnnouncementInterface {
    private final UserService userService;
    public AnnouncementService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String generateWppLink(Long id) {
        User userRecovered = userService.findById(id);
        if (userRecovered != null) {
            return "wa.me/55" + userRecovered.getPhoneNumber();
        } else {
            throw new UserNotFoundException();
        }
    }
}
