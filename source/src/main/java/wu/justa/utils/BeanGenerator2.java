package wu.justa.utils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class BeanGenerator2 {
	
	public <T> T generate(Class<T> clazz) throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
		    if (method.getDeclaringClass() != Object.class ) {		    	
		    	if( method.getReturnType() == String.class) {
		    		return "a String";
		    	}
		    	
		    	if( method.getReturnType() == int.class || method.getReturnType() == Integer.class) {
		    		return 12345;
		    	}
		    	
		    	if( method.getReturnType() == byte.class || method.getReturnType() == Byte.class) {
		    		return 1;
		    	}
		    	
		    	if( method.getReturnType() == short.class || method.getReturnType() == Short.class) {
		    		return 123;
		    	}
		    	
		    	if( method.getReturnType() == long.class || method.getReturnType() == Long.class) {
		    		return 123456789012l;
		    	}

		    	if( method.getReturnType() == double.class || method.getReturnType() == Double.class) {
		    		return 12345.678d;
		    	}

		    	if( method.getReturnType() == float.class || method.getReturnType() == Float.class) {
		    		return 12345.6789f;
		    	}
		    	
		    	if( method.getReturnType() == boolean.class || method.getReturnType() == Boolean.class) {
		    		return true;
		    	}
		    	
		    	// TODO handle other types
				System.out.println(method.getReturnType());
		    }
		    return proxy.invokeSuper(obj, args);
		    
		});
		 
		@SuppressWarnings("unchecked")
		T proxy = (T) enhancer.create();
		return proxy;
	}

}
