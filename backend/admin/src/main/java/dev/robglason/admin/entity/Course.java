package dev.robglason.admin.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Basic
    @Column(name = "course_name", nullable = false, length = 45)
    private String courseName;

    @Basic
    @Column(name = "course_duration", nullable = false, length = 45)
    private String courseDuration;

    @Basic
    @Column(name = "course_description", nullable = false, length = 64)
    private String courseDescription;


    // relationship - each course can be taught by one instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id", nullable = false)
    private Instructor instructor;

    // relationship - can combine a group or a list of students
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_in",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> students = new HashSet<>();


    public Course() {
    }

    public Course(String courseName, String courseDuration, String courseDescription, Instructor instructor) {
        this.courseName = courseName;
        this.courseDuration = courseDuration;
        this.courseDescription = courseDescription;
        this.instructor = instructor;
    }

    // Helper Methods
    public void assignStudentToCourse(Student student) {
        this.students.add(student);
        student.getCourses().add(this);

    }

    public void removeStudentFromCourse(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

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

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDuration='" + courseDuration + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                '}';
    }


}
