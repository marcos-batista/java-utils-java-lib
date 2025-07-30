package test.marcosbatista.java.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.beans.JavaBean;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.management.Query;

import org.junit.Test;

import marcosbatista.java.utils.Util;
import marcosbatista.java.utils.Validation;
import marcosbatista.java.utils.Util.Reflection;
import test.marcosbatista.java.utils.samples.CityImpl;
import test.marcosbatista.java.utils.samples.Country;
import test.marcosbatista.java.utils.samples.CountryImpl;
import test.marcosbatista.java.utils.samples.Person;
import test.marcosbatista.java.utils.samples.PersonImpl;
import test.marcosbatista.java.utils.samples.Province;
import test.marcosbatista.java.utils.samples.ProvinceImpl;

public class UtilTest {
	
	//=================================================================== Util.Reflection =====================================================================//
	@Test
	public void isJavaNativeClass() {
		assertTrue(Util.Reflection.isJavaNativeClass(Object.class));
		assertTrue(Util.Reflection.isJavaNativeClass(String.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Number.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Byte.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Short.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Integer.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Long.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Collection.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Set.class));
		assertTrue(Util.Reflection.isJavaNativeClass(HashSet.class));
		assertTrue(Util.Reflection.isJavaNativeClass(LinkedHashSet.class));
		assertTrue(Util.Reflection.isJavaNativeClass(List.class));
		assertTrue(Util.Reflection.isJavaNativeClass(ArrayList.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Vector.class));
		assertTrue(Util.Reflection.isJavaNativeClass(Map.class));
		assertTrue(Util.Reflection.isJavaNativeClass(HashMap.class));
		assertFalse(Util.Reflection.isJavaNativeClass(Util.class));
		assertFalse(Util.Reflection.isJavaNativeClass(Validation.class));
	}
	
	@Test
	public void isInterface() {
		assertTrue(Reflection.isInterface(Person.class));
		assertFalse(Reflection.isInterface(Query.class));
	}
	
	@Test
	public void getFields() {
		Set<Field> fields = Util.Reflection.getFields(CountryImpl.class);
		assertEquals(2, fields.size());
	}
	
	@Test
	public void getField() {
		Field field = null;
		
		field = Util.Reflection.getField(CountryImpl.class, "name");
		String simpleQualyfied_FIELD_Name = field.getDeclaringClass().getSimpleName() + "." + field.getName();
		assertEquals("CountryImpl.name", simpleQualyfied_FIELD_Name);

		field = Util.Reflection.getField(CountryImpl.class, "province");
		simpleQualyfied_FIELD_Name = field.getDeclaringClass().getSimpleName() + "." + field.getName();
		assertEquals("CountryImpl.province", simpleQualyfied_FIELD_Name);
		
	}
	
	@Test
	public void getMethods() {
		Set<Method> methods = Util.Reflection.getMethods(ProvinceImpl.class);
		assertEquals(2, methods.size());
		for (Method field : methods) {
			String simpleQualyfied_METHOD_Name = field.getDeclaringClass().getSimpleName() + "." + field.getName();
			assertTrue (
				"ProvinceImpl.setAcronym".equals(simpleQualyfied_METHOD_Name)	|| 
				"ProvinceImpl.getAcronym".equals(simpleQualyfied_METHOD_Name)
			);
		}
	}
	
	@Test
	public void getMethod() {
		Method method = Util.Reflection.getMethod(ProvinceImpl.class, "setAcronym", Void.class, String.class);
		assertEquals("setAcronym", method.getName());
		assertEquals("void", method.getReturnType().getSimpleName());
		assertEquals("java.lang.String", method.getParameters()[0].getType().getName());
	}
	
	@Test
	public void getAnnotations() throws NoSuchFieldException, SecurityException {
		Set<Annotation> annotations = null;
		annotations = Util.Reflection.getAnnotations(Person.class);
		assertTrue(annotations.size() == 0);
		annotations = Util.Reflection.getAnnotations(PersonImpl.class);
		assertTrue(annotations.size() == 1);
		for (Annotation annotation : annotations) {
			assertEquals(JavaBean.class, annotation.annotationType());
		}
	}
	
	@Test
	public void getAnnotation() throws NoSuchFieldException, SecurityException {
		Annotation annotation = null;
		annotation = Util.Reflection.getAnnotation(PersonImpl.class, JavaBean.class);
		assertTrue(Validation.isNotNull(annotation));
		assertEquals(JavaBean.class, annotation.annotationType());
		JavaBean javaBeanAnnotation = (JavaBean) annotation;
		assertEquals("name", javaBeanAnnotation.defaultProperty());
	}
	
	//====================================================================== Util.String ======================================================================//
	@Test
	public void capitalize() {
		String value = Util.Text.capitalize("java");
		assertEquals("Java", value);
	}
	
	@Test
	public void uncapitalize() {
		String value = Util.Text.uncapitalize("Java");
		assertEquals("java", value);
	}
		
