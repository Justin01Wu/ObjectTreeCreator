package wu.justa.utils;

import static net.bytebuddy.matcher.ElementMatchers.returns;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class GetterProxy {
	
	private Map<Class<?>, Class<?>> reigsteredClass = new HashMap<>();
	private Stack<Class<?>> classStack = new Stack<>();
	
	public GetterProxy() {
		reigsteredClass.put(java.util.Collection.class, null);
		reigsteredClass.put(java.util.List.class, null);		
		reigsteredClass.put(java.util.Set.class, null);
		reigsteredClass.put(java.util.Map.class, null);
		
		reigsteredClass.put(java.lang.Boolean.class, null);
		reigsteredClass.put(boolean.class, null);		
		
		reigsteredClass.put(java.lang.Integer.class, null);
		reigsteredClass.put(int.class, null);
		
		reigsteredClass.put(java.lang.Short.class, null);
		reigsteredClass.put(short.class, null);
		
		reigsteredClass.put(java.lang.Long.class, null);
		reigsteredClass.put(long.class, null);
		
		reigsteredClass.put(java.lang.Double.class, null);
		reigsteredClass.put(double.class, null);
		
		reigsteredClass.put(java.lang.Float.class, null);
		reigsteredClass.put(float.class, null);
		
		reigsteredClass.put(java.lang.Byte.class, null);
		reigsteredClass.put(byte.class, null);
		
		reigsteredClass.put(java.lang.Character.class, null);
		reigsteredClass.put(java.lang.String.class, null);
		
		reigsteredClass.put(java.sql.Timestamp.class, null);
		reigsteredClass.put(java.sql.Date.class, null);
		reigsteredClass.put(java.util.Date.class, null);
		reigsteredClass.put(java.util.Calendar.class, null);

	}
	
	public <T> T createProxy(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		if(classStack.contains(clazz)){
			// stop nested bean infinite loop 
			return null;
		}
		classStack.push(clazz);  // add myself to let children detect nested bean infinite loop
		DynamicType.Unloaded unloadedType = new ByteBuddy().subclass(clazz)
				.method(ElementMatchers.isGetter().and(returns(String.class)))
				.intercept(FixedValue.value(Default.ss))
				.method(ElementMatchers.isGetter().and(returns(Integer.class)))
				.intercept(FixedValue.value(Default.i))
				.method(ElementMatchers.isGetter().and(returns(int.class)))
				.intercept(FixedValue.value(Default.i))
				.method(ElementMatchers.isGetter().and(returns(short.class)))
				.intercept(FixedValue.value(Default.s))
				.method(ElementMatchers.isGetter().and(returns(Short.class)))
				.intercept(FixedValue.value(Default.s))
				.method(ElementMatchers.isGetter().and(returns(byte.class)))
				.intercept(FixedValue.value(Default.b))
				.method(ElementMatchers.isGetter().and(returns(Byte.class)))
				.intercept(FixedValue.value(Default.b))				
				.method(ElementMatchers.isGetter().and(returns(float.class)))
				.intercept(FixedValue.value(Default.f))
				.method(ElementMatchers.isGetter().and(returns(Float.class)))
				.intercept(FixedValue.value(Default.f))					
				.method(ElementMatchers.isGetter().and(returns(Long.class)))
				.intercept(FixedValue.value(Default.l))
				.method(ElementMatchers.isGetter().and(returns(long.class)))
				.intercept(FixedValue.value(Default.l))
				
				.method(ElementMatchers.isGetter().and(returns(Boolean.class)))
				.intercept(FixedValue.value(true))
				.method(ElementMatchers.isGetter().and(returns(boolean.class)))
				.intercept(FixedValue.value(true))
				
				.method(ElementMatchers.isGetter().and(returns(Calendar.class)))
				.intercept(FixedValue.value(Calendar.getInstance()))
				.method(ElementMatchers.isGetter().and(returns(Date.class)))
				.intercept(FixedValue.value(new Date()))				
				.make();

		Class<T> dynamicType = unloadedType.load(clazz.getClassLoader()).getLoaded();
		
		T bean = dynamicType.newInstance();
		return bean;
	}

}
