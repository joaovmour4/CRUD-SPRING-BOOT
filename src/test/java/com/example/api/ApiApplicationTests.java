package com.example.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.RecoveryUserLoginDto;
import com.example.api.services.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@InjectMocks
	private TokenService tokenService;

	@Test
	void criaUsuarioELogin() throws Exception {

		CreateUserDto createUserDto = new CreateUserDto(
            null,
            "JoãoV",
            "joao@email.com",
            "senhaSegura",
            "São Paulo"
        );

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createUserDto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("JoãoV"));

		RecoveryUserLoginDto recoveryUserLoginDto = new RecoveryUserLoginDto(
			"JoãoV",
			"senhaSegura"
		);

		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(recoveryUserLoginDto)))
				.andExpect(status().isAccepted());
	}

	@Test
	void verificaGeracaoTokenJWT() {
		String token = tokenService.generateToken(28L);
		assertNotNull(token);
	}

	@Test
	void validaTokenJWT() {
		String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIyOSIsImlhdCI6MTczODExNTk3NywiZXhwIjoxNzM4NzIwNzc3fQ.pI5CKIVkOHZ0lqdAQhk9_lrQe_9YhSxpt0xVd7saWmL6go24c7khjwnUr7pKDUpk";
		boolean validate = tokenService.validateToken(token);
		assertTrue(validate);
	}

}
