package com.example.demo

import com.example.demo.entity.User
import com.example.demo.repositroy.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = [],
		properties = [
		"spring.datasource.url=jdbc:mysql://localhost:3306/db"
	]
)
class DemoApplicationTests(@Autowired val restTemplate: TestRestTemplate,
						   @Autowired private val repository: UserRepository
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun `Assert blog page title, content and status code`() {
		val entity = restTemplate.getForEntity<String>("/")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<title>My Application</title>")
	}

	@Test
	fun `Assert blog page title, content and status code for post`() {

		val data = LinkedMultiValueMap(
			mapOf("firstname" to listOf("John"), "lastname" to listOf("Doe"))
		)

		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
		val requestEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(data, headers)

		val entity = restTemplate.postForEntity<String>("/saveUser", requestEntity);

		if (entity != null) {
			assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		}
		if (entity != null) {
			assertThat(entity.body).contains("<title>My Application</title>")
			assertThat(entity.body).contains("John")
		}
	}

	@Test
	fun `Assert blog page title, content and status code for post exception`() {

		val data = LinkedMultiValueMap(
			mapOf("firstname" to listOf(""), "lastname" to listOf("Doe"))
		)

		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
		val requestEntity: HttpEntity<MultiValueMap<String, String>> = HttpEntity(data, headers)

		try {
			val entity = restTemplate.postForEntity<String>("/saveUser", requestEntity);
		} catch (e: Exception) {
			assertThat(e.message).isEqualTo("code error 1")
		}
	}

	@Test
	fun `Assert blog page title, content and status code for delete`() {

		val user: User = User(firstname = "firstname", lastname = "lastname", login = "login", description = "description")

		val userTemp: User? = repository.save(user);
		val id:String? = userTemp?.id.toString();

		val entity = restTemplate.getForEntity<String>("/deleteUser/$id")
		assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
		assertThat(entity.body).contains("<title>My Application</title>")
		assertThat(entity.body).contains("<h6>0</h6>")
		assertThat(entity.body).contains("/deleteUser/0")

	}

}
