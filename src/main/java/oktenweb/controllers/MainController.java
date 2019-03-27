package oktenweb.controllers;

import oktenweb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



import oktenweb.dao.UserDAO;
import oktenweb.models.Client;
import oktenweb.models.Contact;
import oktenweb.models.Restaurant;
import oktenweb.models.User;

//import oktenweb.service.ClientService;
//import oktenweb.service.ContactService;
//import oktenweb.service.RestaurantService;

import oktenweb.service.UserService;
import oktenweb.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserDAO userDAO;
    @Autowired
    private ContactService contactService;
    @Autowired
    UserServiceImpl userServiceImpl;



    @GetMapping({"/", "/goToIndexPage"})
    public String index(){
        return "index";
    }

    @GetMapping("/restaurants")
    public String restaurants(){
        return "restaurant";
    }

    @GetMapping("/clients")
    public String clients(){
        return "client";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/goToSecuredPage")
    public String goToSecuredPage(){
        return "securedPage";
    }

    @PostMapping("/admin/goToSecuredPage2")
    public String goToSecuredPage2(){
        return "securedPage2";
    }

    String name = "";
    String className = "";
    String username = "";



    @PostMapping("/successURL")
    public String successURL(Model model){

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        System.out.println("auth.getName(): "+auth.getName());
        System.out.println("auth.toString(): "+auth.toString());
        System.out.println("auth.getPrincipal(): "+auth.getPrincipal());
        System.out.println("auth.getCredentials(): "+auth.getCredentials());
        System.out.println("auth.getDetails(): "+auth.getDetails());
        System.out.println("auth.getClass(): "+auth.getClass());
        User  userLogged = userDAO.findByUsername(auth.getName());
        //userLogged = (User) userServiceImpl.loadUserByUsername(auth.getName());
        Restaurant restaurant = new Restaurant();
        Client client = new Client();
        System.out.println("userLogged.toString(): "+userLogged.toString());
        System.out.println("userLogged.getClass(): "+userLogged.getClass());

        if(userLogged.getClass().equals(oktenweb.models.Restaurant.class)){
            System.out.println("USER -> RESTAURANT");
            restaurant = (Restaurant) userLogged;
            className = "RESTAURANT";
            username = restaurant.getUsername();
            name = restaurant.getRestaurantName();
        }else if (userLogged.getClass().equals(oktenweb.models.Client.class)){
            System.out.println("USER -> CLIENT");
            client = (Client) userLogged;
            className = "CLIENT";
            username = client.getUsername();
            name = client.getClientName();
        }else {
            System.out.println("UNDEFINED CLASS!");
        }
        model.addAttribute("className", className);
        model.addAttribute("username", username);
        model.addAttribute("name", name);

        return "securedPage";
    }

    @GetMapping("/saveContact")
    public String saveContact(Model model,
                              @RequestParam("username") String username,
                              @RequestParam("contactName") String contactName,
                              @RequestParam("email") String email){

        String usernameCurrent ="";
        Contact contact = new Contact();
        contact.setContactName(contactName);
        contact.setEmail(email);
        List<User> users = userDAO.findAll();
        for (User user : users) {
            if(user.getUsername().equals(username)&&user.getClass().equals(oktenweb.models.Restaurant.class)){
                Restaurant restaurant = (Restaurant) user;
                contact.setRestaurant(restaurant);
                contactService.save(contact);

                className = "RESTAURANT";
                usernameCurrent = username;
                name = restaurant.getRestaurantName();

                break;
            }else if (user.getUsername().equals(username)&&user.getClass().equals(oktenweb.models.Client.class)){
                Client client = (Client) user;
                contact.setClient(client);
                contactService.save(contact);

                className = "CLIENT";
                usernameCurrent = username;
                name = client.getClientName();

                break;
            }
        }


        model.addAttribute("className", className);
        model.addAttribute("username", usernameCurrent);
        model.addAttribute("name", name);

        return "securedPage";
    }
}

