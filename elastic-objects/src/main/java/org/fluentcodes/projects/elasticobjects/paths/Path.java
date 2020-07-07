package org.fluentcodes.projects.elasticobjects.paths;

import org.fluentcodes.projects.elasticobjects.EoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Path creates from a string a special of elements splitted by te delimiter
 * Created by Werner on 19.06.2015.
 */
public class Path {
    public static final String DELIMITER = "/";
    private final ArrayList<PathElement> entries;
    private boolean parent = false;

    public Path(final Path path) {
        this.entries = new ArrayList<>();
        entries.addAll(path.getEntries());
    }

    public Path(final String path) {
        this.entries = new ArrayList<>();
        this.addPaths(path);
    }

    private Path(final List<PathElement> paths) {
        this.entries = new ArrayList<>();
        this.entries.addAll(paths);
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isFilterNothing() {
        for (PathElement path : this.entries) {
            if (path.getPathElement().equals(PathElement.MATCHER_ALL)) {
                return true;
            }
        }
        return false;
    }

    protected ArrayList<PathElement> getEntries() {
        return this.entries;
    }

    public String first() {
        if (entries == null) {
            return null;
        }
        if (entries.size() == 0) {
            return null;
        }
        return this.entries.get(0).getPathElement();
    }

    public String last() {
        if (entries.size() == 0) {
            return null;
        }
        return this.entries.get(entries.size() - 1).getPathElement();
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
    public Path addPath(final String path) {
        if (path == null || path.equals("")) {
            return this;
        }
        Path result = new Path(this);
        result.addPaths(path);
        return this;
    }

    /**
     * Simply adds a string to the path list.
     *
     * @param path the string to be added
     * @return this for fluent interface
     */
    public Path prependPath(final String path) {
        if (path == null || path.equals("")) {
            return this;
        }
        this.entries.add(0, new PathElement(path));
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
            if (path == null || path.isEmpty() || path.equals(PathElement.SAME) || path.matches("^\\s+$")) {
                continue;
            }
            if (path.equals(PathElement.BACK)) {
                if (entries.isEmpty()) {
                    this.entries.add(new PathElement(path));
                    continue;
                }
                if (this.last().equals(PathElement.BACK)) {
                    this.entries.add(new PathElement(path));
                    continue;
                }
                this.entries.remove(this.entries.size() - 1);
                continue;
            }
            this.entries.add(new PathElement(path));
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
        return getFirstPathElement().getPathElement();
    }

    public PathElement getFirstPathElement() {
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

    public Path removeFirst()  {
        if (this.entries.size() == 0) {
            throw new EoException("Problem: Path has no entry any more.");
        }
        Path path = new Path(this);
        path.removeFirstEntry();
        return path;
    }

    public Path getChildPath() {
        if (size() < 1) {
            return new Path(Path.DELIMITER);
        }
        return new Path(this.entries.subList(1, entries.size()));
    }

    public int size() {
        return this.entries.size();
    }

    public String get(int i) {
        return this.entries.get(i).getPathElement();
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
            String path = this.entries.get(i).getPathElement();
            pathDirectory.append(DELIMITER + path);
        }
        return pathDirectory.toString();
    }


    public String child() {
        if (isEmpty()) {
            return null;
        }
        return entries.get(entries.size() - 1).getPathElement();
    }

    public boolean hasChild() {
        return !isEmpty() && this.entries.size()>1;
    }

    public boolean hasModel() {
        if (isEmpty()) {
            return false;
        }
        return getFirstPathElement().hasModels();
    }

    public List<String> getModels() {
        if (isEmpty()) {
            return new ArrayList<>();
        }
        return getFirstPathElement().getModels();
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
