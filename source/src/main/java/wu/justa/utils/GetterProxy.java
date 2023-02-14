package wu.justa.utils;

import static net.bytebuddy.matcher.ElementMatchers.returns;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

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
	
	
	public static <T> Class<T> createProxy(Class<T> clazz) {
		DynamicType.Unloaded unloadedType = new ByteBuddy().subclass(clazz)
				.method(ElementMatchers.isGetter().and(returns(String.class)))
				.intercept(FixedValue.value(Default.ss))
				.method(ElementMatchers.isGetter().and(returns(Integer.class)))
				.intercept(FixedValue.value(12345))
				.method(ElementMatchers.isGetter().and(returns(short.class)))
				.intercept(FixedValue.value(Default.s))
				.method(ElementMatchers.isGetter().and(returns(byte.class)))
				.intercept(FixedValue.value(Default.b))
				.method(ElementMatchers.isGetter().and(returns(float.class)))
				.intercept(FixedValue.value(Default.f))				
				.method(ElementMatchers.isGetter().and(returns(Long.class)))
				.intercept(FixedValue.value(Default.l))
				.method(ElementMatchers.isGetter().and(returns(Boolean.class)))
				.intercept(FixedValue.value(true))
				.method(ElementMatchers.isGetter().and(returns(Calendar.class)))
				.intercept(FixedValue.value(Calendar.getInstance()))
				.method(ElementMatchers.isGetter().and(returns(Date.class)))
				.intercept(FixedValue.value(new Date()))				
				.make();

		Class<T> dynamicType = unloadedType.load(clazz.getClassLoader()).getLoaded();
		return dynamicType;
	}

}
