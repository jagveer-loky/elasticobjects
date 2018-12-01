package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 12.10.2016.
 */
public class RolePermissions {

    public static final String GUEST = "guest";
    public static final String ANONYM = "anonym";
    public static final String SUPERADMIN = "superadmin";
    private String execute;
    private String create;
    private String write;
    private String read;
    private String delete;
    private List<String> deleteList;
    private List<String> createList;
    private List<String> writeList;
    private List<String> executeList;
    private List<String> readList;

    public RolePermissions() {
    }

    public RolePermissions(RolePermissions rolePermissions) {
        this.read = rolePermissions.getRead();
        this.write = rolePermissions.getWrite();
        this.create = rolePermissions.getCreate();
        this.delete = rolePermissions.getDelete();
        this.execute = rolePermissions.getExecute();
        resolve();
    }

    public RolePermissions(Map rolePermissions) {
        if (rolePermissions == null) {
            return;
        }
        this.read = (String) rolePermissions.get(EO_STATIC.F_READ);
        this.write = (String) rolePermissions.get(EO_STATIC.F_WRITE);
        this.create = (String) rolePermissions.get(EO_STATIC.F_CREATE);
        this.delete = (String) rolePermissions.get(EO_STATIC.F_DELETE);
        this.execute = (String) rolePermissions.get(EO_STATIC.F_EXECUTE);
        resolve();
    }

    private List<String> createRoleList(String input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(input.split(","));
    }

    private void resolve() {
        if (readList != null) {
            return;
        }
        this.readList = createRoleList(read);
        this.writeList = createRoleList(write);
        this.createList = createRoleList(create);
        this.deleteList = createRoleList(delete);
        this.executeList = createRoleList(execute);
    }

    public void add(RolePermissions permissions) {
        resolve();
        add(this.readList, permissions.getReadList());
        this.read = String.join(",", this.readList);
        add(this.writeList, permissions.getWriteList());
        this.write = String.join(",", this.writeList);
        add(this.createList, permissions.getCreateList());
        this.create = String.join(",", this.createList);
        add(this.executeList, permissions.getExecuteList());
        this.execute = String.join(",", this.executeList);
        add(this.deleteList, permissions.getDeleteList());
        this.delete = String.join(",", this.deleteList);
    }

    private void add(List<String> permissions, List<String> added) {
        if (added == null) {
            return;
        }
        if (added.isEmpty()) {
            return;
        }
        for (String toAdd : added) {
            if (permissions.contains(toAdd)) {
                continue;
            }
            permissions.add(toAdd);
        }
    }

    public boolean hasPermissions(Permissions requiredPermission, List<String> roles) throws Exception {
        if (roles == null) {
            return true;
        }
        if (roles.isEmpty()) {
            return false;
        }
        Permissions permission = getPermissions(roles);
        return permission.value() >= requiredPermission.value();
    }

    public Permissions getPermissions(List<String> roles) {
        resolve();
        if (roles == null) {
            return Permissions.EXECUTE;
        }
        if (roles.contains(SUPERADMIN)) {
            return Permissions.EXECUTE;
        }
        if (roles.size() == 0) {
            return Permissions.NOTHING;
        }
        if (contains(executeList, roles)) {
            return Permissions.EXECUTE;
        }
        if (contains(deleteList, roles)) {
            return Permissions.DELETE;
        }
        if (contains(createList, roles)) {
            return Permissions.CREATE;
        }
        if (contains(writeList, roles)) {
            return Permissions.WRITE;
        }
        if (contains(readList, roles)) {
            return Permissions.READ;
        }
        return Permissions.NOTHING;
    }

    private boolean contains(List<String> permissions, List<String> roles) {
        for (String permissionRole : permissions) {
            if (roles.contains(permissionRole)) {
                return true;
            }
        }
        return false;
    }

    public String getExecute() {
        return execute;
    }

    public void setExecute(final String execute) {
        this.execute = execute;
    }

    protected List<String> getExecuteList() {
        resolve();
        return executeList;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(final String create) {
        this.create = create;
    }

    protected List<String> getCreateList() {
        resolve();
        return createList;
    }

    public String getWrite() {
        return write;
    }

    public void setWrite(final String write) {
        this.write = write;
    }

    protected List<String> getWriteList() {
        resolve();
        return writeList;
    }

    public String getRead() {
        return read;
    }

    public void setRead(final String read) {
        this.read = read;
    }

    protected List<String> getReadList() {
        resolve();
        return readList;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(final String delete) {
        this.delete = delete;
    }

    protected List<String> getDeleteList() {
        resolve();
        return deleteList;
    }
}
