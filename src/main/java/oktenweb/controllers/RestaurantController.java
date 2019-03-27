package oktenweb.controllers;


import oktenweb.models.Restaurant;
import oktenweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestaurantController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;


    @PostMapping("/saveRestaurant")
    public String saveRestaurant(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("name") String name){

        Restaurant restaurant = new Restaurant();
        restaurant.setPassword(passwordEncoder.encode(password));
        restaurant.setUsername(username);
        restaurant.setRestaurantName(name);

        userService.save(restaurant);

        return "login";
    }
}

