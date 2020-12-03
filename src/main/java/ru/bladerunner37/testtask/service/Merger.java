package ru.bladerunner37.testtask.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dto.DepsDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Merger {

    private static final Logger LOG = LoggerFactory.getLogger(Merger.class);

    private final DepsService depsService;

    @Autowired
    public Merger(DepsService depsService) {
        this.depsService = depsService;
    }

    @Transactional
    public void merge(Set<DepsDto> dtos) {
        LOG.info("Start merging dtos: {}", dtos.size());
        Set<DepsDto> prev = depsService.findAll();
        LOG.info("Found in DB: {}", prev.size());
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
        LOG.info("Deleted from DB: {}", prevIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";")));
        depsService.saveDtos(prev);
        LOG.info("Saved or updated dtos: {}", prev.size());
    }
}
