package wu.justa.utils;

import static net.bytebuddy.matcher.ElementMatchers.returns;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import wu.justa.utils.bean.Address;
import wu.justa.utils.bean.BeanExtended;

public class GetterProxyTest {

	@Test
	@Ignore("JDK builtIn proxy need interface, which is not easy for existing old code" )
	public void testInvoke() throws JsonProcessingException {
		GetterProxy proxy = new GetterProxy(Address.class.getInterfaces());
		Address x = BeanGenerator2.newProxyInstance(Address.class, proxy);
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(x);

		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}

	// byteBuddy sample
	@Test
	public void testAddress() throws JsonProcessingException, InstantiationException, IllegalAccessException {

		DynamicType.Unloaded unloadedType = new ByteBuddy().subclass(Address.class)
				.method(ElementMatchers.isGetter().and(returns(String.class)))
				.intercept(FixedValue.value("Hello World ByteBuddy!"))
				.method(ElementMatchers.isGetter().and(returns(int.class)))
				.intercept(FixedValue.value(12345)).make();

		Class<Address> dynamicType = unloadedType.load(Address.class.getClassLoader()).getLoaded();

		assertEquals(dynamicType.newInstance().getAddress(), "Hello World ByteBuddy!");

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType.newInstance());
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}
	
	@Test
	public void testBeanExtended() throws JsonProcessingException, InstantiationException, IllegalAccessException {
		byte b = 123;
		short s = 123;
		float f = 123.45f;

		DynamicType.Unloaded unloadedType = new ByteBuddy().subclass(BeanExtended.class)
				.method(ElementMatchers.isGetter().and(returns(String.class)))
				.intercept(FixedValue.value("Hello World ByteBuddy!"))
				.method(ElementMatchers.isGetter().and(returns(Integer.class)))
				.intercept(FixedValue.value(12345))
				.method(ElementMatchers.isGetter().and(returns(short.class)))
				.intercept(FixedValue.value(s))
				.method(ElementMatchers.isGetter().and(returns(byte.class)))
				.intercept(FixedValue.value(b))
				.method(ElementMatchers.isGetter().and(returns(float.class)))
				.intercept(FixedValue.value(f))				
				.method(ElementMatchers.isGetter().and(returns(Long.class)))
				.intercept(FixedValue.value(1234567l))
				.method(ElementMatchers.isGetter().and(returns(Boolean.class)))
				.intercept(FixedValue.value(true))
				.method(ElementMatchers.isGetter().and(returns(Calendar.class)))
				.intercept(FixedValue.value(Calendar.getInstance()))
				
				.make();

		Class<BeanExtended> dynamicType = unloadedType.load(BeanExtended.class.getClassLoader()).getLoaded();

		assertEquals(dynamicType.newInstance().getId(), Integer.valueOf(12345));

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType.newInstance());
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}

}
