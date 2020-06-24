package wu.justa.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import wu.justa.utils.bean.Address;
import wu.justa.utils.bean.BeanExtended;
import wu.justa.utils.bean.ManyDataType;

public class BeanGenerator2Test {

	
	@Test
	public void testBooleanCreator() throws Exception {
		BeanGenerator2 generator = new BeanGenerator2();		
		Address result = generator.generate(Address.class);
		assertEquals(result.getCountry(), "a String");
		assertEquals(result.getId(), 12345);
		
	}
	
	@Test
	public void testBeanExtended() throws Exception {
		BeanGenerator2 generator = new BeanGenerator2();
		BeanExtended result = generator.generate(BeanExtended.class);
		System.out.println(result);
		assertNotNull(result);  
		assertEquals(result.getId(), Integer.valueOf(12345));
		//assertEquals(result.getStringArray()[0],"a string");
		//assertEquals(result.getIntegerArray()[0], new Integer(12345));
		//assertEquals(result.getIntArray()[0], 12345);		
		//assertEquals(result.getUsers()[0].getId(), new Integer(12345));
		
		assertEquals(result.getMyLong(), Long.valueOf(123456789012l));
		assertTrue(result.getMyShort() == 123);
		assertTrue(result.getMyByte() == 1);
		assertTrue(result.getMyFloat() == 12345.6789f);
		//assertTrue(result.getMyCalendar() != null);
		assertTrue(result.getMyBoolean());

	}
	
	@Test
	public void testManyDataType() throws Exception {
		ManyDataType result = new BeanGenerator2().generate(ManyDataType.class);
		System.out.println(result);
//		assertEquals(result.getOneEnum(), TypeEnum.Admin);
		assertEquals(result.getOneBoolean2(), Boolean.TRUE);
//		assertTrue(result.getStringList().size() ==1);
//		assertEquals(result.getStringList().get(0), "a string");
		
//		assertTrue(result.getIntegerSet().size() ==1);
//		assertEquals(result.getIntegerSet().iterator().next(), new Integer(12345));
		
//		assertTrue(result.getAddressList().size() ==1);
//		assertEquals(result.getAddressList().get(0).getAddress(), "a string");
		
		
	}
	
}
