package ru.bladerunner37.testtask.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.exception.DuplicatesInFileException;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SerializeService {

    private static final Logger LOG = LoggerFactory.getLogger(SerializeService.class);

    private final DepsService depsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public SerializeService(DepsService depsService, ObjectMapper objectMapper) {
        this.depsService = depsService;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public void serializeToFile(String path) throws Exception {
        File out = new File(path);
        Set<DepsDto> all = depsService.findAll();
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(out, all);
        LOG.info("Written to file, dtos: {}", all.size());
    }

    public Set<DepsDto> deserializeFromFile(String path) throws Exception {
        List<DepsDto> dtos = objectMapper.readValue(new File(path), new TypeReference<>() {});
        Set<DepsDto> depsDtoSet = new HashSet<>(dtos);
        if (depsDtoSet.size() != dtos.size()) {
            throw new DuplicatesInFileException("Found duplicates by unique fields in file");
        }
        LOG.info("Dtos read complete, dtos: {}", depsDtoSet.size());
        return depsDtoSet;
    }
}
