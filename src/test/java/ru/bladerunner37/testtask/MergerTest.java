package ru.bladerunner37.testtask;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.bladerunner37.testtask.dao.DepsDao;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.entity.Deps;
import ru.bladerunner37.testtask.service.Merger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest(args = {"0", "0"})
@RunWith(SpringRunner.class)
@Transactional
public class MergerTest {

    @Autowired
    private Merger merger;

    @MockBean
    private DepsDao depsDao;

    List<Deps> storage;

    @Before
    public void initMocks() {
        storage = new ArrayList<>();
        Mockito.doAnswer(invocation -> {
            AtomicInteger count = new AtomicInteger(storage.stream()
                    .max(Comparator.comparingInt(Deps::getId))
                    .map(Deps::getId)
                    .orElse(0));
            List<Deps> toSave = invocation.getArgument(0);
            toSave.forEach(d -> {
                Optional<Deps> existOpt = storage.stream()
                        .filter(d1 -> d1.getId().equals(d.getId()))
                        .findFirst();
                if (existOpt.isPresent()) {
                    Deps exists = existOpt.get();
                    exists.setDescription(d.getDescription());
                    exists.setDepJob(d.getDepJob());
                    exists.setDepCode(d.getDepCode());
                } else {
                    d.setId(count.incrementAndGet());
                    storage.add(d);
                }
            });
            return null;
        }).when(depsDao).saveAll(Mockito.anyList());
        Mockito.doAnswer(invocation -> new ArrayList<>(storage)).when(depsDao).findAll();
        Mockito.doAnswer(invocation -> {
            storage.removeIf(d -> d.getId().equals(invocation.getArgument(0)));
            return null;
        }).when(depsDao).deleteById(Mockito.anyInt());
    }

    @Test
    public void test() {
        List<Deps> deps = new ArrayList<>();
        Deps deps1 = new Deps();
        deps1.setDepCode("code1");
        deps1.setDepJob("job1");
        deps1.setDescription("desc1");
        deps.add(deps1);
        Deps deps2 = new Deps();
        deps2.setDepCode("code2");
        deps2.setDepJob("job2");
        deps2.setDescription("desc2");
        deps.add(deps2);
        Deps deps3 = new Deps();
        deps3.setDepCode("code3");
        deps3.setDepJob("job3");
        deps3.setDescription("desc3");
        deps.add(deps2);
        depsDao.saveAll(deps);
        Set<DepsDto> newDtos = new HashSet<>();
        DepsDto dto1 = new DepsDto();
        dto1.setDepCode("code4");
        dto1.setDepJob("job4");
        dto1.setDescription("desc4");
        newDtos.add(dto1);
        DepsDto dto2 = new DepsDto();
        dto2.setDepCode("code2");
        dto2.setDepJob("job2");
        dto2.setDescription("desc2");
        newDtos.add(dto2);
        DepsDto dto3 = new DepsDto();
        dto3.setDepCode("code3");
        dto3.setDepJob("job3");
        dto3.setDescription("newDescription");
        newDtos.add(dto3);
        merger.merge(newDtos);
        List<Deps> after = depsDao.findAll();
        Assert.assertEquals(3, after.size());
        Optional<Deps> d1Opt = after.stream().filter(d -> "code4".equals(d.getDepCode())).findFirst();
        if (d1Opt.isEmpty()) {
            Assert.fail("code4 not saved");
        }
        Optional<Deps> d2Opt = after.stream().filter(d -> "code2".equals(d.getDepCode())).findFirst();
        if (d2Opt.isEmpty()) {
            Assert.fail("code2 not saved");
        }
        Optional<Deps> d3Opt = after.stream().filter(d -> "code3".equals(d.getDepCode())).findFirst();
        if (d3Opt.isEmpty()) {
            Assert.fail("code3 not saved");
        } else {
            Assert.assertEquals("newDescription", d3Opt.get().getDescription());
        }
    }
}
