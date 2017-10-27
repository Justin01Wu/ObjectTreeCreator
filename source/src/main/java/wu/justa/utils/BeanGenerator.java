package wu.justa.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

public class BeanGenerator {
	private static Date NOW = new Date();
	
	private Map<Class<?>, BeanCreator<?>> reigsteredClass = new HashMap<>();
	
	private Stack<Class<?>> classStack = new Stack<>();
	
	private boolean verbose = false;
	
	public BeanGenerator(){
		this(false);
	}
	
	public BeanGenerator(boolean verbose){
		this.verbose = verbose;
		
		reigsteredClass.put(java.util.List.class, null);
		reigsteredClass.put(java.util.Set.class, null);
		reigsteredClass.put(java.util.Map.class, null);
		
		reigsteredClass.put(java.lang.Boolean.class, null);
		
		reigsteredClass.put(java.lang.Integer.class, null);
		reigsteredClass.put(java.lang.Short.class, null);		
		reigsteredClass.put(java.lang.Long.class, null);
		
		reigsteredClass.put(java.lang.Double.class, null);
		reigsteredClass.put(java.lang.Float.class, null);
		
		reigsteredClass.put(java.lang.Byte.class, null);
		
		reigsteredClass.put(java.lang.Character.class, null);
		reigsteredClass.put(java.lang.String.class, null);
		
		reigsteredClass.put(java.sql.Timestamp.class, null);
		reigsteredClass.put(java.sql.Date.class, null);
		reigsteredClass.put(java.util.Date.class, null);
		reigsteredClass.put(java.util.Calendar.class, null);
	}
	
	public void addExternalCreator(BeanCreator<?> creator){
		reigsteredClass.put(creator.getClazz(), creator);
	}	
	
	public <T> T generate(Class<T> clazz, Type type) throws Exception {
		
		Type[] types =  {type};
		if(classStack.contains(clazz)){
			// stop nested bean infinite loop 
			return null;
		}
		classStack.push(clazz);  // add myself to let children detect nested bean infinite loop 
		
		T container;
		if(reigsteredClass.keySet().contains(clazz)){
			container = handleBasicClass(clazz, types);
			classStack.pop();
			return container;
		}

		try{
			container = clazz.newInstance();
		}catch(Exception e){
			System.out.println(e.getMessage() + " on class" + clazz.getName());
			if(verbose){
				e.printStackTrace();	
			}			
			classStack.pop();
			return null;
		}
		
		Method[] allMethods = clazz.getMethods();
    	for (Method method : allMethods) {
    	    if (Modifier.isPublic(method.getModifiers())) {   
    	    	
    			if(!method.getName().startsWith("set")){
    				continue;
    			}
    			if( !method.getReturnType().equals(Void.TYPE)){
    				continue;
    			}

    			if(method.getParameters() == null){
    				continue;
    			}
    			if(method.getParameters().length  != 1){
    				continue;
    			}
    	        handleOneMethod(method, container);
    	    }
    	}
    	classStack.pop();  // remove myself to other class can handle the same class 
		return container;
		
	}
	
	public <T> T generate(Class<T> clazz ) throws Exception {
		return generate(clazz, null);
	}
	
	private void  handleOneMethod(Method method, Object container) throws Exception{

		
		Parameter parameter = method.getParameters()[0];
		
		Type[] types = method.getGenericParameterTypes();
		Object argOne = handleOneParameter(parameter, types);
		if(argOne == null){
			return;
		}
		try{
			method.invoke(container, argOne);
		}catch( Exception e){
			System.out.println(e.getMessage() + " on method" + method.getName());
			if(verbose){
				e.printStackTrace();	
			}			

		}
		
	}
	
	private Object handleOneParameter(Parameter parameter, Type[] types) throws Exception{
		Class<?> clazz  = parameter.getType();
		return handleBasicClass(clazz, types);
	}
	
