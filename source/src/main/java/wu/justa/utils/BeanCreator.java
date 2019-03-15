package wu.justa.utils;

/**
 * 
 * @author justin.wu
 *
 * @param <T>
 */
public interface BeanCreator<T> {
	
	public T createBean();
	public Class<T> getClazz();

}
