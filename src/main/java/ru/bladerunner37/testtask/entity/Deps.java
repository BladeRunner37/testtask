package ru.bladerunner37.testtask.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "deps", schema = "app", uniqueConstraints = @UniqueConstraint(columnNames = {"dep_code", "dep_job"}))
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
}
