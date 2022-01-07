package com.example.demo.controller

import com.example.demo.entity.User
import com.example.demo.services.Services
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class `MainController` (private val repository: Services) {

    @GetMapping("/")
    fun blog(model: Model):String {

        model["title"] = "My Application";

        if (repository.findByLogin("myname")?.firstname != null) {
            model["name"] = repository.findByLogin("myname")?.firstname!!;
            model["id"] = repository.findByLogin("myname")?.id.toString()!!;
        } else {
            model["name"] = "Default";
            model["id"] = "0";
        }

        return "blog";
    }

    @PostMapping("/saveUser")
    fun save(@ModelAttribute user: User, model: Model):String {

        try {
            println("test me now!! " + user.firstname);
            user.login = "mynamesaved";
            user.description = "description";

            if(user.firstname == "") {
                throw RuntimeException();
            }

            val userTemp = repository.save(user);

            if (userTemp?.firstname != null) {
                model["name"] = userTemp?.firstname!!;
                model["id"] = userTemp?.id.toString()!!;
            } else {
                model["name"] = "Default";
                model["id"] = "0";
            }

            model["title"] = "My Application";

            return "blog";
        } catch (e: RuntimeException) {
            throw Exception("code error 1");
        }
    }

    // delete mapping
    @GetMapping("/deleteUser/{id}")
    fun delete(@PathVariable id:String , model: Model):String {

        model["title"] = "My Application";
        model["name"] = "Default";
        model["id"] = "0";
        println("id : $id");

        repository.deleteById(id.toInt());

        return "blog";
    }

}