	@SuppressWarnings("unchecked")
	private <T> T handleBasicClass(Class<T> clazz, Type[] types) throws Exception{		
		
		BeanCreator<?> customizedCreator = reigsteredClass.get(clazz);
		if(customizedCreator != null){
			Object o = customizedCreator.createBean();
			return (T)o;
		}
		
		if(Enum.class.isAssignableFrom(clazz)){
			return clazz.getEnumConstants()[0];
		}
		if(clazz.getName().equals("java.sql.Timestamp")){
			Timestamp  timestamp= new Timestamp(NOW.getTime());
			return (T)timestamp;
		}
		
		if(clazz.getName().equals("java.util.Date")){
			return (T)NOW;
		}
		if(clazz.getName().equals("java.sql.Date")){
			java.sql.Date sqlDate= new java.sql.Date(NOW.getTime());
			return (T)sqlDate;
		}
		
		if(clazz.getName().equals("java.util.Calendar")){
			Calendar cal = Calendar.getInstance();
			return (T)cal;
		}
		
		if(clazz.getName().equals("java.lang.String")){
			String string = new String( "a string");
			return (T)string;
		}
		
		if(clazz.getName().equals("java.lang.Character") || clazz.getName().equals("char")){
			Character myChar = 'A';
			return (T)myChar;
		}

		if(clazz.getName().equals("java.lang.Integer") || clazz.getName().equals("int")){
			Integer myInt = 12345;
			return (T)myInt;
		}
		
		if(clazz.getName().equals("java.lang.Long") || clazz.getName().equals("long")){
			Long myLong = 123456789012l;
			return (T)myLong;
		}
		
		if(clazz.getName().equals("java.lang.Short") || clazz.getName().equals("short")){
			Short myObj = 123;
			return (T)myObj;
		}

		if(clazz.getName().equals("double") || clazz.getName().equals("java.lang.Double")){
			Double myDouble = 12345.6789012d;
			return (T)myDouble;
		}
		
		if(clazz.getName().equals("float") || clazz.getName().equals("java.lang.Float")){
			Float myObj = 12345.6789f;
			return (T)myObj;
		}

		if(clazz.getName().equals("byte") || clazz.getName().equals("java.lang.Byte")){
			Byte myObj = 1;
			return (T)myObj;
		}
		
		if( clazz.getName().equals("java.math.BigDecimal")){
			BigDecimal myBigDecimal = new BigDecimal(12345.6789d);
			return (T)myBigDecimal;
		}
		
		if(clazz.getName().equals("java.lang.Boolean") || clazz.getName().equals("boolean")){
			Boolean myBoolean = true;;
			return (T)myBoolean;
		}		
		
		if(clazz.getName().equals("java.util.List")){
			
			ParameterizedType pType = (ParameterizedType) types[0];
			Class<?> pClazz = (Class<?>) pType.getActualTypeArguments()[0];
			//System.out.println(pClazz); //prints out java.lang.Integer
			Object one = generate(pClazz);
			
			java.util.List<Object> list = new ArrayList<>();
			T t =(T)list;
			list.add(one);
			return t;
		}
		
		if(clazz.getName().equals("java.util.Set")){
			ParameterizedType pType = (ParameterizedType) types[0];
			Class<?> pClazz = (Class<?>) pType.getActualTypeArguments()[0];
			//System.out.println(pClazz); //prints out java.lang.Integer
			Object one = generate(pClazz);
			
			java.util.Set<Object> set = new HashSet<>();
			T t =(T)set;
			set.add(one);
			return t;
			
		}
		
		if(clazz.getName().equals("java.util.Map")){
			
			ParameterizedType pType = (ParameterizedType) types[0];
			
			Class<?> keyClazz = (Class<?>) pType.getActualTypeArguments()[0];
			//System.out.println(keyClazz); //prints out java.lang.Integer
			Object key = generate(keyClazz);
			
			Class<?> valueClazz = (Class<?>) pType.getActualTypeArguments()[1];
			//System.out.println(valueClazz); //prints out java.lang.Integer
			Object value = generate(valueClazz);
			
			java.util.Map<Object,Object> map = new HashMap<>();
			T t =(T)map;
			map.put(key,value);
			return t;
			
		}
		
		if(clazz.isArray()){
			
			Class<?> componentType = clazz.getComponentType();
			Object aObject = Array.newInstance(componentType, 1); //1 is length	        
			
			Object one = null;;
			if(componentType.isPrimitive()){
				one = handleBasicClass(componentType, null);
			}else{
				one = generate(componentType);	
			}
			
	        Array.set(aObject, 0, one); // set your value here in first cell
	        
	        return (T)aObject;
			// https://stackoverflow.com/questions/4901128/obtaining-the-array-class-of-a-component-type
			// TODO need to handle primitives and multiple-dimensional arrays,
//			Class<?> componentType = clazz.getComponentType();
//			Class<?> childClazz = Class.forName("[L" + componentType.getName() + ";");			
//			
//			Object object = Array.newInstance(childClazz, 0);
//			return (T)object;
			
			//String[] parameters = new String[1];
			//return (T)parameters;
			
			
		}
		
		
		return generate(clazz);


	}
	
}
