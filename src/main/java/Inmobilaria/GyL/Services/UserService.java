package Inmobilaria.GyL.Services;

import Inmobilaria.GyL.Enums.Role;
import Inmobilaria.GyL.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Inmobilaria.GyL.Entities.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createUser(String email, String password, String name) {

        User user = new User();

        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setRole(Role.CLIENT);
        user.setCreateDate(Date.from(Instant.MIN));

        userRepository.save(user);
    }

    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    public List<User> listUsers() {
        List<User> users = new ArrayList();
        users = userRepository.findAll();

        return users;
    }
}
