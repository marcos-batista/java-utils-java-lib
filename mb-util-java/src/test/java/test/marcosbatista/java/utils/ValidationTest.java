package test.marcosbatista.java.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import marcosbatista.java.utils.Validation;

public class ValidationTest {
	
	@Test
	public void isNull() {
		assertEquals(true, Validation.isNull(null));
	}
	
	@Test
	public void isNotNull() {
		assertEquals(true, Validation.isNotNull(new Object()));
	}
	
	@Test
	public void isEmpty() {
		
		//---------------------------------- string validation ----------------------------------//
		assertEquals(true, Validation.isEmpty(new String()));
		assertEquals(true, Validation.isEmpty(""));
		
		String string = null;
		assertEquals(true, Validation.isEmpty(string));
		
		string = "java";
		assertEquals(false, Validation.isEmpty(string));
		
		//-------------------------------- collection validation --------------------------------//
		assertEquals(true, Validation.isEmpty(new ArrayList<String>()));
		
		Collection<String> collection = null;
		assertEquals(true, Validation.isEmpty(collection));
		
		collection = new ArrayList<String>();
		assertEquals(true, Validation.isEmpty(collection));
		
		collection.add(string);
		assertEquals(false, Validation.isEmpty(collection));
		
		//----------------------------------- map validation -----------------------------------//
		assertEquals(true, Validation.isEmpty(new HashMap<Short, Integer>()));
		
		Map<Integer, String> map = null;
		assertEquals(true, Validation.isEmpty(map));
		
		map = new HashMap<Integer, String>();
		assertEquals(true, Validation.isEmpty(map));
		
		map.put(1, string);
		assertEquals(false, Validation.isEmpty(map));
	}
	
	@Test
	public void isObjectNumber() {
		Byte byteNumber = 0;
		assertEquals(true, Validation.isNumber(byteNumber));
		
		Short shortNumber = byteNumber.shortValue();
		assertEquals(true, Validation.isNumber(shortNumber));
		
		Integer integerNumber = shortNumber.intValue();
		assertEquals(true, Validation.isNumber(integerNumber));
		
		Long longNumber = integerNumber.longValue();
		assertEquals(true, Validation.isNumber(longNumber));
		
	}
	
}
