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
import net.bytebuddy.dynamic.DynamicType.Builder;
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
		
		Class<Address> dynamicType = GetterProxy.createProxy(Address.class);

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
		
		Class<BeanExtended> dynamicType = GetterProxy.createProxy(BeanExtended.class);

		assertEquals(dynamicType.newInstance().getId(), Integer.valueOf(12345));

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType.newInstance());
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}

}
