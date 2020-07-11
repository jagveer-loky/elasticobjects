package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.Models;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
import org.fluentcodes.projects.elasticobjects.utils.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigObject extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ModelConfigObject.class);
    private final Map<String, Method> getterMap;
    private final Map<String, Method> setterMap;

    public ModelConfigObject(EOConfigsCache provider, Builder bean) {
        super(provider, bean);
        this.getterMap = new LinkedHashMap<>();
        this.setterMap = new LinkedHashMap<>();
    }

    protected static Method findSetter(Field field) {
        Class fieldType = field.getType();
        Class modelClass = field.getDeclaringClass();
        try{
            return modelClass.getMethod(setter(field.getName()), fieldType);
        } catch (Exception e) {
            try {
                return modelClass.getMethod(setter(field.getName()), Object.class);
            } catch (Exception e1) {
                LOG.debug("Could not resolve getter for add " + field.getName() + ": " + e1.getMessage());
            }
        }
        return null;
    }
    public static String setter(final String field) {
        return "set" + Util.upperFirstCharacter(field);
    }
    public static String getter(final String field) {
        return "get" + Util.upperFirstCharacter(field);
    }


    protected static Method findGetter(Field field) {
        Class fieldType = field.getType();
        Class modelClass = field.getDeclaringClass();
        try {
            final String methodName = getter(field.getName());
            return modelClass.getMethod(methodName);
        } catch (Exception e) {
            LOG.debug("Could not resolve getter for add " + field.getName() + ": " + e.getMessage());
        }
        return null;
    }

    public ModelInterface getFieldModel(final String fieldName)  {
        return getField(fieldName).getModelConfig();
    }

    public Models getFieldModels(final String fieldName)  {
        return getField(fieldName).getModels();
    }

    public ModelInterface getFieldChild(final String fieldName)  {
        return getField(fieldName).getChildModel();
    }

    @Override
    public Class getFieldClass(final String fieldName)  {
        return getField(fieldName).getModelClass();
    }

    @Override
    public Set<String> keys(final Object object)  {
        resolve();
        return this.getFieldCacheMap().keySet();
    }

    @Override
    public int size(final Object object)  {
        resolve();
        int counter = 0;
        for (String key : this.getFieldKeys()) {
            if (get(key, object) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public List<Object> keysAsIs(Object object)  {
        resolve();
        return (List) this.getFieldKeys();
    }

    public boolean hasSetter(final String fieldName) {
        return setterMap.get(fieldName) != null;
    }

    @Override
    public void set(final String fieldName, final Object object, final Object value)  {
        resolve();
        if (fieldName == null) {
            throw new EoException("Setter: null key request for " + this.getModelKey() + "! ");
        }
        final Method setter = setterMap.get(fieldName);
        if (setter == null) {
            throw new EoException("No setter defined for " + this.getModelKey() + "." + fieldName + " but essential for object instance var access. ");
        }
        Object usedValue = value;
        if (getFieldModel(fieldName).isScalar()) {
            try {
                usedValue = ScalarConverter.transformScalar(getFieldModel(fieldName).getModelClass(), value);
            } catch (Exception e) {
                throw new EoException("Problem transforming field '" + fieldName + "' with value " + value.toString() + " " + value.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }
        try {
            setter.invoke(object, usedValue);
        } catch (Exception e) {
            throw new EoException("Problem setting field '" + fieldName + "' with value " + value.toString() + " " + value.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @Override
    public Object getAsIs(final Object fieldNameAsObject, final Object object)  {
        resolve();
        String fieldName = ScalarConverter.toString(fieldNameAsObject);
        final Method getter = getterMap.get(fieldName);
        if (getter == null) {
            throw new EoException("No getter defined for " + this.getModelKey() + "." + fieldName + " but essential for object instance var access. ");
        }
        try {
            return getter.invoke(object, null);
        } catch (Exception e) {
            throw new EoException(e);
        }
    }

    public boolean hasGetter(final String fieldName) {
        return getterMap.get(fieldName) != null;
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
        resolve();
        if (fieldName == null) {
            throw new EoException("Getter: null key request for " + this.getModelKey() + "! ");
        }
        final Method getter = getterMap.get(fieldName);
        if (getter == null) {
            throw new EoException("No getter defined for " + this.getModelKey() + "." + fieldName + " but essential for object instance var access. ");
        }
        try {
            return getter.invoke(object, null);
        } catch (Exception e) {
            // NullPointerException thrown when null value is add in the Object. Will be ignored.
            if (e.getCause() == null || !e.getCause().toString().equals("java.lang.NullPointerException")) {
                LOG.warn("Could not find value for " + fieldName + ": " + e.getClass() + " - " + e.getCause());
                throw new EoException(e);
            }
            return null;
        }

    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        resolve();
        if (fieldName == null) {
            return false;
        }
        return getterMap.get(fieldName) != null;
    }

    @Override
    public boolean hasKey(final String fieldName) {
        try {
            resolve();
        } catch (Exception e) {
            LOG.error("Problem resolving with fieldName '" + fieldName + "' in '" + getModelKey() + "'.", e);
        }
        if (fieldName == null) {
            return false;
        }
        boolean hasKey = getterMap.get(fieldName) != null;
        if (!hasKey) {
            LOG.warn("  Problem with fieldName '" + fieldName + "' in '" + getModelKey() + "'.");
        }
        return hasKey;
    }

    @Override
    public boolean hasKey(final Path path) {
        if (path.isEmpty()) {
            return false;
        }
        return hasKey(path.first());
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        resolve();
        if (get(fieldName, object) == null) {
            throw new EoException("Object value for " + fieldName + " is already null.");
        }
        set(fieldName, object, null);
    }

    @Override
    public Object create()  {
        resolve();
        if (getShapeType() == ShapeTypes.CONFIG) {
            return null;
        }
        if (getDefaultImplementation() == null || getDefaultImplementation().isEmpty()) {
            try {
                return getModelClass().getConstructor(EOConfigsCache.class).newInstance(getConfigsCache());
            } catch (Exception e) {
                //LOG.warn("Problem with creating " + getModelClass().getName() + e.getMessage());
                try {
                    return getModelClass().newInstance();
                } catch (Exception instantiationException) {
                    throw new EoException(e);
                }
            }
        } else {
            ModelInterface implementation = getConfigsCache().findModel(getDefaultImplementation());
            try {
                return implementation.getModelClass().getConstructor(EOConfigMap.class).newInstance(getConfigsCache());
            } catch (Exception e) {
                try {
                    return implementation.getModelClass().newInstance();
                } catch (Exception instantiationException) {
                    throw new EoException(e);
                }
            }
        }
    }

    @Override
    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        if (getFieldKeys() == null) {
            return;
        }
        for (String fieldKey : getFieldKeys()) {
            ModelInterface type;
            String fieldName;
            try {
                FieldConfig fieldCache = getConfigsCache().findField(fieldKey);
                fieldName = fieldCache.getFieldKey();
                if (fieldName == null) {
                    //throw new EoException("Null fieldName?! " + fieldName);
                    continue;
                }
                getFieldCacheMap().put(fieldName, fieldCache);
                type = fieldCache.getModelConfig();
                if (type == null) {
                    //throw new EoException("Problem getting model of field " + fieldName);
                    continue;
                }
                if ("java.lang".equals(type.getPackagePath())) {

                } else if (this.getPackagePath().equals(type.getPackagePath())) {

                } else {
                    if (getImportClasses(type.getModelKey()) == null) {
                        getImportClasses().put(type.getModelKey(), fieldCache.getModelConfig());
                    }
                }
            } catch (Exception e) {
                throw new EoException("Problem resolving field with name '" + fieldKey + "' defined within model '" + getModelKey() + "': Probably no field definition is provided.", e);
            }

            // If scope is for creating a bean from template, the class will be created by this.
            if (getConfigsCache().getScope() == Scope.CREATE) {
                continue;
            }
            if (ShapeTypes.INTERFACE == this.getShapeType()) {  // no getter/setter
                continue;
            }

            Class typeClass = type.getModelClass();
            String baseName = Util.upperFirstCharacter(fieldName);
            try {
                Method setterField = findSetMethod(getModelClass(), setter(fieldName), typeClass);
                setterMap.put(fieldName, setterField);
            } catch (Exception e) {
                try {
                    Method setterField = findSetMethod(getModelClass(), setter(fieldName), Object.class);
                    setterMap.put(fieldName, setterField);
                } catch (Exception e1) {
                    LOG.debug("Could not resolve getter for add " + baseName + ": " + e1.getMessage());
                }
            }

            try {
                Method getterField;
                if (typeClass.equals(Boolean.class)) {
                    getterField = findGetMethod(getModelClass(), "is" + baseName);
                } else {
                    getterField = findGetMethod(getModelClass(), getter(fieldName));
                }
                getterMap.put(fieldName, getterField);
            } catch (Exception e) {
                LOG.debug("Could not resolve getter for find" + baseName + ": " + e.getMessage());
            }
        }
    }

    private Method findSetMethod(final Class myClass, final String methodString, final Class typeClass)  {
        if (myClass == Object.class) {
            if (typeClass != null) {
                throw new EoException("Reached Object root class: Could not find " + methodString + " for " + typeClass.getSimpleName() + ".");
            }
            throw new EoException("Reached Object root class: Could not find " + methodString + ".");
        }
        try {
            Method method = myClass.getMethod(methodString, typeClass);
            return method;
        } catch (Exception e) {
            return findSetMethod(myClass.getSuperclass(), methodString, typeClass);
        }
    }

    private Method findGetMethod(final Class myClass, final String methodString)  {
        if (myClass == Object.class) {
            throw new EoException("Reached Object root class: Could not find getter " + methodString + ".");
        }
        try {
            Method method = myClass.getMethod(methodString);
            return method;
        } catch (Exception e) {
            return findGetMethod(myClass.getSuperclass(), methodString);
        }
    }


    public boolean equals(ModelConfigObject modelCache) {
        if (getModelKey() == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return getModelKey().equals(modelCache.getModelKey());
    }

    public boolean hasModel() {
        return true;
    }

    public boolean isScalar() {
        return false;
    }

    public boolean isEnum() {
        return false;
    }

    public boolean isMap() {
        return false;
    }

    public boolean isSet() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isListType() {
        return false;
    }

    public boolean isMapType() {
        return true;
    }

    public boolean isObject() {
        return true;
    }

    public boolean isNull() {
        return false;
    }


}
