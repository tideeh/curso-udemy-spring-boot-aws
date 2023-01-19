package com.example.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class GreetingController {

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/1")
    public Greeting a1(
                        @RequestParam(value = "name", defaultValue = "World") 
                        String name) {

        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/2")
    public Greeting a2(
                        @RequestParam(value = "name", defaultValue = "World") 
                        String name) {

        return new Greeting(counter.incrementAndGet(), String.format(template, name), new Date());
    }

    @GetMapping("/3")
    public Map<String, Object> a3(
                @RequestParam(value = "name", defaultValue = "Joaquim") String name,
                @RequestParam(value = "idade", defaultValue = "18") String idade) {

        Map<String, Object> map = new HashMap<>();

        if(!isStringInt(idade)){
            idade = null;
        }

        map.put("name", name);
        map.put("idade", idade);

        return map;
    }

    public boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
    
}
