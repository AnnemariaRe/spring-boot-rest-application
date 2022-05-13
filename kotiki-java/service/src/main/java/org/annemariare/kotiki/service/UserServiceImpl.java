package org.annemariare.kotiki.service;

import org.annemariare.kotiki.dao.UserRepo;
import org.annemariare.kotiki.converter.UserConverter;
import org.annemariare.kotiki.dto.UserDto;
import org.annemariare.kotiki.entity.UserEntity;
import org.annemariare.kotiki.enums.Role;
import org.annemariare.kotiki.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepository;

    @Autowired
    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserDto user) {
        user.setRole(Role.ROLE_USER);
        userRepository.save(UserConverter.toUser(user));
    }

    @Override
    public List<UserDto> getAll() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> dto = new ArrayList<>();
        for (var entity : users) {
            dto.add(UserConverter.fromUser(entity));
        }

        return dto;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        if(!userRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        userRepository.deleteById(id);
    }
}
