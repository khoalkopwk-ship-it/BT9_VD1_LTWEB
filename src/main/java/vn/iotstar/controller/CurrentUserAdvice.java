package vn.iotstar.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.UserRepository;

@ControllerAdvice
public class CurrentUserAdvice {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CurrentUserAdvice(
            UserRepository userRepository,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @ModelAttribute("currentUser")
    public UserDTO currentUser(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication
                    instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return userRepository
            .findByEmailWithRole(authentication.getName())
            .map(userMapper::toDto)
            .orElse(null);
    }
}