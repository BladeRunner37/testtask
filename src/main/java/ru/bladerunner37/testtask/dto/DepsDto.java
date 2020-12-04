package ru.bladerunner37.testtask.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"depCode", "depJob"})
public class DepsDto {

    @JsonIgnore
    private Integer id;
    private String depCode;
    private String depJob;
    private String description;
}
