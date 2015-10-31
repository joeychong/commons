package com.ebizzone.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import com.ebizzone.log.LogFactory;
import com.ebizzone.log.Logger;

/**
 * This utility is use to convert an object to JSONObject. 
 * This class depends on JSONObject develop by <a href="json.org">json.org</a>.
 * You can donwload the source code from provided link.
 * 
 * @author jckoh
 * @since 1
 * @see {@link JSONField}, {@link JSONObject}
 */
public class JSONUtils {
	private static final int MAX_DEPTH = 10;
	private static final String GETTER_PREIX = "get";
	private static final Logger LOG = LogFactory.getLogger(JSONUtils.class);
	
	/**
	 * Private constructor. 
	 */
	private JSONUtils(){
		
	}
	
	/**
	 * Convert object to JSON object. Force all the values to String
	 * @param obj - Object that want to convert to JSONObject, required to mark all the fields using JSONField
	 * @return JSONObject
	 */
	public static JSONObject toJSONString(Object obj) {
		return toJSON(obj, true, 0, null);
	}
	
	/**
	 * Convert object to JSON object.
	 * @param obj - Object that want to convert to JSONObject, required to mark all the fields using JSONField
	 * @return JSONObject
	 * 
	 * @see {@link JSONField}, {@link JSONObject}
	 */
	public static JSONObject toJSON(Object obj) {
		return toJSON(obj, false, 0, null);
	}
	
	/**
	 * Convert object to JSON object. Force all the values to String
	 * @param obj - Object that want to convert to JSONObject, required to mark all the fields using JSONField
	 * @param filter - remove unwanted field(s) based on condition
	 * @return JSONObject
	 * 
	 * @see {@link JSONField}, {@link JSONObject}
	 */
	public static JSONObject toJSONString(Object obj, Filter filter) {
		return toJSON(obj, true, 0, filter);
	}
	
	/**
	 * Convert object to JSON object.
	 * @param obj - Object that want to convert to JSONObject, required to mark all the fields using JSONField
	 * @param filter - remove unwanted field(s) based on condition
	 * @return JSONObject
	 * 
	 * @see {@link JSONField}, {@link JSONObject}
	 */
	public static JSONObject toJSON(Object obj, Filter filter) {
		return toJSON(obj, false, 0, filter);
	}
	
	private static final String[] EMPTY = new String[]{};
	
