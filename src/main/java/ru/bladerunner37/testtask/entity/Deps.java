package ru.bladerunner37.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "deps", schema = "app", uniqueConstraints = @UniqueConstraint(columnNames = {"dep_code", "dep_job"}))
public class Deps implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dep_code")
    @NotEmpty(message = "depCode con not be empty")
    @Size(max = 20, message = "depCode must be maximum 20 characters")
    private String depCode;

    @Column(name = "dep_job")
    @NotEmpty(message = "depCode con not be empty")
    @Size(max = 100, message = "depJob must be maximum 100 characters")
    private String depJob;

    @Column(name = "description")
    @Size(max = 255, message = "description must be maximum 255 characters")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deps deps = (Deps) o;
        return id.equals(deps.id) &&
                Objects.equals(depCode, deps.depCode) &&
                Objects.equals(depJob, deps.depJob) &&
                Objects.equals(description, deps.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depCode, depJob, description);
    }

    @Override
    public String toString() {
        return "Deps{" +
                "id=" + id +
                ", depCode='" + depCode + '\'' +
                ", depJob='" + depJob + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
