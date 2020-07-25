package org.fluentcodes.projects.elasticobjects.utilobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Offers serialized setter and getter for java models
 */

public class ScalarPaths {
    private static final Logger LOG = LogManager.getLogger(ScalarPaths.class);
    private List<Object> values = new ArrayList<Object>();
    private List<String> names = new ArrayList<String>();

    public ScalarPaths(String[] names, Object[] values) {
        this.set(Arrays.asList(names), Arrays.asList(values));
    }

    public ScalarPaths(List<String> names, List<Object> values) {
        this.set(names, values);
    }

    public ScalarPaths(String scalarPath) {
        for (String entry : scalarPath.split(",")) {
            String[] keyValue = entry.split("=");
            if (keyValue.length != 2) {
                LOG.error("Problem with entry " + entry);
                continue;
            }
            names.add(keyValue[0]);
            values.add(keyValue[1]);
        }
    }


    public ScalarPaths() {
    }

    public String serialize() {
        //       JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < this.names.size(); i++) {
            String path = this.names.get(i);
            Object value = this.values.get(i);
            //           jsonArray.put(new JSONObject().put(path,value));
        }
        //       return jsonArray.getSerialized(3);
        //TODO
        return "";
    }


    public void setCounter(int i) {
        this.put("counter", i);
    }


    private void set(List<String> names, List<Object> values) {
        if (names == null) {
            names = new ArrayList<String>();
        }
        if (names.size() == 0) {
            for (int i = 0; i < values.size(); i++) {
                names.add(new Integer(i).toString());
            }
        }
        int maxLength = names.size();
        if (values.size() < maxLength) {
            LOG.debug("size of the names with " + maxLength + " is bigger than values " + values.size());
            maxLength = values.size();
        }

        for (int i = 0; i < maxLength; i++) {
            this.put(names.get(i), values.get(i));
        }
    }

    public List<String> paths() {
        return this.names;
    }

    public List<String> paths(String path) {
        return new PathPattern(path + "*").filter(this.names);
    }

    public int size() {
        return this.names.size();
    }

    public Integer size(String filter) {
        return this.paths(filter).size();
    }

    public List<Object> getValues() {
        return this.values;
    }

    public Object getValue(String name) {
//        String myPath=name;
//
//        if (!this.path.equals("")&&!this.path.equals(Path.DELIMITER)) {
//            myPath=this.path + Path.DELIMITER + name;
//        }
        Path myPath = new Path(name);
        int index = names.indexOf(myPath.directory(false));
        if (index > -1) {
            return values.get(index);
        }
        return null;
    }

    public Object get(String name) {
        Path path = new Path(name);
        String myPath = path.directory(false);
        int index = names.indexOf(myPath);
        if (index > -1) {
            return values.get(index);
        }
        return null;
    }

    public String getString(String path) {
        Object value = this.get(path);
        return ScalarConverter.toString(value);
    }

    /**
     * Removes a path
     *
     * @param path
     */
    public void rm(String path) {
        int index = names.indexOf(path);
        if (index > -1) {
            names.remove(index);
            values.remove(index);
        }
    }

    public void put(String myPath, Object value) {
        Path pathObject = new Path(myPath);
        if (value instanceof ScalarPaths) {
            List<String> names = ((ScalarPaths) value).paths();
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                Path subPath = null;//pathObject.addPath(name);
                String myName = subPath.directory().replaceAll("^" + Path.DELIMITER, "");
                int index = names.indexOf(myName);
                if (index > -1) {
                    values.set(index, ((ScalarPaths) value).get(name));
                } else {
                    this.names.add(myName);
                    this.values.add(((ScalarPaths) value).get(name));
                }
            }
        } else { //if (ShapeTypesProvider.isScalar(value)) {
            if (value instanceof String) {  // expand json
                if (((String) value).startsWith("[")) {
                    //TODO
                    //value=new JSONArray((String)value);
                } else if (((String) value).startsWith("{")) {
                    //value=new JSONObject((String)value);
                }
            }
            String myName = pathObject.directory().replaceAll("^" + Path.DELIMITER, "");
            int index = names.indexOf(myName);
            if (index > -1) {
                values.set(index, value);
            } else {
                names.add(myName);
                values.add(value);
            }
//        }
//        else {
//            LOG.warn("Problem adding non scalar " + value.getClass().getSimpleName() + " value at " + pathObject.directory());
//            return;
        }
    }


}



