package ru.practicum.mainService.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainService.exceptions.NotFoundException;
import ru.practicum.mainService.user.dto.UserDto;
import ru.practicum.mainService.user.dto.UserMapper;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userRepository.save(userMapper.toUser(userDto));
        log.info("Новый User c ID {} создан.", user.getId());
        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userExists(userId);
        userRepository.deleteById(userId);
        log.info("User c ID {} удалён", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            log.info("Получен список всех User с параметрами ids={}, from={}, size={}", ids, from, size);
            return userMapper.toUserDto(userRepository.findAll(PageRequest.of(from / size, size)));
        } else {
            log.info("Получен список всех User с параметрами ids={}, from={}, size={}", ids, from, size);
            return userMapper.toUserDto(userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size)));
        }
    }

    private void userExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(User.class, userId);
        }
    }

}
