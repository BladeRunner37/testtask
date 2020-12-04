package ru.bladerunner37.testtask.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.exception.DuplicatesInFileException;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SerializeService {

    private final DepsService depsService;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public void serializeToFile(String path) throws Exception {
        File out = new File(path);
        Set<DepsDto> all = depsService.findAll();
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(out, all);
        log.info("Written to file, dtos: {}", all.size());
    }

    public Set<DepsDto> deserializeFromFile(String path) throws Exception {
        List<DepsDto> dtos = objectMapper.readValue(new File(path), new TypeReference<>() {});
        Set<DepsDto> depsDtoSet = new HashSet<>(dtos);
        if (depsDtoSet.size() != dtos.size()) {
            throw new DuplicatesInFileException("Found duplicates by unique fields in file");
        }
        log.info("Dtos read complete, dtos: {}", depsDtoSet.size());
        return depsDtoSet;
    }
}
