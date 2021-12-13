package demo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*

@Controller
class `MainController` (private val repository: UserRepository) {

    @GetMapping("/")
    fun blog(model: Model):String {

        model["title"] = "My Application";

        if (repository.findByLogin("myname")?.firstname != null) {
            model["name"] = repository.findByLogin("myname")?.firstname!!;
        } else {
            model["name"] = "Default";
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
            } else {
                model["name"] = "Default";
            }

            model["title"] = "My Application";

            return "blog";
        } catch (e: RuntimeException) {
            throw Exception("code error 1");
        }

    }

}