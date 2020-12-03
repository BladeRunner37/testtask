package ru.bladerunner37.testtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class DepsDto {

    @JsonIgnore
    private Integer id;
    private String depCode;
    private String depJob;
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
        DepsDto depsDto = (DepsDto) o;
        return Objects.equals(depCode, depsDto.depCode) &&
                Objects.equals(depJob, depsDto.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }
}
