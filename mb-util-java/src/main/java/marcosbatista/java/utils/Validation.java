package marcosbatista.java.utils;

import java.util.Collection;
import java.util.Map;

public class Validation {
	
	public static final boolean isNull(Object value) {
		return value == null;
	}
	
	public static final boolean isNotNull(Object value) {
		return !isNull(value);
	}
	
	public static final boolean isEmpty(String value) {
		boolean isNull = isNull(value);
		return isNull || value.isEmpty();
	}
	
	public static final boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
	public static final boolean isEmpty(Collection<?> value) {
		boolean isNull = isNull(value);
		return isNull || value.isEmpty();
	}
	
	public static final boolean isNotEmpty(Collection<?> value) {
		return !isEmpty(value);
	}
	
	public static final boolean isEmpty(Map<?,?> value) {
		boolean isNull = isNull(value);
		return isNull || value.isEmpty();
	}
	
	public static final boolean isNotEmpty(Map<?,?> value) {
		return !isEmpty(value);
	}
	
	public static final boolean isNumber(Object value) {
		return value instanceof Number;
	}
	
	public static final boolean isNotNumber(Object value) {
		return !isNumber(value);
	}
	
}
