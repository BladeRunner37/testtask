package ru.bladerunner37.testtask.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bladerunner37.testtask.entity.Deps;

public interface DepsDao extends JpaRepository<Deps, Integer> {
}
