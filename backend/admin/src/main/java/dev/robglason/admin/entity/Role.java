package dev.robglason.admin.entity;

import java.util.Objects;

public class Role {

    private Long roleId;
    private String name;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!roleId.equals(role.roleId)) return false;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
