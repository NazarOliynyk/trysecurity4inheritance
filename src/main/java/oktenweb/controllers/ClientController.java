package oktenweb.controllers;


import oktenweb.models.Client;
import oktenweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @PostMapping("/saveClient")
    public String saveClient(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name){

        Client client = new Client();
        client.setPassword(passwordEncoder.encode(password));
        client.setUsername(username);
        client.setClientName(name);

        userService.save(client);

        return "login";
    }

}
