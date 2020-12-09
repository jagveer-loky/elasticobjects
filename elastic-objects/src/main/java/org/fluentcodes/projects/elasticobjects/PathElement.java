package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * PathElement encapsulate eo local values of eo and its parent.
 * Created by Werner on 4.07.2020.
 */
public class PathElement {
    public static final Pattern modelPattern = Pattern.compile("^\\(([^\\)]*)\\)(.*)");
    public static final String BACK = "..";
    public static final String SAME = ".";
    public static final String MATCHER = "*";
    public static final String MATCHER_ALL = "+";
    public static final String ROOT_MODEL = "_rootmodel";
    public static final String LOGS = "_logs";
    public static final String ERROR_LEVEL = "_errorLevel";
    public static final String SERIALIZATION_TYPE = "_serializationType";
    public static final String LOG_LEVEL = "_logLevel";
    public static final String CALLS = "_calls";
    public static final String TEMPLATE = "_template";
    public static final String IN_TEMPLATE = "_";
    public static final String CONFIG = "_config";

    public static final PathElement OF_SERIALIZATION_TYPE(EO eo, JSONSerializationType serializationType) {
        return new PathElement(SERIALIZATION_TYPE, JSONSerializationType.class)
                .resolve(eo, serializationType);
    }

    public static final PathElement OF_LOG_LEVEL(EO eo, LogLevel logLevel) {
        return new PathElement(LOG_LEVEL, LogLevel.class)
                .resolve(eo, logLevel);
    }

    public static final PathElement OF_ERROR_LEVEL(EO eo, LogLevel logLevel) {
        return new PathElement(ERROR_LEVEL, LogLevel.class)
                .resolve(eo, logLevel);
    }
    public static final PathElement OF_LOGS(EO eo) {
        return new PathElement(LOGS,  List.class, String.class)
                .resolve(eo, null);
    }
    public static final PathElement OF_CALLS(EO eo) {
        return new PathElement(CALLS,  List.class)
                .resolve(eo, null);
    }

    private String[] modelsArray;
    private Models models;
    private Object value;
    private EO parent;
    private String key;
    private boolean resolved = false;
    private boolean root = false;
    private boolean changed = false;
    private boolean isJson = false;

    public PathElement(final String name, EO parentEo, Object value) {
        this(name);
        if (PathElement.ROOT_MODEL.equals(name) && parentEo.isRoot()) {
            if (!parentEo.isEmpty()) {
                throw new EoException("Not null root could not be changed");
            }
            this.parent = parentEo;
            this.value = value;
            resolved = true;
        }
        else {
            resolve(parentEo, value);
        }
    }

    public PathElement(final String name, EO parentEo, Class defaultClass) {
        this(name);
        if (!hasModelArray() && defaultClass != null) {
            this.modelsArray = new String[]{defaultClass.getSimpleName()};
            this.models = new Models(parentEo.getConfigsCache(), defaultClass);
            this.parent = parentEo;
        }
        else {
            resolve(parentEo, null);
        }
    }

    public PathElement(final Call call, EO parentEo) {
        this(Integer.valueOf(parentEo.sizeEo()).toString());
        this.modelsArray = new String[]{call.getClass().getSimpleName()};
    }

    public PathElement(final String name, String... modelsArray) {
        this(name);
        if (!hasModels()) {
            if (modelsArray.length>0) {
                this.modelsArray = modelsArray;
            }
            else {
                this.modelsArray = new String[]{Map.class.getSimpleName()};
            }
        }
    }

    public PathElement(final String name, Class... modelsArray) {
        this(name);
        if (!hasModels()) {
            if (modelsArray.length>0) {
                this.modelsArray = new String[modelsArray.length];
                int counter = 0;
                for (Class modelClass: modelsArray) {
                    this.modelsArray[counter] = modelsArray[counter].getSimpleName();
                    counter++;
                }
            }
            else {
                this.modelsArray = new String[]{Map.class.getSimpleName()};
            }
        }
    }

    public PathElement(EOConfigsCache cache, Class... modelsArray) {
        this.key = PathElement.ROOT_MODEL;
        this.models = new Models(cache, modelsArray);
        this.modelsArray = models.toString().split(",");
        this.value = models.create();
        this.root = true;
        resolved = true;
    }

    private PathElement(EO root, String classNames) {
        if (!root.isRoot()) {
            throw new EoException("No Root element");
        }
        this.key = ROOT_MODEL;
        this.models = new Models(root.getConfigsCache(), classNames.split(","));
        this.modelsArray = models.toString().split(",");
        this.value = models.create();
        this.root = true;
        resolved = true;
    }

