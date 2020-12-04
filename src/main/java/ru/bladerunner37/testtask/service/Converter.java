package ru.bladerunner37.testtask.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.entity.Deps;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Converter {

    private final ModelMapper modelMapper;

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
