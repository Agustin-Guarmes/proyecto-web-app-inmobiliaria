package Inmobilaria.GyL.service;

import Inmobilaria.GyL.entity.ImageUser;
<<<<<<< HEAD
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.Enums.Role;
=======
import Inmobilaria.GyL.enums.Role;
import Inmobilaria.GyL.entity.User;
>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
import Inmobilaria.GyL.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
=======
import javax.servlet.http.HttpSession;
>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
<<<<<<< HEAD

@Service
public class UserService implements UserDetailsService{ 
=======
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService implements UserDetailsService{ 

>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
    @Autowired
    private UserRepository userRepository;
    
    
    @Transactional
    public void createUser(String email, String password, String name) {

        User user = new User();

        user.setEmail(email);
<<<<<<< HEAD
=======

>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setName(name);
        user.setRole(Role.CLIENT);

<<<<<<< HEAD
=======

>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
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
    public void modifyUser(Long id,String name,String password,ImageUser icon){
    
        Optional<User> response= userRepository.findById(id);
        if(response.isPresent()){ 
            
            User user = response.get(); 
          
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

<<<<<<< HEAD
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
=======

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072
        User user = userRepository.findByEmail(email);

        if (user != null) {

            List<GrantedAuthority> permissions = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

            permissions.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("userSession", user);

<<<<<<< HEAD
            return new User(user.getName(), user.getPassword(), permissions);
=======
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), permissions);
>>>>>>> 17791e52cfdcc2a7f3f66334130ebe9919e69072

        } else {
            return null;
        }
    }
}
