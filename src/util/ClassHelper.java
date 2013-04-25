package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassHelper {
	public static void setPropertiesByMethods(Object o, String paramName,
			Object value) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Method[] method = o.getClass().getDeclaredMethods();
		for (int i = 0; i < method.length; i++) {
			// System.out
			// .println("method name :" + method[i].getName().toString());
			//
			// System.out
			// .println("set"
			// + (paramName.substring(0, 1).toUpperCase() + paramName
			// .substring(1)));
			if (method[i].getName().toString().toLowerCase()
					.equals("set" + (paramName.toLowerCase()))) {
				// System.out.println("value type:"+value.getClass().getSimpleName());
				method[i].invoke(o, value);
				break;
			}
		}
	}
}