    public PathElement(EOConfigsCache cache, Object value) {
        this.key = PathElement.ROOT_MODEL;
        if (value instanceof Class) {
            modelsArray = new String[]{((Class)value).getSimpleName()};
            this.models = new Models(cache, modelsArray);
            this.value = models.create();
            resolved = true;
            this.root = true;
            return;
        }
        if (value instanceof String) {
            if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                modelsArray = new String[]{Map.class.getSimpleName()};
                this.value = new LinkedHashMap<>();
            } else if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                modelsArray = new String[]{List.class.getSimpleName()};
                this.value = new ArrayList<>();
            } else {
                modelsArray = new String[]{String.class.getSimpleName()};
                this.value = value;
            }
        } else {
            modelsArray = new String[]{value.getClass().getSimpleName()};
            this.value = value;
        }
        this.models = new Models(cache, modelsArray);
        this.root = true;
        resolved = true;
    }

    public PathElement(final Models models) {
        this.models = models;
        this.modelsArray = models.toString().split(",");
    }

    public PathElement(final String name) {
        if (PathElement.ROOT_MODEL.equals(name)) {
            this.key = name;
            return;
        }
        final Matcher matcher = PathElement.modelPattern.matcher(name);
        String modelKey = null;
        if (matcher.find()) {
            modelKey = matcher.group(1);
            this.key = matcher.group(2);
        }
        else {
            this.key = name;
        }
        if (LOG_LEVEL.equals(key) ) {
            modelKey = LogLevel.class.getSimpleName();
        }
        else if (ERROR_LEVEL.equals(key) ) {
            modelKey = LogLevel.class.getSimpleName();
        }
        else if (CALLS.equals(key) ) {
            modelKey = List.class.getSimpleName();
        }
        else if (SERIALIZATION_TYPE.equals(key) ) {
            modelKey = JSONSerializationType.class.getSimpleName();
        }
        if (modelKey == null || modelKey.isEmpty()) {
            modelsArray = new String[]{};
            return;
        }
        modelsArray = modelKey.split(",");
    }

    protected void resolveRoot(EO rootEo, Object value) {
        if (resolved) {
            return;
        }
        root = true;
        resolved = true;
        Models valueModels = resolveFromValue(rootEo.getConfigsCache(), value);
        if (hasModelArray()) {
            this.models = new Models(rootEo.getConfigsCache(), getModelsArray());
        }
        else {
            this.models = valueModels;
        }
        createValue(valueModels, value);
    }

    protected PathElement resolve(EO parentEo, Object value) {
        if (resolved) {
            return this;
        }
        resolved = true;
        this.parent = parentEo;
        if (parentEo == null) {
            throw new EoException("Parent must be set!");
        }
        if (!((EoChild)parentEo).hasPathElement()) {
            throw new EoException("Parent must be set!");
        }
        if (parentEo.isList() && isParentSet()) {
            key = Integer.valueOf(parentEo.size()).toString();
        }
        Models valueModels = resolveFromValue(parentEo.getConfigsCache(), value);
        Models childModels = ((EoChild)parentEo).getChildModels(this);
        if (childModels != null) {
            this.models = childModels;
            if (hasModelArray()) {
                Models arrayModels = new Models(parentEo.getConfigsCache(), getModelsArray());
                if (childModels.toString().contains(arrayModels.toString())) {
                    this.models = arrayModels;
                }
                else {
                    throw new EoException("Problem with incompatible directives from child " + childModels.toString() + " and field " + arrayModels.toString() );
                }
            }
        }
        else if (hasModelArray()) {
            this.models = new Models(parentEo.getConfigsCache(), getModelsArray());
        }
        else {
            this.models = valueModels;
            if (!valueModels.isCreate() && !valueModels.isScalar()) {
                this.models = new Models(parent.getConfigsCache(), Map.class);
            }
        }
        createValue(valueModels, value);
        if (this.value instanceof Call) {
            if (!parentEo.getPath().isCallDirectory()) {
                ((Call)this.value).initTargetPath(parentEo.getPath().add(key));
            }
            EO calls = ((EoChild)parentEo).getCallsEo();
            this.key = Integer.valueOf(calls.size()).toString();
            this.parent = calls;
        }
        return this;
    }

    private void createValue(Models valueModels, Object value) {
        this.value = this.models.create();
        if (value != null && this.value == null) {
            this.value = this.models.transform(value);
        }
        if (valueModels == null) {
            return;
        }
        if (models.getModelClass() == String.class && isJson) {
            return;
        }
        if (models.isScalar() && !valueModels.isScalar()) {
            throw new EoException("Problem setting non scalar value ("
                    + valueModels.getModel().getNaturalId() + ") for field name '"
                    + key + "'. Expected is "
                    + models.getModel().getNaturalId() + "!");
        }
        else if (!models.isScalar() && valueModels.isScalar()) {
            if (models.isObject() && valueModels.getModelClass() == Long.class) {
                return;
            }
            throw new EoException("Problem setting scalar value ("
                    + valueModels.getModel().getNaturalId() + ") for field name '"
                    + key + "'. Expected is "
                    + models.getModel().getNaturalId() + "!");
        }
    }

    private Models resolveFromValue(EOConfigsCache cache, Object value) {
        if (value == null) {
            return new Models(cache, Map.class.getSimpleName());
        }
        if (value instanceof String) {
            if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                isJson = true;
                return new Models(cache, List.class);
            } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                isJson = true;
                return new Models(cache, Map.class);
            }
            else {
                return new Models(cache, String.class);
            }
        }
        /*else if (value.getClass().isEnum()) {
            modelsArray = new String[]{String.class.getSimpleName()};
            this.value = value;
        }*/
        else {
            return new Models(cache, value.getClass());
        }
    }

    public EO buildEo() {
        if (ROOT_MODEL.equals(key)) {
            if (!parent.isRoot()) {
                throw new EoException("No Root element");
            }
            ((EoChild)parent).setRootModels((String)value);
            return parent;
        }
        return new EoChild(this);
    }

    public EO getParent() {
        return parent;
    }
    public boolean hasParent() {
        return parent != null;
    }

    protected void addToParent(EoChild eo) {
        ((EoChild)parent).addEo(eo);
    }
    public Object getValue() {
        return value;
    }
    public boolean hasObject() {
        return value != null;
    }
    protected boolean setValue(Object newValue) {
        /*if (this.object != null && this.object == value) {
            return;  // the same object
        }
        if (object != null && value != null) {
            //throw new eoException("Not allowed to set a null source");
            if (this.object.hashCode() == value.hashCode()) {
                return;
            }
            if (!(object instanceof LogLevel)) {
                info("Existing Object is overwritten! " + getPath() + ".");
            }
        }*/
        if (isScalar()) {
            this.value = models.transform(newValue);
        }
        else {
            this.value = newValue;
        }

        if (this.value != null) {
            changed = true;
        }
        if (hasParent() && isParentSet()) {
            ((EoChild)parent).setValueChecked(key, this.value);
        }
        return true;
    }
    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     * @param key the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String key) {
        return key == null || key.isEmpty() || key.startsWith("_");
    }

    public boolean isParentNotSet() {
        return key == null || key.isEmpty() || key.startsWith("_");
    }

    public static boolean isParentSet(final String key) {
        return !isParentNotSet(key);
    }

    public boolean isParentSet() {
        return !isParentNotSet();
    }

    public boolean isChanged() {
        return changed;
    }

    public Object create() {
        if (isCreate()) {
            return this.models.create();
        }
        return null;
    }

    public boolean isRoot() {
        return root;
    }

    public boolean isCreate() {
        return this.models.isCreate();
    }

    public boolean isScalar() {
        return this.models.isScalar()|| this.models.isEnum();
    }

    public boolean isCall() {
        return this.models.isCall();
    }

    protected boolean isCallDirectory() {
        return CALLS.equals(this.key);
    }

    public boolean isBack() {
        return BACK.equals(key);
    }

    public boolean isSame() {
        return SAME.equals(key);
    }

    public boolean isFilter() {
        return key.contains(MATCHER) || key.contains(MATCHER_ALL);
    }

    public String[] getModelsArray() {
        return modelsArray;
    }

    public Models getModels() {
        return this.models;
    }

    protected void setRootModels(final EO root, final String models) {
        if (!isRoot()) {
            throw new EoException("No Root element, no models could be redefined");
        }
        this.models = new Models(root.getConfigsCache(), models.split(","));
        this.modelsArray = models.toString().split(",");
        this.value = this.models.create();
    }

    public ModelConfigInterface getModel() {
        return this.models.getModel();
    }

    public String getKey() {
        return key;
    }
    public boolean hasKey() {
        return key != null && !key.isEmpty();
    }

    public boolean hasModels() {
        return models != null && !models.isEmpty();
    }
    public boolean hasModel() {
        return this.models.hasModel();
    }

    public boolean hasModelArray() {
        return modelsArray != null && modelsArray.length>0;
    }

    public boolean isRootModel() {
        return ROOT_MODEL.equals(getKey());
    }

    @Override
    public String toString(){
        if (!hasModelArray()) {
            return key;
        }
        return "(" + String.join(",", modelsArray) + ")" + key;
    }
}
