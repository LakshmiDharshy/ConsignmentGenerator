package com.consign.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.consign.exception.BadRequestException;
import com.consign.model.CarrierAccount;
import com.consign.service.ConsignNumberGeneratorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsignNumberGeneratorServiceImpl implements ConsignNumberGeneratorService {

	static Logger logger = LoggerFactory.getLogger(ConsignNumberGeneratorServiceImpl.class);
	public static final String DEFAULT_CONSIGN_PREFIX = "FMCC";

	public String getConsignNoteNumber(CarrierAccount carrierAccount) {
		validateInput(carrierAccount);
		Integer nextIndex = getNextIndexedToUse(carrierAccount.getLastUsedIndex());
		String paddedString = paddingWithZero(String.valueOf(nextIndex), carrierAccount.getDigits());
		Integer checkSumValue = calculateCheckSum(paddedString);
		String consignNoteNumber = DEFAULT_CONSIGN_PREFIX + carrierAccount.getAccountNumber() + paddedString
				+ checkSumValue;
		logger.info("consignNoteNumber::" + consignNoteNumber);
		return consignNoteNumber;
	}

	private void validateInput(CarrierAccount carrierAccount) {
		if (carrierAccount.getRangeStart() > carrierAccount.getLastUsedIndex()) {
			throw new BadRequestException("lastIndexUsed is greater than or equal to rangeStart");

		} else if (carrierAccount.getLastUsedIndex() >= carrierAccount.getRangeEnd()) {
			throw new BadRequestException("lastIndexUsed is less than rangeStart");
		}
	}

	private static Integer getNextIndexedToUse(Integer lastIndexUsed) {
		return ++lastIndexUsed;
	}

	private static String paddingWithZero(String inputString, int paddedLength) {
		return "0".repeat(paddedLength - inputString.length()) + inputString;
	}

	public static Integer calculateCheckSum(String input) {
		List<Integer> digits = input.chars().map(Character::getNumericValue).boxed().collect(Collectors.toList());

		logger.info("digits::" + digits);

		List<Integer> reverseList = IntStream.rangeClosed(1, digits.size()).mapToObj(i -> digits.get(digits.size() - i))
				.collect(Collectors.toList());

		logger.info("reverseList::" + reverseList);

		List<Integer> evenList = IntStream.range(0, reverseList.size()).filter(i -> i % 2 == 0)
				.mapToObj(reverseList::get).collect(Collectors.toList());

		List<Integer> oddList = IntStream.range(0, reverseList.size()).filter(i -> i % 2 != 0)
				.mapToObj(reverseList::get).collect(Collectors.toList());

		Integer oddCount = oddList.stream().collect(Collectors.summingInt(Integer::intValue)) * 7;
		Integer evenCount = evenList.stream().collect(Collectors.summingInt(Integer::intValue)) * 3;

		Integer sum = oddCount + evenCount;

		return getNextMultipleOfTen(sum) - sum;
	}

	public static Integer getNextMultipleOfTen(Integer n) {
		if (n % 10 != 0) {
			return n + (10 - n % 10);
		} else {
			return n;
		}
	}

	

}
