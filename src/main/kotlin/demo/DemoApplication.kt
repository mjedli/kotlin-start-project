package demo

import com.samskivert.mustache.Mustache
import com.samskivert.mustache.Mustache.TemplateLoader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mustache.MustacheEnvironmentCollector
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class DemoApplication {

	fun main(args: Array<String>) {
		runApplication<DemoApplication>(*args);
	}
}
