package wu.justa.utils.test;

import wu.justa.utils.BeanCreator;

public class CustomizedStringCreator implements BeanCreator <String>{
	
	@Override
	public String createBean() {
		String bean = new String("===this is customized String===");
		return bean;
	}

	@Override
	public Class<String> getClazz() {
		return String.class;
	}
}