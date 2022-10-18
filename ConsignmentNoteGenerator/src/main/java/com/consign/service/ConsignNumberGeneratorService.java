package com.consign.service;

import com.consign.model.CarrierAccount;

public interface ConsignNumberGeneratorService {

	 String getConsignNoteNumber(CarrierAccount carrierAccount);

}
