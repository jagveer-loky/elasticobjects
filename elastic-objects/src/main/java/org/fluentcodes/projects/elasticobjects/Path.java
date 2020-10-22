package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Path creates from a string a special of elements splitted by te delimiter
 * Created by Werner on 19.06.2015.
 */
public class Path {
    public static final String DELIMITER = "/";
    private final PathElement[] entries;
    private boolean absolute = false;

    public Path(Path path, String... pathEntries) {
        List<PathElement> entryList = new ArrayList<>();
        if (!path.isEmpty()) {
            entryList.addAll(path.getEntries());
        }
        absolute = path.isAbsolute();
        for (String pathElement: pathEntries) {
            this.addPaths(pathElement.split(Path.DELIMITER), entryList);
        }
        this.entries = new PathElement[entryList.size()];
        for (int i=0; i<entryList.size(); i++) {
            this.entries[i] = entryList.get(i);
        }
    }

    public Path(PathElement... pathElements) {
        this(false, pathElements);
    }

    public Path(boolean isAbsolute, PathElement... pathElements) {
        this.entries = pathElements;
        this.absolute = isAbsolute;
    }

    public Path(String... pathEntries) {
        if (pathEntries == null || pathEntries.length==0 || pathEntries[0] == null) {
            this.entries = new PathElement[0];
            return;
        }
        boolean hasDelimiter = false;
        if (pathEntries[0].startsWith(DELIMITER)) {
            absolute = true;
            pathEntries[0] = pathEntries[0].replaceAll("^" + DELIMITER + "+", "");
        }
        if (pathEntries.length == 1 && !pathEntries[0].contains(DELIMITER)) {
            if (pathEntries[0] == null || pathEntries[0].isEmpty() || PathElement.SAME.equals(pathEntries[0]) || pathEntries[0].matches("\\s*")) {
                this.entries = new PathElement[0];
                return;
            }
            this.entries = new PathElement[] {new PathElement(pathEntries[0])};
            return;
        }

        if (pathEntries.length == 1) {
            pathEntries = pathEntries[0].split(DELIMITER);
        }

        StringBuilder simplePath = new StringBuilder();
        for (String pathEntry : pathEntries) {
            if (pathEntry == null || pathEntry.isEmpty() || PathElement.SAME.equals(pathEntry) || pathEntry.matches("\\s*")) {
                continue;
            }
            if (pathEntry.contains(DELIMITER)||pathEntry.contains(PathElement.BACK)) {
                hasDelimiter = true;
                break;
            }
            simplePath.append(pathEntry);
            simplePath.append(DELIMITER);
        }
    if (!hasDelimiter){
        pathEntries = simplePath.toString().replaceAll(DELIMITER + "$","").split(DELIMITER);
        PathElement[] tempEntries = new PathElement[pathEntries.length];
            int size = 0;
            for (int i=0; i< pathEntries.length; i++) {
                String entry = pathEntries[i];
                if (entry == null) {
                    continue;
                }
                if (PathElement.SAME.equals(entry)) {
                    continue;
                }
                tempEntries[size] = new PathElement(pathEntries[i]);
                size++;
            }
            this.entries = new PathElement[size];
            if (size==0) {
                return;
            }
            for (int i=0; i<pathEntries.length;i++) {
                entries[i] = tempEntries[i];
            }
            return;
        }
        List<PathElement> entryList = new ArrayList<>();
        for (String pathElement: pathEntries) {
            if (pathElement == null || pathElement.isEmpty()) {
                continue;
            }
            this.addPaths(pathElement.split(Path.DELIMITER), entryList);
        }
        this.entries = new PathElement[entryList.size()];
        for (int i= 0; i< entryList.size(); i++) {
            this.entries[i] = entryList.get(i);
        }
    }

    public static final String ofs(String... pathEntries) {
        return new Path(pathEntries).directory(true);
    }

