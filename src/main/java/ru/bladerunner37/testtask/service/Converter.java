package ru.bladerunner37.testtask.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.entity.Deps;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Converter {

    private final ModelMapper modelMapper;

    @Autowired
    public Converter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Set<DepsDto> convert(List<Deps> deps) {
        return deps.stream()
                .map(d -> modelMapper.map(d, DepsDto.class))
                .collect(Collectors.toSet());
    }

    public List<Deps> transform(Set<DepsDto> dtos) {
        return dtos.stream()
                .map(d -> modelMapper.map(d, Deps.class))
                .collect(Collectors.toList());
    }
}
