package io.submanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping(path = "/public")
    public String testPublic() {
        return "Hello World from public endpoint!";
    }

    @GetMapping(path = "/private")
    public String testPrivate(Principal principal) {
        String email = principal.getName();
        return "Hello World from private endpoint! " +email;
    }
}
