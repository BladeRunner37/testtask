package ru.bladerunner37.testtask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dto.DepsDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class Merger {

    private final DepsService depsService;

    @Transactional
    public void merge(Set<DepsDto> dtos) {
        log.info("Start merging dtos: {}", dtos.size());
        Set<DepsDto> prev = depsService.findAll();
        log.info("Found in DB: {}", prev.size());
        Set<Integer> prevIds = prev.stream()
                .map(DepsDto::getId)
                .collect(Collectors.toSet());
        dtos.forEach(d -> {
            prev.stream()
                    .filter(f -> f.equals(d))
                    .findFirst()
                    .ifPresent(f -> f.setDescription(d.getDescription()));
        });
        prev.retainAll(dtos);
        prev.addAll(dtos);
        Set<Integer> idsToSave = prev.stream()
                .map(DepsDto::getId)
                .collect(Collectors.toSet());
        prevIds.removeAll(idsToSave);
        depsService.deleteIds(prevIds);
        log.info("Deleted from DB: {}", prevIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";")));
        depsService.saveDtos(prev);
        log.info("Saved or updated dtos: {}", prev.size());
    }
}
