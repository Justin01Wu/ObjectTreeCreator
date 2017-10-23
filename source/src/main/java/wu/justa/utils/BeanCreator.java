package wu.justa.utils;

public interface BeanCreator<T> {
	
	public T createBean();
	public Class<T> getClazz();

}
