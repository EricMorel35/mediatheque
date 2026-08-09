package com.xtt.mediatheque.resources;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.service.KindService;

/**
 * Web slice tests for {@link KindResource}, including how
 * {@link com.xtt.mediatheque.exception.GlobalExceptionHandler} turns a
 * {@link TechnicalAccessException} into an HTTP response.
 *
 * @author Eric Morel
 */
@WebMvcTest(KindResource.class)
class KindResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private KindService kindService;

	@Test
	void getKinds_returnsServiceResult() throws Exception {
		KindsDTO kind = new KindsDTO();
		kind.setName("Comedy");
		when(kindService.getKinds()).thenReturn(List.of(kind));

		mockMvc.perform(get("/getKinds")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("Comedy"));
	}

	@Test
	void getKinds_whenTechnicalAccessException_returns400WithCodeAndMessage() throws Exception {
		when(kindService.getKinds()).thenThrow(new TechnicalAccessException("ERR-02", "Kind lookup failed"));

		mockMvc.perform(get("/getKinds")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("ERR-02")).andExpect(jsonPath("$.message").value("Kind lookup failed"));
	}

}
