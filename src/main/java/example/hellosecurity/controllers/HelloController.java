package example.hellosecurity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class HelloController {
    @GetMapping("/")
    public String getIndex() {
        return "index.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            request.getSession().invalidate();
        return "redirect:/";
    }


    @GetMapping("/public-data")
    public String getPublicPage() {
        return "public-data/public.html";
    }

    @GetMapping("/private-data")
    public String getPrivatePage() {
        return "private-data/private.html";
    }
}
