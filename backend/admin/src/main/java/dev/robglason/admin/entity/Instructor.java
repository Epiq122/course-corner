package dev.robglason.admin.entity;

import java.util.Objects;

public class Instructor {

    private Long instructorId;
    private String firstName;
    private String lastName;
    private String summary;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instructor that = (Instructor) o;

        if (!instructorId.equals(that.instructorId)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        return Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        int result = instructorId.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}
