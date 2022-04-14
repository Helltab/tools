package icu.helltab.itool.object;


import java.util.HashMap;
import java.util.Map;

/**
 * Topic thread local register
 *
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 16:38
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Object> TL = new ThreadLocal<>();


    private ThreadLocalUtil(){}

    @SuppressWarnings("unchecked")
    public static <T> T get(T t) {
        Map<Class<T>, T> map = getMap();
        T res = map.get(t.getClass());
        if(null == res) {
            res = t;
            map.put((Class<T>) t.getClass(), res);
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private static <T> Map<Class<T>, T> getMap() {
        Map<Class<T>, T> map = (Map<Class<T>, T>) TL.get();
        if (map == null) {
            map = new HashMap<>();
            TL.set(map);
        }
        return map;
    }

    /**
     * save ThreadLocal
     *
     */
    @SuppressWarnings("unchecked")
    public static <T> void set(T t) {
        Map<Class<T>, T> map = getMap();
        map.put((Class<T>) t.getClass(), t);
    }

    /**
     * remove map in thread
     */
    public static void remove() {
        TL.remove();
    }

}
