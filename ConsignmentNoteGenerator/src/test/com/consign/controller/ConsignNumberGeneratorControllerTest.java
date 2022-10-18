package com.consign.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.consign.model.CarrierAccount;
import com.consign.service.ConsignNumberGeneratorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ConsignNumberGeneratorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private ConsignNumberGeneratorService consignNumberGeneratorService;

	@Test
	public void testGetExample() throws Exception {
		CarrierAccount carrierAccount = new CarrierAccount();
		carrierAccount.setLastUsedIndex(19605);
		carrierAccount.setCarrierName("FreightmateCourierCo");
		carrierAccount.setAccountNumber("123ABC");
		carrierAccount.setDigits(10);
		carrierAccount.setLastUsedIndex(19604);
		carrierAccount.setRangeStart(19000);
		carrierAccount.setRangeEnd(20000);

		Mockito.when(consignNumberGeneratorService.getConsignNoteNumber(carrierAccount))
				.thenReturn("FMCC123ABC00000196051");

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getConsignNumber").content(asJsonString(carrierAccount))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
