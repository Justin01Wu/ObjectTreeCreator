package wu.justa.utils;

import net.sf.cglib.proxy.Enhancer;

public class BeanGenerator2 {
	
	public <T> T generate(Class<T> clazz) throws Exception {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback( new MyInterceptor());	
		 
		@SuppressWarnings("unchecked")
		T proxy = (T) enhancer.create();
		return proxy;
	}
	
	

}