    protected Path setAbsolute(Boolean absolute) {
        this.absolute = absolute;
        return this;
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public boolean isRootModel() {
        if (isEmpty() || entries.length>1 || !entries[0].isRootModel()) {
            return false;
        }
        return true;
    }
    public boolean isFilterNothing() {
        for (PathElement path : this.entries) {
            if (path.getKey().equals(PathElement.MATCHER_ALL)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFilter() {
        return getParent().isFilter();
    }

    public EO moveToParent (final EO eo) {
        Path parentPath = this.getParentPath();
        return parentPath.moveTo(eo);
    }

    public EO moveTo (EO eo) {
        EO target = eo;
        if (isAbsolute()) {
            target = eo.getRoot();
        }
        for (PathElement element: entries) {
            if (element.isBack()) {
                target = target.getParent();
            }
            else if (element.isSame()) { }
            else {
                if (((EoChild)target).hasEo(element)) {
                    target = target.getEo(element);
                }
                else {
                    throw new EoException("Could not move to path '" + this.toString() + "' because key '" + element.toString() + "' does not exist on '" + target.getPathAsString() + "'." );
                }
            }
        }
        return target;
    }

    public EO createParent (final EO eo, Models models) {
        Path parentPath = this.getParentPath();
        return parentPath.create(eo, models.create());
    }
    public EO create (EO parent) {
        return create(parent, null);
    }

    public EO create (EO parent, final Object value) {
        EO target = parent;
        if (isEmpty()) {
            target.mapObject(value);
        }
        if (isAbsolute()) {
            parent = parent.getRoot();
        }
        int counter = 0;
        for (PathElement element: entries) {
            counter++;
            if (element.isBack()) {
                parent = parent.getParent();
            }
            else if (element.isSame()) {
                if (counter==entries.length) {
                    if (parent.isRoot() && element.hasModelArray()) {
                        ((EoRoot)parent).setRootModels(String.join(",", element.getModelsArray()));
                    }
                    parent.mapObject(value);
                }
            }
            else {
                if (counter==entries.length) {
                    if (element.isRootModel()) {
                        ((EoChild)parent).setRootModels((String)value);
                    }
                    else if (((EoChild)parent).hasEo(element)) {
                        parent = parent.getEo(element);
                        parent.mapObject(value);
                        //element.resolve(parent, value);
                        //((EoChild)parent).setPathElement(element);
                    }
                    else {
                        if (parent.isScalar()) {
                            throw new EoException("Could not create a field value with '" + element.getKey() + "' for a scalar (" + parent.getModel().toString() + ") parent on path '" + parent.getPathAsString() + "'");
                        }
                        element.resolve(parent, value);
                        parent = new EoChild(element);
                        if (!element.isScalar()){
                            parent.mapObject(value);
                        }
                    }
                }
                else {
                    if (((EoChild)parent).hasEo(element)) {
                        parent = parent.getEo(element);
                        //element.resolve(parent, null);
                        //((EoChild)parent).setPathElement(element);
                    }
                    else {
                        element.resolve(parent, null);
                        parent = new EoChild(element);
                    }
                }
            }

        }
        return parent;
    }

    protected List<PathElement> getEntries() {
        return Arrays.asList(this.entries);
    }

    public String first() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[0].getKey();
    }

    public String last() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[entries.length - 1].getKey();
    }

    public boolean isEmpty() {
        return entries == null || entries.length == 0 || PathElement.SAME.equals(entries[0]);
    }

    private void addPaths(final String[] pathElements, List<PathElement> entries) {
        if (pathElements[0].isEmpty())  {
            entries.clear();
            absolute = true;
        }
        for (String path : pathElements) {
            if (path == null || path.isEmpty() || PathElement.SAME.equals(path) || path.matches("^\\s+$")) {
                continue;
            }
            if (path.equals(PathElement.BACK)) {
                if (entries.isEmpty()) {
                    entries.add(new PathElement(path)); // first ".." on empty path.
                    continue;
                }
                if (entries.get(entries.size()-1).equals(PathElement.BACK)) { // several subsequent ".."
                    entries.add(new PathElement(path));
                    continue;
                }
                entries.remove(entries.size() - 1);
                continue;
            }
            entries.add(new PathElement(path));
        }
    }


    /**
     * Gets the first entry.
     *
     * @return returns the first entry of the path.
     */
    public String getFirstEntry() {
        if (isEmpty()) {
            return null;
        }
        return getFirstPathElement().getKey();
    }

    public Path getFirstPath() {
        if (isEmpty()) {
            return new Path("");
        }
        return new Path(getFirstPathElement());
    }

    public PathElement getFirstPathElement() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[0];
    }

    public boolean hasFirstEntry() {
        return !isEmpty();
    }

    public Path createChildPath() {
        if (size() < 1) {
            return new Path("").setAbsolute(this.absolute);
        }
        return new Path(Arrays.copyOfRange(this.entries, 1, entries.length)).setAbsolute(this.absolute);
    }

    public Path getParentPath() {
        if (size() < 1) {
            Path parent =  new Path(Path.DELIMITER);
        }
        return new Path(this.absolute, Arrays.copyOfRange(this.entries, 0, entries.length-1));
    }

    public String getParentKey() {
        if (size() < 1) {
            throw new EoException("No entry in path");
        }
        return getParent().getKey();
    }

    public PathElement getParent() {
        if (size() < 1) {
            throw new EoException("No entry in path");
        }
        return entries[entries.length-1];
    }

    public Path add(String... keys) {
        try {
            if (isEmpty()) {
                return new Path(keys);
            }
            else {
                return new Path(this, keys);
            }
        }
        catch (Exception e) {
            throw new EoInternalException(e);
        }
    }

    protected boolean isCallDirectory() {
        if (isEmpty()) {
            return false;
        }
        if (size()==1 && entries[0].isCallDirectory()) {
            return true;
        }
        return false;
    }


    public int size() {
        return this.entries.length;
    }

    public String get(int i) {
        return this.entries[i].getKey();
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        for (PathElement element: entries) {
            result.append(DELIMITER);
            result.append(element.toString());
        }
        if (isAbsolute()) {
            return result.toString();
        }
        return result.toString().replaceAll("^/", "");
    }

    public String directory() {
        return directory(false);
    }

    public String directory(boolean includeModels) {
        if (includeModels) {
            return toString();
        }
        StringBuffer result = new StringBuffer();
        for (PathElement element: entries) {
            result.append(DELIMITER);
            result.append(element.getKey());
        }
        if (isAbsolute()) {
            if (isEmpty()) {
                return DELIMITER;
            }
            return result.toString();
        }
        return result.toString().replaceAll("^/", "");
    }

    public String parent() {
        return getParentPath().directory(true);
    }


    public String child() {
        if (isEmpty()) {
            return null;
        }
        PathElement element = entries[entries.length - 1];
        if (element.hasModels()) {
            return "(" + String.join(",",element.getModelsArray()) + ")" + element.getKey();
        }
        return element.getKey();
    }

    public boolean hasChild() {
        return !isEmpty() && this.entries.length>1;
    }

    public boolean hasModel() {
        if (isEmpty()) {
            return false;
        }
        return getFirstPathElement().hasModelArray();
    }

    public String[] getModels() {
        if (isEmpty()) {
            return new String[]{};
        }
        return getFirstPathElement().getModelsArray();
    }

    public boolean hasPlaceHolder() {
        String item = this.directory();
        return item.matches(".*[\\(\\{\\*\\[]+.*");
    }

    public boolean hasMatcher() {
        String item = this.directory();
        return item.contains(PathElement.MATCHER);
    }

}
