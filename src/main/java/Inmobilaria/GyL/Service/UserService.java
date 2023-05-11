package Inmobilaria.GyL.Service;

import Inmobilaria.GyL.Entities.ImageUser;
import Inmobilaria.GyL.Entities.User;
import Inmobilaria.GyL.Enums.Role;
import Inmobilaria.GyL.Repositories.UserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  { 
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

    @Transactional
    public void modifyUser(Long id,String name,String email,String password,ImageUser icon){
    
        Optional<User> response= userRepository.findById(id);
        if(response.isPresent()){ 
            
            User user=response.get(); 
          
            user.setName(name);
            user.setPassword(password);
            user.setIcon(icon); 
            
            userRepository.save(user);
        }
    } 
    
    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
