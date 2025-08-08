package com.example.leavemanagement1.controller;

import com.example.leavemanagement1.model.User;
import com.example.leavemanagement1.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role.toUpperCase());
        userService.registerUser(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session) {
        User user = userService.login(username, password);
        if (user == null) {
            return "redirect:/login?error=Invalid username or password";
        }

        session.setAttribute("username", username);
        session.setAttribute("role", user.getRole());

        switch (user.getRole()) {
            case "STUDENT":
                return "redirect:/student/dashboard";
            case "STAFF":
                return "redirect:/staff/dashboard";
            case "ADMIN":
                return "redirect:/admin/dashboard";
            default:
                return "redirect:/login?error=Unknown role";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}

