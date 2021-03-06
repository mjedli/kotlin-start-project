package com.example.demo

import com.example.demo.entity.User
import com.example.demo.repositroy.UserRepository
import com.example.demo.repositroy.UserRepositoryPer
import com.example.demo.services.Services
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.util.AssertionErrors.assertEquals
import org.springframework.test.web.client.match.MockRestRequestMatchers.xpath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap


@WebMvcTest
class DemoApplicationMockTests(@Autowired val mockMvcTest: MockMvc) {

	@Test
	fun contextLoads() {
	}

	@MockkBean
	private lateinit var userRepository: Services;

	@Test
	fun `Assert blog page title, content and status code`() {

		every {userRepository.findByLogin("myname")} returns
				User(firstname = "firstname", lastname = "lastname", login = "login", description = "description" )

		mockMvcTest.get("/")
			.andExpect{status{isOk()}}
			.andExpect{ content { contentType("text/html;charset=UTF-8")}}
			.andExpect{xpath("/html/head/title").string(containsString("My Application"))}
			.andExpect{xpath("/html/body/h3").string(containsString("firstname"))}

		verify { userRepository.findByLogin("myname") }
	}

	@Test
	fun `Assert blog page title, content and status code for post`() {

		val user: User = User(firstname = "firstname", lastname = "lastname", login = "login", description = "description")

		every { userRepository.save(any()) } returns user

		val multimap: MultiValueMap<String, String> = LinkedMultiValueMap();
		multimap.add("firstname", "John");
		multimap.add("lastname", "Doe");

		mockMvcTest.perform(
			MockMvcRequestBuilders.post("/saveUser").params(multimap)
				.contentType("text/html;charset=UTF-8")
				.accept("text/html;charset=UTF-8")
		)
			.andExpect{xpath("/html/head/title").string(containsString("My Application"))}
			.andExpect{xpath("/html/body/h3").string(containsString("firstname"))}

		verify { userRepository.save(any()) }
	}

	@Test
	fun `Assert blog page title, content and status code for post exception`() {

		val user: User = User(firstname = "firstname", lastname = "lastname", login = "login", description = "description")

		every { userRepository.save(any()) } returns user

		val multimap: MultiValueMap<String, String> = LinkedMultiValueMap();
		multimap.add("firstname", "");
		multimap.add("lastname", "Doe");

		try {

				mockMvcTest.perform(
					MockMvcRequestBuilders.post("/saveUser").params(multimap)
						.contentType("text/html;charset=UTF-8")
						.accept("text/html;charset=UTF-8")
				)

		} catch (e: Exception) {
			e.message?.let { assertEquals(it, "code error 1", "code error 1") }
		}
	}


	@Test
	fun `Assert blog page title, content and status code for delete`() {

		every { userRepository.deleteById(any()) } returns 1

		mockMvcTest.get("/deleteUser/0")
			.andExpect{status{isOk()}}
			.andExpect{ content { contentType("text/html;charset=UTF-8")}}
			.andExpect{xpath("/html/head/title").string(containsString("My Application"))}
			.andExpect{xpath("/html/body/h6").string(containsString("0"))}

		verify { userRepository.deleteById("0".toInt()) }

	}

}
