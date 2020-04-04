package com.chetan.actuatorservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Controller
@Validated
public class HelloWorldController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-person")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/hello-person/{id}")
    @ResponseBody
    public Greeting getByPersonId(
            @PathVariable @Min(value = 1, message = "=====> ID HAS TO BE GREATER THAN 1")
            @Max(value = 4, message = "=====> ID HAS TO BE LESS THAN OR EQUAL TO 4") long id) { //jsr 303 annotations
        if (id == 1) {
            return new Greeting(id, String.format(template, "Remo"));
        } else if (id == 2) {
            return new Greeting(id, String.format(template, "Bill"));
        } else if (id == 3) {
            return new Greeting(id, String.format(template, "Mike"));
        } else {
            return new Greeting(id, String.format(template, "Stranger"));
        }
    }

}