	//====================================================================== Util.Pojo ======================================================================//
	@Test
	public void getProperties() {
		Set<Field> fields = Util.Pojo.getProperties(PersonImpl.class);
		assertEquals(2, fields.size());
		for (Field field : fields) {
			String simpleQualyfiedFieldName = field.getDeclaringClass().getSimpleName() + "." + field.getName();
			assertTrue (
				"PersonImpl.name".equals(simpleQualyfiedFieldName)	|| 
				"PersonImpl.age".equals(simpleQualyfiedFieldName)
			);
		}
	}
	
	@Test
	public void getProperty() {
		Field field = null;
		
		field = Util.Pojo.getProperty(PersonImpl.class, "name");
		String simpleQualyfiedFieldName = field.getDeclaringClass().getSimpleName() + "." + field.getName();
		assertEquals("PersonImpl.name", simpleQualyfiedFieldName);
		
		field = Util.Pojo.getProperty(PersonImpl.class, "age");
		simpleQualyfiedFieldName = field.getDeclaringClass().getSimpleName() + "." + field.getName();
		assertEquals("PersonImpl.age", simpleQualyfiedFieldName);
	}
	
	@Test
	public void getGetter() {
		Method getter = Util.Pojo.getGetter(CityImpl.class, "province");
		assertEquals("getProvince", getter.getName());
		assertEquals("Province", getter.getReturnType().getSimpleName());
	}
	
	@Test
	public void getSetter() {
		Method setter = Util.Pojo.getSetter(PersonImpl.class, "age");
		assertEquals("setAge", setter.getName());
		assertEquals("void", setter.getReturnType().getSimpleName());
		assertEquals("Integer", setter.getParameters()[0].getType().getSimpleName());
	}
	
	@Test
	public void isGetter() {
		Method getter = Util.Pojo.getGetter(PersonImpl.class, "name");
		boolean isGetter = Util.Pojo.isGetter(getter);
		assertTrue(isGetter);
	}
	
	@Test
	public void isSetter() {
		Method setter = Util.Pojo.getSetter(PersonImpl.class, "name");
		boolean isSetter = Util.Pojo.isSetter(setter);
		assertTrue(isSetter);
	}
	
	@Test
	public void copyValues1() {
		String name = "Marcos Batista"; Integer age = 55;
		
		Person one = new PersonImpl();
		one.setName(name);
		one.setAge(age);
		
		Person other = new PersonImpl();
		Util.Pojo.copyValues(one, other);
		
		assertEquals(name, other.getName());
		assertEquals(age, other.getAge());
	}
	
	@Test
	public void copyValues2() {
		String acronym = "MG";
		Province province = new ProvinceImpl();
		province.setAcronym(acronym);
		
		String name = "Brasil";
		Country one = new CountryImpl();
		one.setProvince(province);
		one.setName(name);
		
		Country other = new CountryImpl();
		Util.Pojo.copyValues(one, other);
		
		assertEquals(name, other.getName());
		assertEquals(province, other.getProvince());
		assertEquals(acronym, other.getProvince().getAcronym());
	}
	
	@Test
	public void copyValues3() {
		String acronym = "MG", name = "Brasil";
		
		Province province1 = new ProvinceImpl();
		province1.setAcronym(acronym);
		
		Country country1 = new CountryImpl();
		country1.setName(name);
		country1.setProvince(province1);
		
		Province province2 = new ProvinceImpl();
		Util.Pojo.copyValues(province1, province2);
		
		Country country2 = new CountryImpl();
		Util.Pojo.copyValues(country1, country2);
		country2.setProvince(province2);
		
		assertEquals(province2, country2.getProvince());
		assertEquals(province2.getAcronym(), country2.getProvince().getAcronym());
	}
	
	@Test
	public void getMapGettersAndSetters1() {
		Person person = new PersonImpl();
		Map<Method, Method> mapGettersAndSetters = Util.Pojo.getMapGettersAndSetters(person.getClass());
		assertTrue(Validation.isNotNull(mapGettersAndSetters));
		
		Country country = new CountryImpl();
		mapGettersAndSetters = Util.Pojo.getMapGettersAndSetters(country.getClass());
		assertTrue(Validation.isNotNull(mapGettersAndSetters));
		
		Province province = new ProvinceImpl();
		mapGettersAndSetters = Util.Pojo.getMapGettersAndSetters(province.getClass());
		assertTrue(Validation.isNotNull(mapGettersAndSetters));
		
	}
	
	@Test (expected = RuntimeException.class)
	public void getMapGettersAndSetters2() {
		Map<Method, Method> mapGettersAndSetters = Util.Pojo.getMapGettersAndSetters(Person.class);
		assertTrue(Validation.isNotNull(mapGettersAndSetters));
	}
	
	@Test
	public void isPojo1() {
		Person person = new PersonImpl();
		boolean isPojo = Util.Pojo.isPojo(person);
		assertTrue(isPojo);
		
		Country country = new CountryImpl();
		isPojo = Util.Pojo.isPojo(country);
		assertTrue(isPojo);
		
		Province province = new ProvinceImpl();
		isPojo = Util.Pojo.isPojo(province);
		assertTrue(isPojo);
	}
	
	@Test(expected = RuntimeException.class)
	public void isPojo2() {
		boolean isPojo = Util.Pojo.isPojo(null);
		assertTrue(isPojo);
	}

	
}
