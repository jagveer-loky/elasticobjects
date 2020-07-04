package org.fluentcodes.projects.elasticobjects.paths;

import org.fluentcodes.projects.elasticobjects.EoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Path creates from a string a special of elements splitted by te delimiter
 * Created by Werner on 19.06.2015.
 */
public class PathElement {
    public static final String DELIMITER = "/";
    public static final String BACK = "..";
    public static final String SAME = ".";
    public static final String MATCHER = "*";
    public static final String MATCHER_ALL = "+";
    private final ArrayList<String> entries;
    private boolean parent = false;

    public PathElement(final PathElement path) {
        this.entries = new ArrayList<>();
        entries.addAll(path.getEntries());
    }

    public PathElement(final String path) {
        this.entries = new ArrayList<>();
        this.addPaths(path);
    }

    private PathElement(final List<String> paths) {
        this.entries = new ArrayList<>();
        this.entries.addAll(paths);
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isFilterNothing() {
        for (String path : this.entries) {
            if (path.equals(MATCHER_ALL)) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<String> getEntries() {
        return this.entries;
    }

    public String first() {
        if (entries == null) {
            return null;
        }
        if (entries.size() == 0) {
            return null;
        }
        return this.entries.get(0);
    }

    public String last() {
        if (entries.size() == 0) {
            return null;
        }
        return this.entries.get(entries.size() - 1);
    }

    public boolean isEmpty() {
        return entries == null || entries.size() == 0;
    }

    /**
     * Simply adds a string to the path list.
     *
     * @param path the string to be added
     * @return this for fluent interface
     */
    public PathElement addPath(final String path) {
        if (path == null || path.equals("")) {
            return this;
        }
        PathElement result = new PathElement(this);
        result.addPaths(path);
        return this;
    }

    /**
     * Simply adds a string to the path list.
     *
     * @param path the string to be added
     * @return this for fluent interface
     */
    public PathElement prependPath(final String path) {
        if (path == null || path.equals("")) {
            return this;
        }
        this.entries.add(0, path);
        return this;
    }

    public void addPaths(final String toAdd) {
        if (toAdd == null || toAdd.isEmpty()) {
            return;
        }
        if (toAdd.startsWith(DELIMITER)) {
            this.entries.clear();
        }
        String[] paths = toAdd.split(DELIMITER);
        for (String path : paths) {
            if (path == null || path.isEmpty() || path.equals(SAME) || path.matches("^\\s+$")) {
                continue;
            }
            if (path.equals(BACK)) {
                if (entries.isEmpty()) {
                    this.entries.add(path);
                    continue;
                }
                if (this.last().equals(PathElement.BACK)) {
                    this.entries.add(path);
                    continue;
                }
                this.entries.remove(this.entries.size() - 1);
                continue;
            }
            this.entries.add(path);
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
        return this.entries.get(0);
    }

    public boolean hasFirstEntry() {
        return !isEmpty();
    }

    public void removeFirstEntry()  {
        if (this.entries.size() == 0) {
            throw new EoException("Problem: Path has no entry any more.");
        }
        this.entries.remove(0);
    }

    public PathElement removeFirst()  {
        if (this.entries.size() == 0) {
            throw new EoException("Problem: Path has no entry any more.");
        }
        PathElement path = new PathElement(this);
        path.removeFirstEntry();
        return path;
    }

    public PathElement getChildPath() {
        if (size() < 1) {
            return new PathElement(PathElement.DELIMITER);
        }
        return new PathElement(this.entries.subList(1, entries.size()));
    }

    public int size() {
        return this.entries.size();
    }

    public String get(int i) {
        return this.entries.get(i);
    }

    @Override
    public String toString() {
        return directory();
    }

    public String directory() {
        if (entries == null || entries.size() == 0) {
            return DELIMITER;
        }
        if (entries.size() == 1) {
            return DELIMITER + child();
        }
        return parent() + DELIMITER + child();
    }

    public String directory(boolean hasLeadingDelimiter) {
        if (hasLeadingDelimiter) {
            return this.directory();
        }
        return this.directory().replaceAll("^" + DELIMITER, "");
    }

    public String parent() {
        if (entries == null || entries.size() == 0) {
            return null;
        }

        if (entries.size() == 1) {
            return "/";
        }
        StringBuffer pathDirectory = new StringBuffer("");
        for (int i = 0; i < entries.size() - 1; i++) {
            String path = this.entries.get(i);
            pathDirectory.append(DELIMITER + path);
        }
        return pathDirectory.toString();
    }


    public String child() {
        if (isEmpty()) {
            return null;
        }
        return entries.get(entries.size() - 1);
    }

    public boolean hasChild() {
        return !isEmpty();
    }

    public boolean hasPlaceHolder() {
        String item = this.directory();
        return item.matches(".*[\\(\\{\\*\\[]+.*");
    }

    public boolean hasMatcher() {
        String item = this.directory();
        return item.contains(MATCHER);
    }

}
