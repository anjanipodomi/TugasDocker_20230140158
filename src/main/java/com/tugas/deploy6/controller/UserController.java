package com.tugas.deploy6.controller;

import com.tugas.deploy6.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> dataMahasiswa = new ArrayList<>();

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model
    ) {
        username = username.trim();
        password = password.trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            session.setAttribute("isLogin", true);
            return "redirect:/home";
        }

        model.addAttribute("error", "Username dan password harus diisi");
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("isLogin") == null) {
            return "redirect:/";
        }

        model.addAttribute("dataMahasiswa", dataMahasiswa);
        return "home";
    }

    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("isLogin") == null) {
            return "redirect:/";
        }

        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(
            @RequestParam("nama") String nama,
            @RequestParam("nim") String nim,
            @RequestParam(value = "jenisKelamin", required = false) String jenisKelamin,
            HttpSession session
    ) {
        if (session.getAttribute("isLogin") == null) {
            return "redirect:/";
        }

        User user = new User(nama, nim, jenisKelamin);
        dataMahasiswa.add(user);

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
