package com.consign.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrierAccount implements Serializable {

	@NotNull
	private String carrierName;
	@NotNull
	private String accountNumber;
	@NotNull
	private Integer digits;
	@NotNull
	private Integer lastUsedIndex;
	@NotNull
	private Integer rangeStart;
	@NotNull
	private Integer rangeEnd;

}
