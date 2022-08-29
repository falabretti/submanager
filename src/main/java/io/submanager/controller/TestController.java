package io.submanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @GetMapping(path = "/public")
    public String testPublic() {
        return "Hello World from public endpoint!";
    }

    @GetMapping(path = "/private")
    public String testPrivate() {
        return "Hello World from private endpoint!";
    }
}