	/**
	 * Loop through all declared fields. If JSONField annotation found, put the field in the JSON Object 
	 * @param obj - Object that contain JSONField
	 * @param forceString - force all the values to string if true
	 * @param depth - current depth of object. Use to prevent infinite loop
	 * @param filter - Filter that filter out unwanted fields. Put null if no filter
	 * @return JSONObject
	 */
	private static JSONObject toJSON(Object obj, boolean forceString, int depth, Filter filter) {
		JSONObject jsonObject = new JSONObject();
		Class<?> clazz = obj.getClass();
		//Field[] fields = clazz.getDeclaredFields();
		List<Field> fields = new ArrayList<Field>();
		do {
			if (clazz != null) {
				LOG.debug(clazz.getName());
				
				if (clazz.isInstance(Object.class)) {
					break;
				} else if (clazz.isInstance(String.class)) {
					break;
				} else if (clazz.isInstance(Boolean.class)) {
					break;
				} else if (clazz.isInstance(Short.class)) {
					break;
				} else if (clazz.isInstance(Integer.class)) {
					break;
				} else if (clazz.isInstance(Float.class)) {
					break;
				} else if (clazz.isInstance(Double.class)) {
					break;
				} else if (clazz.isInstance(Number.class)) {
					break;
				} else if (clazz.isInstance(JSONObject.class)) {
					break;
				} else if (clazz.isInstance(JSONArray.class)) {
					break;
				}
				
				for (Field f: clazz.getDeclaredFields()) {
					int m = f.getModifiers();
					if ((m & Modifier.TRANSIENT) == Modifier.TRANSIENT) {
						continue;
					} else if ((m & Modifier.STATIC) == Modifier.STATIC) {
						continue;
					}  else if ((m & Modifier.FINAL) == Modifier.FINAL) {
						continue;
					}
					fields.add(f);
				}
			}
		} while((clazz = clazz.getSuperclass()) != null); 
		
		clazz = obj.getClass();
		Field field = null;
		String key;
		String fieldName = null;
		String getterName = null;
		for (int i = 0; i < fields.size(); i ++) {
			field = fields.get(i);
			LOG.debug(field.getName());
			if (field.isAnnotationPresent(JSONField.class)) {
				JSONField jsonField = field.getAnnotation(JSONField.class);
				fieldName = field.getName();
				getterName = jsonField.getter();
				key = jsonField.name();
				if ("*".equals(key)) {
					key = fieldName;
				}
				
				if (filter != null) {
					String[] tags = jsonField.tags();
					boolean discard = false;
					if (tags.length == 1 && tags[0].length() == 0) {
						discard = filter.filter(EMPTY);
					} else {
						discard = filter.filter(tags);
					}
					
					if (discard) {
						continue;
					}
				}
				
				try {
					
					if ("*".equals(getterName)) {
						//LOG.debug(field.isAccessible());
						if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
							Object value = field.get(obj);
							process(jsonObject, key, value, forceString, depth, filter);
						} else {
							getterName = GETTER_PREIX.concat(fieldName.substring(0, 1).toUpperCase());
							//LOG.debug("Lenght:" + fieldName.length());
							if (fieldName.length() > 1) {
								getterName = getterName.concat(fieldName.substring(1));
							}
							
							Method method = clazz.getMethod(getterName);					
							Object value = method.invoke(obj);			
							process(jsonObject, key, value, forceString, depth, filter);
						}
					} else {
						Method method = clazz.getMethod(getterName);					
						Object value = method.invoke(obj);			
						process(jsonObject, key, value, forceString, depth, filter);
					}
				} catch (NoSuchMethodException e) {
					LOG.warn("toJSON", e);
				} catch (SecurityException e) {
					LOG.warn("toJSON", e);
				} catch (JSONException e) {
					LOG.warn("toJSON", e);
				} catch (IllegalAccessException e) {
					LOG.warn("toJSON", e);
				} catch (IllegalArgumentException e) {
					LOG.warn("toJSON", e);
				} catch (InvocationTargetException e) {
					LOG.warn("toJSON", e);
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * Put value to JSONObject
	 * @param obj - current JSON Object
	 * @param key - key of the attribute
	 * @param value - value of the attribute
	 * @param forceString - true if want to force all values to string
	 * @param depth - depth of the object
	 * @param filter - field filter
	 */
	private static void process(JSONObject obj, String key,  Object value, 
			boolean forceString, int depth, Filter filter) {
		boolean parse = true;
		Class<? extends Object> clazz;
		if (depth > MAX_DEPTH) {
			LOG.warn("exceed maximum depth!");
			return;
		} else if (value == null) {
			obj.put(key, JSONObject.NULL);
			return;
		} else {
			clazz = value.getClass();
		} 
		
		if (value instanceof String) {
			parse = false;
		} else if (value instanceof Boolean) {
			parse = false;
		} else if (value instanceof Short) {
			parse = false;
		} else if (value instanceof Integer) {
			parse = false;
		} else if (value instanceof Float) {
			parse = false;
		} else if (value instanceof Double) {
			parse = false;
		} else if (value instanceof Number) {
			parse = false;
		} else if (value instanceof JSONObject) {
			parse = false;
		} else if (value instanceof JSONArray) {
			parse = false;
		} else if (Collection.class.isAssignableFrom(clazz) || value instanceof Object[]) {

			Object[] collection;
			
			if (value instanceof Object[]) {
				collection = (Object[]) value;
			} else {
				collection = ((Collection<?>) value).toArray();
			}
			
			boolean firstTime = true;
			JSONArray array = new JSONArray();
			for (Object o : collection) {
				if (firstTime) {
					firstTime = false;
					if (o instanceof String) {
						parse = false;
					} else if (o instanceof Boolean) {
						parse = false;
					} else if (o instanceof Short) {
						parse = false;
					} else if (o instanceof  Integer) {
						parse = false;
					} else if (o instanceof Float) {
						parse = false;
					} else if (o instanceof Double) {
						parse = false;
					} else if (o instanceof Number) {
						parse = false;
					}
				}
			
				if (parse) {
					array.put(JSONUtils.toJSON(o, forceString, depth + 1, filter));
				} else {
					if (forceString) {
						array.put(o.toString());
					} else {
						array.put(o);
					}
				}
			}
			obj.put(key, array);
			return;
		} else if (JSONString.class.isAssignableFrom(clazz)) {
			parse = false;
		} 
		
		if (parse) {
			obj.put(key, JSONUtils.toJSON(value, forceString, depth + 1, filter));
		} else {
			if (forceString) {
				obj.put(key, value.toString());
			} else {
				obj.put(key, value);
			}
		}		
	}
	
	/**
	 * JSON field filter
	 * @author jckoh
	 *
	 */
	public interface Filter {
		/**
		 * Filter out unwanted fields.
		 * @param tags - tags defined in JSONField
		 * @return true if want to exclude the field from JSONObject
		 */
		public boolean filter(String[] tags);
	}
}
