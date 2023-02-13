package wu.justa.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class BeanGenerator2 {
    public static <T> T newProxyInstance(Class<T> type, InvocationHandler handler) {
        return type.cast(Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[] {type}, handler));
    }

}
