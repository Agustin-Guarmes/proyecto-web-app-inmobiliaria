package Inmobilaria.GyL.Service;

import Inmobilaria.GyL.Entities.ImageUser;
import Inmobilaria.GyL.Entities.User;
import Inmobilaria.GyL.Repositories.UserRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  { 
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void ModifyUser(Long id,String name,String email,String password,ImageUser icon){
    
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
