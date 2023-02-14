package wu.justa.utils;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import wu.justa.utils.bean.Address;
import wu.justa.utils.bean.BeanExtended;
import wu.justa.utils.bean.UserWithAdress;

public class GetterProxyTest {

	// byteBuddy sample
	@Test
	public void testAddress() throws Exception {
		
		Address dynamicType = new GetterProxy().generate(Address.class);

		assertEquals(dynamicType.getAddress(), Default.ss);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType);
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}
	
	@Test
	@Ignore("not finished")
	public void testBeanExtended() throws Exception {
		
		BeanExtended dynamicType = new GetterProxy().generate(BeanExtended.class);

		assertEquals(dynamicType.getId(), Integer.valueOf(12345));

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType);
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}
	
	@Test
	@Ignore("not finished")
	public void testUserWithAdress() throws Exception {
		
//		UserWithAdress bean = new GetterProxy().generate(UserWithAdress.class);
//
//		assertEquals(bean.getId(), Integer.valueOf(12345));
//		assertEquals(bean.getAddress().getId(), 12345);
		
		ClassLoader cl = Address.class.getClassLoader();
		System.out.println(cl);
		
		ReceiverTypeDefinition<Address> r = new ByteBuddy().subclass(Address.class)
		.method(ElementMatchers.isGetter().and(returns(String.class)))
		.intercept(FixedValue.value(Default.ss));
		
		r=r.method(ElementMatchers.isGetter().and(returns(Integer.class)))
		.intercept(FixedValue.value(Default.i));
		
		r=r.method(ElementMatchers.isGetter().and(returns(int.class)))
		.intercept(FixedValue.value(Default.i));
		
		Address address = GetterProxy.createObject(r, cl);

		ReceiverTypeDefinition<UserWithAdress> r2 = new ByteBuddy().subclass(UserWithAdress.class)
				.method(ElementMatchers.isGetter().and(returns(String.class)))
				.intercept(FixedValue.value(Default.ss));
				
				r2=r2.method(ElementMatchers.isGetter().and(returns(Integer.class)))
				.intercept(FixedValue.value(Default.i));
		
		
				r2=r2.method(ElementMatchers.isGetter().and(returns(int.class)))
					.intercept(FixedValue.value(Default.i));

				
				r2=r2.method(ElementMatchers.isGetter().and(returns(Address.class)))
				.intercept(FixedValue.value(address));		

		ClassLoader cl2 = UserWithAdress.class.getClassLoader();
		System.out.println(cl2);
		UserWithAdress bean = GetterProxy.createObject(r2, cl2);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(bean);
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);


	}

	
	

}
