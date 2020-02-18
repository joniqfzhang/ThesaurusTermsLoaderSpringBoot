package au.gov.dva.mailroom.thesaurus.loader.model;


import java.util.Objects;
import java.util.UUID;

public class Student {

    private final UUID studentId;

   // @NotBlank
    private final String firstName;

    //@NotBlank
    private final String lastName;

   // @NotBlank
    private final String email;

    //@NotNull
    private final Gender gender;

    public Student(/*@JsonProperty("studentId") */UUID studentId,
                   /*@JsonProperty("firstName")*/ String firstName,
                  /* @JsonProperty("lastName")*/ String lastName,
                   /*@JsonProperty("email")*/ String email,
                   /*@JsonProperty("gender")*/ Gender gender) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(email, student.email) &&
                gender == student.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, email, gender);
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }

    enum Gender {
        MALE, FEMALE
    }
}
