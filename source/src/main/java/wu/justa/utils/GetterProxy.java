package wu.justa.utils;

import static net.bytebuddy.matcher.ElementMatchers.returns;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;


public class GetterProxy {
	
	private static Logger LOG = Logger.getLogger(GetterProxy.class.getName());
	
	private Map<Class<?>, Object> reigsteredClass = new HashMap<>();
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
	
	public <T> T generate(Class<T> clazz ) throws Exception {
		return generate(clazz, null);
	}
	
	public <T> T generate(Class<T> clazz, Type type) throws Exception {
		
		Type[] types =  {type};
		if(classStack.contains(clazz)){
			// stop nested bean infinite loop 
			return null;
		}
		classStack.push(clazz);  // add myself to let children detect nested bean infinite loop
		
		T container  = handleJDKClass(clazz );
		boolean knownClass = false;

		if(clazz.isArray()){
			//array
			knownClass = true;
		}else if(Enum.class.isAssignableFrom(clazz)){
			// enum
			knownClass = true;
		}else if(reigsteredClass.keySet().contains(clazz)){
			// registered class
			knownClass = true;
		}
		System.out.println(" create object on class " + clazz.getName());	
		
		Method[] allMethods = clazz.getMethods();
    	for (Method method : allMethods) {
    	    if (!Modifier.isPublic(method.getModifiers())) {
    	    	continue;
    	    }
   	    	
			if(method.getName().startsWith("set")){
				continue;
			}
			if(method.getName().equals("getClass")){
				continue;
			}
			if( method.getReturnType().equals(Void.TYPE)){
				continue;
			}

			if(method.getParameters().length != 0){
				continue;
			}
			if(reigsteredClass.keySet().contains(method.getReturnType())) {
				continue;
			}			
			 
			Object methodReturn = new GetterProxy().generate(method.getReturnType());
			
			reigsteredClass.put(method.getReturnType(), methodReturn);		
			// TODO need to update current  DynamicType for this return
			

    	    
    	}
    	classStack.pop();  // remove myself to other class can handle the same class 
		return container;
	}
	
	private void  handleOneMethod(Method method, Object container, Type containerGeneric) throws Exception{

		
		Parameter parameter = method.getParameters()[0];
		
		Type[] types = method.getGenericParameterTypes();
		Object argOne = handleOneParameter(parameter, types, containerGeneric);
		if(argOne == null){
			return;
		}
		try{
			method.invoke(container, argOne);
		}catch( Exception e){
			LOG.log( Level.SEVERE, e.getMessage() + " on method " + method.getName(), e );
		}
		
	}
	
	private Object handleOneParameter(Parameter parameter, Type[] types, Type containerGeneric ) throws Exception{
		Class<?> clazz  = parameter.getType();
		return handleBasicClass(clazz, types, containerGeneric);
	}
	
	private <T> T handleJDKClass(Class<T> clazz ) throws Exception{		
		
		if(reigsteredClass.keySet().contains(clazz)){			
			throw new Exception("can't create proxy on JDK class");
		}
		
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
	
	private <T> T handleBasicClass(Class<T> clazz, Type[] types, Type containerGeneric ) throws Exception{	
		
//		GetterProxy o = reigsteredClass.get(clazz);
//		if(o != null){			
//			return (T)o;
//		}
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
	
	private void handleCollection(java.util.Collection<Object> collection, Type[] types,Type containerGeneric) throws Exception {

		
		if(types[0] instanceof ParameterizedType){
			ParameterizedType pType = (ParameterizedType) types[0];
			Type myType = pType.getActualTypeArguments()[0];     // get first generic type
			
			Class<?> pClazz = null;
			if(myType instanceof Class<?>) {
				pClazz = (Class<?>) myType;					
			}else {
				if(containerGeneric instanceof ParameterizedType ) {
					pClazz = (Class<?>)((ParameterizedType)containerGeneric).getActualTypeArguments()[0];							
				}	else {
					System.out.println("unexpected result");
				}
			}				  
			if(pClazz != null) {
				Object one = generate(pClazz);
				collection.add(one);
			}
		}else {
			LOG.fine( "can't handle non ParameterizedType for Collection" ); 
		}		
	}

}
