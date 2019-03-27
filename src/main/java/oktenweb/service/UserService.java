package oktenweb.service;


import oktenweb.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService{

    void save(User user);

    List<User> findAll();

    User findOneById(Integer id);

}
