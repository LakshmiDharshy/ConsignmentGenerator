package com.consign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consign.model.CarrierAccount;
import com.consign.service.ConsignNumberGeneratorService;

@RestController
@RequestMapping(value = "/api/v1")
public class ConsignNumberGeneratorController {

	@Autowired
	private ConsignNumberGeneratorService consignNumberGeneratorService;

	@GetMapping("/getConsignNumber")
	public ResponseEntity<String> getConsignNumber(@RequestBody CarrierAccount carrierAccount) {
		return new ResponseEntity<>(consignNumberGeneratorService.getConsignNoteNumber(carrierAccount), HttpStatus.OK);
	}

}
