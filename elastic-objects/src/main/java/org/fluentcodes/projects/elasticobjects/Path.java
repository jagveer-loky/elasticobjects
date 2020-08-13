package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

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
        List<PathElement> entries = new ArrayList<>();
        if (!path.isEmpty()) {
            entries.addAll(path.getEntries());
        }
        absolute = path.isAbsolute();
        for (String pathElement: pathEntries) {
            this.addPaths(pathElement.split(Path.DELIMITER), entries);
        }
        this.entries = (PathElement[])entries.toArray();
    }

    public Path(PathElement... pathElements) {
        this.entries = pathElements;
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

    public EO moveTo (EO eo) {
        EO target = eo;
        if (isAbsolute()) {
            target = eo.getRoot();
        }
        for (PathElement element: entries) {
            try {
                if (element.isBack()) {
                    target = target.getParent();
                }
                else if (element.isSame()) { }
                else {
                    target = target.getEo(element);
                }
            }
            catch (EoException e) {
                throw new EoException("Path " + this.toString() + " undefined: " + e.getMessage());
            }
        }
        return target;
    }

    public EO create (final EO eo, final Object value) {
        EO target = eo;
        if (isEmpty()) {
            target.mapObject(value);
        }
        if (isAbsolute()) {
            target = eo.getRoot();
        }
        int counter = 0;
        for (PathElement element: entries) {
            counter++;
            try {
                if (element.isBack()) {
                    target = target.getParent();
                }
                else if (element.isSame()) {
                    if (counter==entries.length) {
                        target.mapObject(value);
                    }
                }
                else {
                    if (counter==entries.length) {
                        target = target.set(element, value);
                    }
                    else {
                        target = target.set(element);
                    }
                }
            }
            catch (EoException e) {
                throw new EoException("Path " + this.toString() + " undefined: " + e.getMessage());
            }
        }
        return target;
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
            return new Path(Path.DELIMITER);
        }
        return new Path(Arrays.copyOfRange(this.entries, 0, entries.length-1));
    }

    public String getParentKey() {
        if (size() < 1) {
            throw new EoException("No entry in path");
        }
        return entries[entries.length-1].getKey();
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
