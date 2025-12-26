package com.eventapp.service.impl;

import com.eventapp.entity.User;
import com.eventapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository repo;

    public List<User> all() { return repo.findAll(); }
    public Optional<User> get(Long id) { return repo.findById(id); }
    public User create(User u) { return repo.save(u); }
    public User update(Long id, User u) {
        u.setId(id);
        return repo.save(u);
    }
    public void delete(Long id) { repo.deleteById(id); }
}
