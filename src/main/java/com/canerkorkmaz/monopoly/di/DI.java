package com.canerkorkmaz.monopoly.di;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Optional;

public final class DI {
    private static DI instance = null;
    private HashMap<String, Class<?>> registry = new HashMap<>();
    private HashMap<String, Optional<?>> singletons = new HashMap<>();

    private DI() {

    }

    public static DI getInstance() {
        if (instance == null) {
            synchronized (DI.class) {
                if (instance == null) {
                    instance = new DI();
                }
            }
        }
        return instance;
    }

    /**
     * Just a helper for shortening DI.getInstance.instantiate
     */
    public static <T> T get(Class<T> clazz) {
        return getInstance().instantiate(clazz);
    }

    public <T> void register(Class<T> clazz) {
        this.register(clazz, clazz);
    }

    public <T> void register(Class<T> clazz, Class<? extends T> actualClazz) {
        this.registry.put(clazz.getName(), actualClazz);
    }

    public <T> void registerSingleton(Class<T> clazz) {
        this.registerSingleton(clazz, clazz);
    }

    public <T> void registerSingleton(Class<T> clazz, Class<? extends T> actualClazz) {
        this.registry.put(clazz.getName(), actualClazz);
        this.singletons.put(clazz.getName(), Optional.empty());
    }

    @SuppressWarnings("unchecked")
    public <T> T instantiate(Class<T> clazzName) {
        final Class<T> clazz = getClassFromRegistry(clazzName);
        final String name = clazzName.getName();
        if (singletons.containsKey(name)) {
            return ((Optional<T>) singletons.get(name)).orElseGet(() -> {
                T instance = instantiateImpl(clazz);
                singletons.put(name, Optional.of(instance));
                return instance;
            });
        }
        return instantiateImpl(clazz);
    }

    @SuppressWarnings("unchecked")
    private <T> T instantiateImpl(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> annotatedConstructor = null;
        Constructor<?> emptyConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getAnnotation(Injected.class) != null) {
                annotatedConstructor = constructor;
                break;
            } else if (emptyConstructor == null && constructor.getParameterCount() == 0) {
                emptyConstructor = constructor;
            }
        }
        if (annotatedConstructor == null) {
            if (emptyConstructor == null) {
                throw new RuntimeException("Cannot create class using DI");
            }
            try {
                return (T) emptyConstructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Cannot create class using DI", e);
            }
        }
        final Class<?>[] parameterTypes = annotatedConstructor.getParameterTypes();
        // TODO: Use proxy for handling cyclic dependencies
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = instantiate(parameterTypes[i]);
        }
        try {
            return (T) annotatedConstructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException("Cannot create class using DI", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Class<T> getClassFromRegistry(Class<T> clazz) {
        final Class<T> registryClazz = (Class<T>) registry.get(clazz.getName());
        if (registryClazz != null) {
            return registryClazz;
        }
        return clazz;
    }
}
