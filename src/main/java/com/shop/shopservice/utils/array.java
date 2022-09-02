package com.shop.shopservice.utils;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/array")
public class array {
	@GetMapping("getarray")
	public int getAll(){
		int[] array1 = {1, 3, 5, 7, 9};
	     int elementToFind = 5;
	     int indexOfElement = IntStream.range(0, array1.length).
	             filter(i -> elementToFind == array1[i]).
	             findFirst().orElse(-1);
	     System.out.println("Index of " + elementToFind + " is " + indexOfElement);
		return indexOfElement;
	}
	 
}
