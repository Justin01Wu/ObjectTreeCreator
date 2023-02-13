package wu.justa.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetterProxy implements InvocationHandler{
    private Object target;// underlying object
    
	public GetterProxy(Object target) {
		this.target = target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result=null;
		try {
			
			if (method.getName().startsWith("get") && args.length==1 ) {
				return new Object();// TOOD fix it
			}else {
				result = method.invoke(target, args);	
			}
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
		return result;
	}

}
