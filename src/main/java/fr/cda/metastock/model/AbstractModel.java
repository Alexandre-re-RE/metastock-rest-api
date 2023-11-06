package fr.cda.metastock.model;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractModel<T> {
    
    public AbstractModel<T> merge(T incoming) {
        Method[] methods = incoming.getClass().getMethods();
        Map<String, Method> getters = new HashMap<>();
        Map<String, Method> setters = new HashMap<>();
        for (Method method : methods) {
            if (
                method.getName().startsWith("get") &&
                !method.getName().equals("getClass")
            ) {
                getters.put(method.getName().replace("get", "").toLowerCase(), method);
            }

            if (method.getName().startsWith("is")) {
                getters.put(method.getName().replace("is", "").toLowerCase(), method);
            }

            if (method.getName().startsWith("set")) {
                setters.put(method.getName().replace("set", "").toLowerCase(), method);
            }
        }

        for (String field: getters.keySet()) {

            if(field.equals("id")) {
                continue;
            }

            Method getter = getters.get(field);
            Method setter = setters.get(field);

            try {
                Object incomingValue = getter.invoke(incoming);
                if(
                    incomingValue != null &&
                    !incomingValue.equals(getter.invoke(this)) &&
                    setter != null
                ) {
                    setter.invoke(this, incomingValue);
                }
            } catch (Exception ignored) {}
        }

        return this;
    }
}