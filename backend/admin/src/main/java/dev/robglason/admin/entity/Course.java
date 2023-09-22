package dev.robglason.admin.entity;

import java.util.Objects;

public class Course {

    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!courseId.equals(course.courseId)) return false;
        if (!Objects.equals(courseName, course.courseName)) return false;
        if (!Objects.equals(courseDuration, course.courseDuration))
            return false;
        return Objects.equals(courseDescription, course.courseDescription);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (courseDuration != null ? courseDuration.hashCode() : 0);
        result = 31 * result + (courseDescription != null ? courseDescription.hashCode() : 0);
        return result;
    }
}
