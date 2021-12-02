package demo

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
	properties = [
		"spring.datasource.url=jdbc:mysql://localhost:3306/db"
	]
)
class DemoApplicationTests(@Autowired val restTemplate: TestRestTemplate) {

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

}
