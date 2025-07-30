package marcosbatista.java.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {
	
	public static class Reflection {
		
		public static boolean isJavaNativeClass(Class<?> clazz) {
			String clazzPackageName = clazz.getPackageName();
			if(clazzPackageName.substring(0, 4).equals("java")) return true;
			if(clazzPackageName.substring(0, 5).equals("javax")) return true;
			return false;
		}
		
		public static boolean isInterface(Class<?> clazz) {
			return Validation.isNotNull(clazz) && clazz.isInterface();
		}
		
		public static Set<Field> getFields(Class<?> clazz) {
			Set<Field> fields = new HashSet<Field>();
			
			if(Reflection.isJavaNativeClass(clazz)) return fields;
			
			Set<Field> fieldsFromClazz = new HashSet<Field>();
			Field[] declaredFields = clazz.getDeclaredFields();
			fieldsFromClazz.addAll(Arrays.asList(declaredFields));
			fields.addAll(fieldsFromClazz);
			
			Set<Field> fieldsFromSuperClazz = new HashSet<Field>();
			Class<?> parentClazz = clazz.getSuperclass();
			fieldsFromSuperClazz = getFields(parentClazz);
			fields.addAll(fieldsFromSuperClazz);
			
			return fields;
		}
		
		//TODO: review this method to invode previously one getFields instead of repeat the implementation
		public static Field getField(Class<?> clazz, String fieldName) {
			if(Reflection.isJavaNativeClass(clazz)) return null;
			
			Set<Field> fields = new HashSet<Field>();
			Field[] declaredFields = clazz.getDeclaredFields();
			fields.addAll(Arrays.asList(declaredFields));
			for (Field field : fields) {
				if(field.getName().equals(fieldName)) {
					return field;
				}
			}
			
			Class<?> parentClazz = clazz.getSuperclass();
			Field field = getField(parentClazz, fieldName);
			if(!Validation.isNull(field)) {
				if(field.getName().equals(fieldName)) {
					return field;
				}
			}
			
			return null;
		}
		
		public static Set<Method> getMethods(Class<?> clazz) {
			Set<Method> methods = new HashSet<Method>();
			
			if(Reflection.isJavaNativeClass(clazz)) return methods;
			
			Set<Method> methodsFromClazz = new HashSet<Method>();
			Method[] declaredMethods = clazz.getDeclaredMethods();
			methodsFromClazz.addAll(Arrays.asList(declaredMethods));
			methods.addAll(methodsFromClazz);
			
			Set<Method> methodsFromSuperClazz = new HashSet<Method>();
			Class<?> parentClazz = clazz.getSuperclass();
			methodsFromSuperClazz = getMethods(parentClazz);
			methods.addAll(methodsFromSuperClazz);
			
			return methods;
		}
		
		public static Method getMethod(Class<?> clazz, String methodName, Class<?> methodReturnType, Class<?>...methodParameterTypes) {
			if(Reflection.isJavaNativeClass(clazz)) return null;
			
			Set<Method> declaredMethods = new HashSet<Method>();
			declaredMethods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
			for (Method declaredMethod : declaredMethods) {
				
				String declaredName = declaredMethod.getName();
				Class<?> declaredReturnType = declaredMethod.getReturnType();
				boolean hasSameReturnType = (methodReturnType.equals(Void.class)) ? 
					declaredReturnType.getName().equals(Util.Text.uncapitalize(methodReturnType.getSimpleName()))
					:declaredReturnType.equals(methodReturnType)
				;
				int declaredParameterCount = declaredMethod.getParameterCount();
				boolean isTheSameMethod = (declaredName.equals(methodName) && hasSameReturnType && declaredParameterCount == methodParameterTypes.length);
				if(!isTheSameMethod) continue;
				
				if(declaredMethod.getParameterCount() == 0) return declaredMethod;
				
				for (int i = 0; i < declaredMethod.getParameterTypes().length; i++) {
					isTheSameMethod = isTheSameMethod &&
						declaredMethod.getParameterTypes()[i].equals(methodParameterTypes[i])
					;
					if(isTheSameMethod) continue;
				}
				if(!isTheSameMethod) return null;
				return declaredMethod;
			}
			
			Class<?> parentClazz = clazz.getSuperclass();
			return getMethod(parentClazz, methodName, methodReturnType, methodParameterTypes);
		}
		
		public static Set<Annotation> getAnnotations(Class<?> clazz) {
			Set<Annotation> annotations = new HashSet<Annotation>();
			Collections.addAll(annotations, clazz.getAnnotations());
			return annotations;
		}
		
		public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
			return clazz.getAnnotation(annotationClass);
		}
		
	}
	
	public static class Text {
		
		public static String capitalize(String value) {
			if(Validation.isNull(value)) return null;
			String firstChar = value.substring(0, 1);
			firstChar = firstChar.toUpperCase();
			String capitalized = firstChar.concat(value.substring(1));
			return capitalized;
		}
		
		public static String uncapitalize(String value) {
			if(Validation.isNull(value)) return null;
			String firstChar = value.substring(0, 1);
			firstChar = firstChar.toLowerCase();
			String capitalized = firstChar.concat(value.substring(1));
			return capitalized;
		}

	}

	public static class Pojo {
		
		public static Set<Field> getProperties(Class<?> clazz) {
			return Reflection.getFields(clazz);
		}
		
		public static Field getProperty(Class<?> clazz, String propertyName) {
			return Reflection.getField(clazz, propertyName);
		}
		
		public static boolean isGetter(Method method) {
			return
				method.getName().startsWith("get") &&
				method.getParameterCount() == 0 &&
				!method.getReturnType().equals(Void.class)
			;
		}
		
		public static Method getGetter(Class<?> clazz, String propertyName) {
			Field property = Util.Reflection.getField(clazz, propertyName);
			String methodName = ("get").concat(Util.Text.capitalize(propertyName));
			return Util.Reflection.getMethod(clazz, methodName, property.getType());
		}
		
		public static boolean isSetter(Method method) {
			return
				method.getName().startsWith("set") &&
				method.getParameterCount() == 1 &&
				method.getReturnType().equals(void.class)
			;
		}
		
		public static Method getSetter(Class<?> clazz, String propertyName) {
			Field property = Util.Reflection.getField(clazz, propertyName);
			String methodName = ("set").concat(Util.Text.capitalize(propertyName));
			return Util.Reflection.getMethod(clazz, methodName, Void.class, property.getType());
		}
		
		public static Collection<Method> getCollectionGetters(Class<? extends Object> clazz) {
			Set<Method> collectionGetters = new HashSet<Method>();
			Set<Method> allMethods = Util.Reflection.getMethods(clazz);
			for (Method foundMethod : allMethods) {
				boolean isGetter = Util.Pojo.isGetter(foundMethod);
				if(isGetter)
					collectionGetters.add(foundMethod);
			}
			return collectionGetters;
		}
		
		public static Collection<Method> getCollectionSetters(Class<? extends Object> clazz) {
			Set<Method> collectionGetters = new HashSet<Method>();
			Set<Method> allMethods = Util.Reflection.getMethods(clazz);
			for (Method foundMethod : allMethods) {
				boolean isSetter = Util.Pojo.isSetter(foundMethod);
				if(isSetter)
					collectionGetters.add(foundMethod);
			}
			return collectionGetters;
		}
		
		public static Map<Method, Method> getMapGettersAndSetters(Class<? extends Object> clazz) {
			if(clazz.isInterface()) throw new RuntimeException("Param \"clazz\" is an interface.");
			Map<Method, Method> mapGettersAndSetters = new HashMap<Method, Method>();
			
			Collection<Method> collectionGetters = getCollectionGetters(clazz);
			for (Method getter : collectionGetters) {
				String fakePropertyName = Util.Text.uncapitalize(getter.getName().replace("get", ""));
				Method setter = Util.Pojo.getSetter(clazz, fakePropertyName);
				mapGettersAndSetters.put(getter, setter);
			}
			
			return mapGettersAndSetters;
		}
		
		public static boolean isPojo(Object object) {
			if(Validation.isNull(object)) throw new RuntimeException("Parameter \"object\" is null");
			Map<Method, Method> mapGettersAndSetters = Util.Pojo.getMapGettersAndSetters(object.getClass());
			return !Validation.isEmpty(mapGettersAndSetters);
		}
		
		public static String buildPropertyNameFromMethod(Method method) {
			boolean isGetter = Util.Pojo.isGetter(method);
			if(isGetter) return Util.Text.uncapitalize(method.getName().replace("get", ""));
			boolean isSetter = Util.Pojo.isSetter(method);
			if(isSetter) return Util.Text.uncapitalize(method.getName().replace("set", ""));
			return null;
		}
		
		public static void copyValues(Object source, Object destination) {
			if(Validation.isNull(source)) throw new RuntimeException("Source object is null");
			if(Validation.isNull(destination)) throw new RuntimeException("Destination object is null");
			if(source == destination) return;
			
			String methodKey = null;
			
			//--------------------------------------- get source getter methods ---------------------------------------------//
			Class<? extends Object> sourceClass = source.getClass();
			Map<String, Method> mapSourceGetters = new HashMap<String, Method>();
			Collection<Method> sourceCollectionGetters = Util.Pojo.getCollectionGetters(sourceClass);
			for (Method sourceGetter : sourceCollectionGetters) {
				methodKey = buildPropertyNameFromMethod(sourceGetter);
				mapSourceGetters.put(methodKey, sourceGetter);
			}
			
			//------------------------------------- get destination setter methods -----------------------------------------//
			Class<? extends Object> destinationClass = destination.getClass();
			Map<String, Method> mapDestionationSetters = new HashMap<String, Method>();
			Collection<Method> destionationCollectionSetter = Util.Pojo.getCollectionSetters(destinationClass);
			for (Method destinationSetter : destionationCollectionSetter) {
				methodKey = buildPropertyNameFromMethod(destinationSetter);
				mapDestionationSetters.put(methodKey, destinationSetter);
			}
			
			//---------------------------------------------- copy values ---------------------------------------------------//
			Set<String> mapKeys = mapDestionationSetters.keySet();
			for (String mapppedMethodKey : mapKeys) {
				
				Method sourceGetter = mapSourceGetters.get(mapppedMethodKey);
				if(Validation.isNull(sourceGetter)) continue;
				
				Method destinationSetter = mapDestionationSetters.get(mapppedMethodKey);
				if(Validation.isNull(destinationSetter)) continue;
				
				try
				{
					Object value = sourceGetter.invoke(source);
					destinationSetter.invoke(destination, value);
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
				catch (IllegalArgumentException e)
				{
					throw new RuntimeException(e);
				}
				catch (InvocationTargetException e)
				{
					throw new RuntimeException(e);
				}
			}
			
		}
		
	}
	
}
