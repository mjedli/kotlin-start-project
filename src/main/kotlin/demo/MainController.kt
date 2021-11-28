package demo

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

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

}