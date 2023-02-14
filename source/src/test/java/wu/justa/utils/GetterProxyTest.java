package wu.justa.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import wu.justa.utils.bean.Address;
import wu.justa.utils.bean.BeanExtended;

public class GetterProxyTest {

	// byteBuddy sample
	@Test
	public void testAddress() throws JsonProcessingException, InstantiationException, IllegalAccessException {
		
		Address dynamicType = new GetterProxy().createProxy(Address.class);

		assertEquals(dynamicType.getAddress(), Default.ss);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType);
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}
	
	@Test
	public void testBeanExtended() throws JsonProcessingException, InstantiationException, IllegalAccessException {
		
		BeanExtended dynamicType = new GetterProxy().createProxy(BeanExtended.class);

		assertEquals(dynamicType.getId(), Integer.valueOf(12345));

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dynamicType);
		Object json;
		json = mapper.readValue(jsonInString, Object.class);
		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
		System.out.println(indented);

	}

}
