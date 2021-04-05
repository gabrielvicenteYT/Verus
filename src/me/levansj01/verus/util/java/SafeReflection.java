package me.levansj01.verus.util.java;

import com.google.common.base.Joiner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

public class SafeReflection {
    public static Method access(String[] arrstring, String string, Class<?> ... arrclass) {
        ReflectiveOperationException reflectiveOperationException = null;
        for (String string2 : arrstring) {
            try {
                Class<?> class_ = Class.forName(string2);
                Method method = class_.getDeclaredMethod(string, arrclass);
                method.setAccessible(true);
                return method;
            }
            catch (ClassNotFoundException | NoSuchMethodException reflectiveOperationException2) {
                reflectiveOperationException = reflectiveOperationException2;
            }
        }
        throw new IllegalArgumentException(String.join((CharSequence)",", arrstring) + ":" + string + "(", reflectiveOperationException);
    }

    public static <T> T fetchConstructor(Constructor<T> constructor, Object ... arrobject) {
        try {
            return constructor.newInstance(arrobject);
        }
        catch (IllegalAccessException | InstantiationException | InvocationTargetException reflectiveOperationException) {
            throw new IllegalArgumentException(reflectiveOperationException);
        }
    }

    public static Map<String, Command> getKnownCommands(SimpleCommandMap simpleCommandMap) {
        return (Map)SafeReflection.getLocalField(SimpleCommandMap.class, simpleCommandMap, "knownCommands");
    }

    public static <T> Constructor<T> constructor(Class<T> class_, Class<?> ... arrclass) {
        try {
            Constructor<T> constructor = class_.getDeclaredConstructor(arrclass);
            constructor.setAccessible(true);
            return constructor;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException(noSuchMethodException);
        }
    }

    public static <T> T getLocalField(Class class_, Object object, String string) {
        return SafeReflection.fetch(SafeReflection.access(class_, string), object);
    }

    public static <T> T fetch(Field field, Object object) {
        try {
            return (T)field.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public static PacketPlayOutNamedEntitySpawn spawn(int n, UUID uUID, int n2, int n3, int n4, byte by, byte by2, int n5, DataWatcher dataWatcher, List<DataWatcher.WatchableObject> list) {
        throw new UnsupportedOperationException();
    }

    public static Field access(Class class_, String string) {
        try {
            Field field = class_.getDeclaredField(string);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new IllegalArgumentException(class_.getSimpleName() + ":" + string, noSuchFieldException);
        }
    }

    public static <T> void setLocalField(Class class_, Object object, String string, T t) {
        SafeReflection.set(SafeReflection.access(class_, string), object, t);
    }

    public static Method access(Class<?> class_, String string, Class<?> ... arrclass) {
        try {
            Method method = class_.getDeclaredMethod(string, arrclass);
            method.setAccessible(true);
            return method;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException(class_.getSimpleName() + ":" + string + "(", noSuchMethodException);
        }
    }

    public static Class<?> findClass(String ... arrstring) {
        for (String string : arrstring) {
            try {
                return Class.forName(string);
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
        }
        throw new IllegalArgumentException("Could not find a class: " + Joiner.on((String)", ").join((Object[])arrstring));
    }

    public static SimpleCommandMap getCommandMap() {
        return (SimpleCommandMap)SafeReflection.getLocalField(Bukkit.getServer().getClass(), Bukkit.getServer(), "commandMap");
    }

    public static <T> T execute(Method method, Object object, Object ... arrobject) {
        try {
            return (T)method.invoke(object, arrobject);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            throw new IllegalArgumentException(reflectiveOperationException);
        }
    }

    public static Field access(Class class_, String ... arrstring) {
        for (String string : arrstring) {
            try {
                Field field = class_.getDeclaredField(string);
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
            }
        }
        throw new IllegalArgumentException(class_.getSimpleName() + ":" + Joiner.on((String)",").join((Object[])arrstring));
    }

    public static <T> void set(Field field, Object object, T t) {
        try {
            field.set(object, t);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public static <T> T fetch(Field field, Object object, Class<T> class_) {
        try {
            return (T)field.get(object);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalArgumentException(illegalAccessException);
        }
    }

    public static <T> T getLocalField(String[] arrstring, Object object, String string) {
        ReflectiveOperationException reflectiveOperationException = null;
        for (String string2 : arrstring) {
            try {
                Class<?> class_ = Class.forName(string2);
                Field field = class_.getDeclaredField(string);
                field.setAccessible(true);
                return (T)field.get(object);
            }
            catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException reflectiveOperationException2) {
                reflectiveOperationException = reflectiveOperationException2;
            }
        }
        throw new IllegalArgumentException(String.join((CharSequence)",", arrstring) + ":" + string, reflectiveOperationException);
    }
}

