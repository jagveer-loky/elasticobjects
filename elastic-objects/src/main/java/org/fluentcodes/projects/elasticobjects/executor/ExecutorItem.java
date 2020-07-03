package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * externalize reflection mechanism for static methods..
 * created 27.5.2018
 */
public class ExecutorItem {
    private Method method;
    private String methodName;
    private Class executorClass;
    private String className;
    private String[] args;

    public ExecutorItem(final Class executorClass, final String methodName, final String... args)  {
        this.args = args;
        this.executorClass = executorClass;
        try {
            this.method = executorClass.getMethod(methodName, TYPES.value.argClass);
        } catch (NoSuchMethodException e) {
            throw new EoException(e);
        }
        this.methodName = methodName;
        this.className = executorClass.getSimpleName();
    }

    public ExecutorItem(String toParse, TYPES type)  {
        if (toParse == null || toParse.isEmpty()) {
            throw new EoException("No call attribute to initialize Executor item parser is defined");
        }
        if (type == null) {
            throw new EoException("No type defined for " + toParse);
        }
        String[] keyArgs = toParse.split("\\(");
        if (keyArgs.length < 2) {
            throw new EoException("No args defined: " + keyArgs[0]);
        }

        String[] executeParts = keyArgs[0].split("\\.");

        if (executeParts.length < 2) {
            throw new EoException("No method defined: " + keyArgs[0]);
        }
        this.className = executeParts[0];
        this.methodName = executeParts[1];

        String argsAsString = keyArgs[1];
        argsAsString = argsAsString.replaceAll("\\s", "");
        argsAsString = argsAsString.replaceAll("\\)$", "");
        String[] args = argsAsString.split(",");
        List<String> concat = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("'") || (arg.startsWith("'") && !arg.endsWith("'"))) {
                if (i == args.length - 1) {
                    throw new EoException("Argument not closed! " + arg);
                }
                StringBuilder item = new StringBuilder(arg);
                for (int k = i + 1; k < args.length; k++) {
                    String nextArg = args[k];
                    item.append(",");
                    item.append(nextArg);
                    if (arg.endsWith("'")) {
                        i = k;
                        break;
                    }
                }
                concat.add(item.toString());
            } else {
                concat.add(arg);
            }
        }
        //https://stackoverflow.com/questions/4042434/converting-arrayliststring-to-string-in-java
        this.args = concat.stream().toArray(String[]::new);
        try {
            executorClass = type.getClass(className);
            method = executorClass.getMethod(methodName, type.getArgClass());
        } catch (Exception e) {
            throw new EoException("Problem setting reflection for " + toParse + ": " + e.getMessage());
        }
    }

    public Method getMethod() {
        return this.method;
    }

    /** static invocation of the method
     *
     * @param input Array of objects
     * @return
     * @
     */

    public Object invoke(final Object[] input)  {
        try {
            return this.method.invoke(null, new Object[]{input});
        } catch (Exception e) {
            throw new EoException(e);
        }
    }

    public Object invoke(final EO eo, Map attributes)  {
        List<Object> inputList = new ArrayList<>();
        for (String arg: args) {
            if (arg.equals("eo")|| arg.equals("adapter")) {
                inputList.add(eo);
            }
            else {
                inputList.add(attributes.get(arg));
            }
        }
        return this.invoke(inputList.toArray());
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Class getExecutorClass() {
        return this.executorClass;
    }

    public String getClassName() {
        return this.className;
    }

    protected int getArgsLength() {
        return args.length;
    }

    protected String[] getArgs() {
        return args;
    }

    protected String getArgs(int i) {
        if (i < args.length) {
            return args[i];
        }
        return null;
    }

    public enum TYPES {
        value(EO_STATIC.CP_STATICS, Object[].class),
        call(EO_STATIC.CP_CALLS, EO.class, Map.class);
        private String classPath;
        private Class[] argClass;

        TYPES(String classPath, Class... argClass) {
            this.classPath = classPath;
            this.argClass = argClass;
        }

        protected String getClassPath() {
            return classPath;
        }

        protected Class[] getArgClass() {
            return argClass;
        }

        protected Class getClass(String className)  {
            try {
                return Class.forName(classPath + "." + className);
            } catch (ClassNotFoundException e) {
                throw new EoException(e);
            }
        }

    }
}
