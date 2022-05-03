package com.example.demo.util;

import org.springframework.validation.BindingResult;

public class Utils {

	public static String getFieldError(BindingResult bindingResult) {
		return bindingResult.getFieldError().getField() + " : " + bindingResult.getFieldError().getDefaultMessage();
	}
}