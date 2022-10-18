package com.consign.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.consign.exception.BadRequestException;
import com.consign.model.CarrierAccount;

public class ConsignNumberGeneratorServiceImplTest {

	@Test
	public void getConsignNoteNumberTest() {
		ConsignNumberGeneratorServiceImpl impl = new ConsignNumberGeneratorServiceImpl();

		CarrierAccount carrierAccount = new CarrierAccount();
		carrierAccount.setLastUsedIndex(19605);
		carrierAccount.setCarrierName("FreightmateCourierCo");
		carrierAccount.setAccountNumber("123ABC");
		carrierAccount.setDigits(10);
		carrierAccount.setLastUsedIndex(19604);
		carrierAccount.setRangeStart(19000);
		carrierAccount.setRangeEnd(20000);

		String consignNoteNumber = impl.getConsignNoteNumber(carrierAccount);
		assertEquals("FMCC123ABC00000196051", consignNoteNumber);
	}
	
	@Test
	public void getConsignNoteNumber_RangeValid1() {
		ConsignNumberGeneratorServiceImpl impl = new ConsignNumberGeneratorServiceImpl();

		CarrierAccount carrierAccount = new CarrierAccount();
		carrierAccount.setLastUsedIndex(19605);
		carrierAccount.setCarrierName("FreightmateCourierCo");
		carrierAccount.setAccountNumber("123ABC");
		carrierAccount.setDigits(10);
		carrierAccount.setLastUsedIndex(19604);
		carrierAccount.setRangeStart(20000);
		carrierAccount.setRangeEnd(19000);

		
		 Exception exception = assertThrows(
					BadRequestException.class, 
					() -> impl.getConsignNoteNumber(carrierAccount));
					
		 assertTrue(exception.getMessage().contains("lastIndexUsed is greater than or equal to rangeStart"));
	}
	
	@Test
	public void getConsignNoteNumber_RangeValid2() {
		ConsignNumberGeneratorServiceImpl impl = new ConsignNumberGeneratorServiceImpl();

		CarrierAccount carrierAccount = new CarrierAccount();
		carrierAccount.setLastUsedIndex(19605);
		carrierAccount.setCarrierName("FreightmateCourierCo");
		carrierAccount.setAccountNumber("123ABC");
		carrierAccount.setDigits(10);
		carrierAccount.setLastUsedIndex(20000);
		carrierAccount.setRangeStart(19000);
		carrierAccount.setRangeEnd(20000);

		
		 Exception exception = assertThrows(
					BadRequestException.class, 
					() -> impl.getConsignNoteNumber(carrierAccount));
					
		 assertTrue(exception.getMessage().contains("lastIndexUsed is less than rangeStart"));
	}

}
