package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

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
        List<String> tempPath = new ArrayList<>();
        addPathElements(tempPath, pathEntries);
        int size = tempPath.size();
        this.entries = tempPath.stream()
                .map(PathElement::new)
                .toArray(PathElement[]::new);
    }

    public static final String ofs(String... pathEntries) {
        return new Path(pathEntries).directory(true);
    }

    private void addPathElements(final List<String>tempPath, final String[] addArray) {
        for (String pathEntry : addArray) {
            if (pathEntry == null || pathEntry.isEmpty() || PathElement.SAME.equals(pathEntry) || pathEntry.matches("\\s*")) {
                continue;
            }
            if (pathEntry.contains(DELIMITER)) {
                if (pathEntry.startsWith(Path.DELIMITER)) {
                    tempPath.clear();
                    absolute = true;
                }
                addPathElements(tempPath, pathEntry.split(Path.DELIMITER));
                continue;
            }
            if (pathEntry.equals(PathElement.BACK)) {
                if (tempPath.size()>0 && !PathElement.BACK.equals(tempPath.get(tempPath.size()-1))) {
                        tempPath.remove(tempPath.size() - 1);
                        continue;
                }
            }
            tempPath.add(pathEntry);
        }
    }

    protected Path setAbsolute(Boolean absolute) {
        this.absolute = absolute;
        return this;
    }

    public boolean isAbsolute() {
        return absolute;
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
                    parent.mapObject(value);
                }
            }
            else if (element.isRootModel()) {
                throw new EoException("Could not change model with a set");
            }
            else {
                if (counter==entries.length) {
                    if (((EoChild)parent).hasEo(element)) {
                        parent = parent.getEo(element);
                        parent.mapObject(value);
                        //element.resolve(parent, value);
                        //((EoChild)parent).setPathElement(element);
                    }
                    else {
                        if (parent.isScalar()) {
                            throw new EoException("Could not create a field value with '" + element.getKey() + "' for a scalar (" + parent.getModel().toString() + ") parent on path '" + parent.getPathAsString() + "'");
                        }
                        parent = parent.createChild(element, value);
                    }
                }
                else {
                    if (((EoChild)parent).hasEo(element)) {
                        parent = parent.getEo(element);
                        //element.resolve(parent, null);
                        //((EoChild)parent).setPathElement(element);
                    }
                    else {
                        parent = parent.createChild(element);
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
        if (this.entries.length == 0) {
            return new Path(Path.DELIMITER);
        }
        try {
            return new Path(this.absolute, Arrays.copyOfRange(this.entries, 0, entries.length - 1));
        }
        catch (IllegalArgumentException e) {
            throw new EoException(e);
        }
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
        if (element.hasModelArray()) {
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
