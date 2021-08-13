package utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import sun.reflect.ReflectionFactory;

import com.nqzero.permit.Permit;

@SuppressWarnings ( "restriction" )
public class Reflections {

    public static void setAccessible(AccessibleObject member) {
        String versionStr = System.getProperty("java.version");
        int javaVersion = Integer.parseInt(versionStr.split("\\.")[0]);
        if (javaVersion < 12) {
            // quiet runtime warnings from JDK9+
            Permit.setAccessible(member);
        } else {
            // not possible to quiet runtime warnings anymore...
            // see https://bugs.openjdk.java.net/browse/JDK-8210522
            // to understand impact on Permit (i.e. it does not work
            // anymore with Java >= 12)
            member.setAccessible(true);
        }
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        }
        catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    // 这个常用来设置各种类变量的值
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static Object getFieldValue(final Object obj, final String fieldName) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    // 获取第一个构造器
    public static Constructor<?> getFirstCtor(final String name) throws Exception {
        final Constructor<?> ctor = Class.forName(name).getDeclaredConstructors()[0];
        setAccessible(ctor);
        return ctor;
    }

    public static Object newInstance(String className, Object ... args) throws Exception {
        return getFirstCtor(className).newInstance(args);
    }

    public static <T> T createWithoutConstructor ( Class<T> classToInstantiate )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return createWithConstructor(classToInstantiate, Object.class, new Class[0], new Object[0]);
    }

    // 处理构造器不可访问的类实例化
    @SuppressWarnings ( {"unchecked"} )
    public static <T> T createWithConstructor ( Class<T> classToInstantiate, Class<? super T> constructorClass, Class<?>[] consArgTypes, Object[] consArgs )
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Constructor<? super T> objCons = constructorClass.getDeclaredConstructor(consArgTypes);
        setAccessible(objCons);
        // classToInstantiate是constructorClass的子类，为什么不直接实例化classToInstantiate，而需要调用constructorClass这一父类的构造器最后再强转?
        // 原因：(1)跳过newConstructorForSerialization方法中第一个if，防止直接返回父类
        // (2)获取到的 sc 还是 constructorClass 这个父类的构造器，实例化向下转型
        // (3)ReflectionFactory的newConstructorForSerialization实例化对象不调用构造器
        Constructor<?> sc = ReflectionFactory.getReflectionFactory().newConstructorForSerialization(classToInstantiate, objCons);
        setAccessible(sc);
        // 强转为T,这也是为什么要求constructorClass是T的父类
        return (T)sc.newInstance(consArgs);
    }

}
