package ru.spbpu.weather.util.unit;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.validation.BindingResult;
import ru.spbpu.weather.controller.AuthController;
import ru.spbpu.weather.model.User;
import ru.spbpu.weather.security.SecurityConfig;
import ru.spbpu.weather.service.RegistrationService;
import ru.spbpu.weather.util.UserValidator;
import ru.spbpu.weather.util.testconfig.UnitTestConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({UnitTestConfig.class, SecurityConfig.class})
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private RegistrationService registrationService;

	@Test
	void shouldRegisterNewUserAndRedirectToLoginPage() throws Exception {
		doNothing().when(userValidator).validate(any(User.class), any(BindingResult.class));
		doNothing().when(registrationService).register(any(User.class));
		String correct = "correct";
		MockHttpServletRequestBuilder request = post("/auth/registration")
						.with(csrf())
						.param("username", correct)
						.param("password", correct);
		mockMvc.perform(request)
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/auth/login"));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(registrationService).register(userCaptor.capture());

		User capturedUser = userCaptor.getValue();
		assertEquals(correct, capturedUser.getUsername());
		assertEquals(correct, capturedUser.getPassword());
	}

	@Test
	void shouldRedirectAfterSuccessfulLogin() throws Exception {
		mockMvc.perform(formLogin("/process_login")
										.user("user")
										.password("password"))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/weather"));
	}

	@Test
	void shouldRedirectToLoginPageAfterUnsuccessfulLogin() throws Exception {
		mockMvc.perform(formLogin("/process_login")
										.user("incorrect")
										.password("incorrect"))
						.andExpect(status().is3xxRedirection())
						.andExpect(redirectedUrl("/auth/login?error"));
	}
}
