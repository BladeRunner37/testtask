package ru.bladerunner37.testtask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dao.DepsDao;
import ru.bladerunner37.testtask.dto.DepsDto;

import java.util.Set;

@Service
public class DepsService {

    private final DepsDao depsDao;
    private final Converter converter;

    @Autowired
    public DepsService(DepsDao depsDao, Converter converter) {
        this.depsDao = depsDao;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public Set<DepsDto> findAll() {
        return converter.convert(depsDao.findAll());
    }

    @Transactional
    public void deleteIds(Set<Integer> ids) {
        ids.forEach(depsDao::deleteById);
    }

    @Transactional
    public void saveDtos(Set<DepsDto> dtos) {
        depsDao.saveAll(converter.transform(dtos));
    }
}
