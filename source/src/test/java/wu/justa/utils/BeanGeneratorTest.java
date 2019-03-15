package wu.justa.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import wu.justa.utils.bean.BeanExtended;
import wu.justa.utils.bean.BeanWithArray;
import wu.justa.utils.bean.BeanWithMap;
import wu.justa.utils.bean.CustomizedBean;
import wu.justa.utils.bean.ManyDataType;
import wu.justa.utils.bean.NestedBean;
import wu.justa.utils.bean.NoGenericBean;
import wu.justa.utils.bean.TypeEnum;
import wu.justa.utils.bean.User;
import wu.justa.utils.bean.WrongSetterBean;
import wu.justa.utils.test.CustomizedBeanCreator;
import wu.justa.utils.test.CustomizedStringCreator;

public class BeanGeneratorTest {
	
	@Test
	public void testIntegerCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();		
		Integer result = generator.generate(Integer.class);
		assertEquals(result, new Integer(12345));
	}
	
	@Test
	public void testIntCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();		
		Integer result = generator.generate(int.class);
		assertEquals(result, new Integer(12345));
	}
	
	@Test
	public void testBooleanCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();		
		Boolean result = generator.generate(boolean.class);
		assertTrue(result);
	}


	@Test
	public void testCustomizedCreator() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExternalCreator(new CustomizedBeanCreator());
		CustomizedBean result = generator.generate(CustomizedBean.class);
		System.out.println(result);
		assertEquals(result.getId(), new Integer(-2323));
		assertEquals(result.getName(), "this bean is created by a customized creator");

	}
	
	@Test
	public void testManyDataType() throws Exception {
		ManyDataType result = new BeanGenerator().generate(ManyDataType.class);
		System.out.println(result);
		assertEquals(result.getOneEnum(), TypeEnum.Admin);
		assertEquals(result.getOneBoolean2(), Boolean.TRUE);
		assertTrue(result.getStringList().size() ==1);
		assertEquals(result.getStringList().get(0), "a string");
		
		assertTrue(result.getIntegerSet().size() ==1);
		assertEquals(result.getIntegerSet().iterator().next(), new Integer(12345));
		
		assertTrue(result.getAddressList().size() ==1);
		assertEquals(result.getAddressList().get(0).getAddress(), "a string");
		
		
	}
	
	@Test
	public void testString() throws Exception {
		String result = new BeanGenerator().generate(String.class);
		System.out.println(result);
		assertEquals(result, "a string");

	}
	
	@Test
	public void testDateConvert() throws Exception {
		String result = new BeanGenerator().generate(String.class);
		System.out.println(result);
		assertEquals(result, "a string");

	}
	
	@Test
	public void testCustomizedString() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		generator.addExternalCreator( new CustomizedStringCreator());
		String result = generator.generate(String.class);
		System.out.println(result);
		assertEquals(result, "===this is customized String===");

	}
	
	@Test
	public void testNestedBean() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		NestedBean result = generator.generate(NestedBean.class);
		System.out.println(result);
		assertNull(result.getParent());  //nested bean, so it will be null 
		assertEquals(result.getName(), "a string");

	}
	
	
	@Test
	public void testBeanWithMap() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		BeanWithMap result = generator.generate(BeanWithMap.class);
		System.out.println(result);
		assertNotNull(result.getUserMap());  
		
		User user = result.getUserMap().get(12345);
		assertEquals(user.getId(), new Integer(12345));
		assertEquals(user.getType(), TypeEnum.Admin);

	}
	
	@Test
	public void testBeanWithArray() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		BeanWithArray result = generator.generate(BeanWithArray.class);
		System.out.println(result);
		assertNotNull(result);  
		assertEquals(result.getStringArray()[0],"a string");
		assertEquals(result.getIntegerArray()[0], new Integer(12345));
		assertEquals(result.getIntArray()[0], 12345);
		assertEquals(result.getUsers()[0].getId(), new Integer(12345));
		

	}
	
	@Test
	public void testBeanExtended() throws Exception {
		BeanGenerator generator = new BeanGenerator();
		BeanExtended result = generator.generate(BeanExtended.class);
		System.out.println(result);
		assertNotNull(result);  
		assertEquals(result.getId(), new Integer(12345));
		assertEquals(result.getStringArray()[0],"a string");
		assertEquals(result.getIntegerArray()[0], new Integer(12345));
		assertEquals(result.getIntArray()[0], 12345);		
		assertEquals(result.getUsers()[0].getId(), new Integer(12345));
		
		assertEquals(result.getMyLong(), new Long(123456789012l));
		assertTrue(result.getMyShort() == 123);
		assertTrue(result.getMyByte() == 1);
		assertTrue(result.getMyFloat() == 12345.6789f);
		assertTrue(result.getMyCalendar() != null);
		assertTrue(result.getMyBoolean());

	}
	
	@Test
	public void testSetInteger() throws Exception {
		
    	Method[] allMethods = ManyDataType.class.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (method.getName().equals("getIntegerSet")) {
    	    	Class<?> clazz = method.getReturnType();
    	    	Type type = method.getGenericReturnType();
    	    	
    			BeanGenerator generator = new BeanGenerator();
    			@SuppressWarnings("unchecked")
				Set<Integer> result = (Set<Integer>)generator.generate(clazz, type);
    			System.out.println(result);
    			assertEquals(result.iterator().next(), new Integer(12345));
    	        
    	    }
    	}

		

	}


	@Test
	public void testStringArray() throws Exception {
		
		Class<?> stringArrayClass = String[].class;
		
		assertEquals(stringArrayClass.getName(), "[Ljava.lang.String;");
		
    	Method[] allMethods = BeanWithArray.class.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (method.getName().equals("getStringArray")) {
    	    	Class<?> clazz = method.getReturnType();
    	    	Type type = method.getGenericReturnType();
    	    	
    			BeanGenerator generator = new BeanGenerator();
				String[] result = (String[])generator.generate(clazz, type);
    			System.out.println(result);
    			assertEquals(result[0], "a string");
    	        
    	    }
    	}

	}
	
	@Test
	public void testEnumArray() throws Exception {
		
    	Method[] allMethods = ManyDataType.class.getDeclaredMethods();
    	for (Method method : allMethods) {
    	    if (method.getName().equals("getTypeList")) {
    	    	Class<?> clazz = method.getReturnType();
    	    	Type type = method.getGenericReturnType();
    	    	
    			BeanGenerator generator = new BeanGenerator();
    			@SuppressWarnings("unchecked")
				List<TypeEnum> result = (List<TypeEnum>)generator.generate(clazz, type);
    			System.out.println(result);
    			assertEquals(result.get(0), TypeEnum.Admin);
    	        
    	    }
    	}
		
	}
	
	@Test
	public void testNoGenericBean() throws Exception {		
		
		BeanGenerator generator = new BeanGenerator();
		NoGenericBean result = generator.generate(NoGenericBean.class);
		System.out.println(result);

	}

	@Test
	public void testWrongSetterBean() throws Exception {		
		
		BeanGenerator generator = new BeanGenerator();
		WrongSetterBean result = generator.generate(WrongSetterBean.class);
		assertNull(result.getMyBean());
		assertNull(result.getMyInt());

	}

}
