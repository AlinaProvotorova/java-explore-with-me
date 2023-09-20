package ru.practicum.mainService.user;

import ru.practicum.mainService.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto create(UserDto userDto);

    void delete(Long userId);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

}
