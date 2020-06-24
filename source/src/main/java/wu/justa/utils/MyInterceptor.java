package wu.justa.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MyInterceptor implements MethodInterceptor {
 
	static Set<Class<?>> ret = new HashSet<Class<?>>();
	{
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(Void.class);
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
	    if (method.getDeclaringClass() != Object.class ) {		    	
	    	
	    	Class<?> returnClazz = method.getReturnType();
	    	if( returnClazz == String.class) {
	    		return "a String";
	    	}
	    	
	    	if(returnClazz.isPrimitive() || isWrapperType(returnClazz)) {
	    		return handleBasicClass(returnClazz);
	    	}
	    	
			if(returnClazz.isArray()){
				
				Class<?> componentType = returnClazz.getComponentType();
				Object aObject = Array.newInstance(componentType, 1); //1 is length	        
				
				Object one = null;;
				if(componentType.isPrimitive() || isWrapperType(componentType)){
					one = handleBasicClass(componentType);
				}else{
					one = new BeanGenerator2().generate(componentType);	
				}
				
		        Array.set(aObject, 0, one); // set your value here in first cell
		        
		        return aObject;
			}
	    	
	    	// TODO handle other types
			System.out.println(returnClazz);
	    }
	    return proxy.invokeSuper(obj, args);
	}
	
	public static boolean isWrapperType(Class<?> clazz)  {
        return ret.contains(clazz);
    }

	private static <T> T handleBasicClass(Class<T> returnClazz) {
		if (returnClazz == int.class || returnClazz == Integer.class) {
			Integer myInt = 12345;
			return (T) myInt;
		}

		if (returnClazz == byte.class || returnClazz == Byte.class) {
			Byte myObj = 1;
			return (T) myObj;
		}

		if (returnClazz == short.class || returnClazz == Short.class) {
			Short myObj = 123;
			return (T) myObj;
		}

		if (returnClazz == long.class || returnClazz == Long.class) {
			Long myLong = 123456789012l;
			return (T) myLong;
		}

		if (returnClazz == double.class || returnClazz == Double.class) {
			Double myDouble = 12345.6789012d;
			return (T) myDouble;
		}

		if (returnClazz == float.class || returnClazz == Float.class) {
			Float myObj = 12345.6789f;
			return (T) myObj;
		}

		if (returnClazz == char.class || returnClazz == Character.class) {
			Character myChar = 'A';
			return (T) myChar;
		}

		if (returnClazz == boolean.class || returnClazz == Boolean.class) {
			Boolean myBoolean = true;
			;
			return (T) myBoolean;
		}
		throw new RuntimeException("unexpected type" + returnClazz);
	}



}
