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
import ru.bladerunner37.testtask.dao.DepsDao;
import ru.bladerunner37.testtask.dto.DepsDto;
import ru.bladerunner37.testtask.entity.Deps;
import ru.bladerunner37.testtask.exception.DuplicatesInFileException;
import ru.bladerunner37.testtask.service.SerializeService;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest(args = {"0", "0"})
@RunWith(SpringRunner.class)
public class SerializeServiceTest {

    @MockBean
    private DepsDao depsDao;

    @Autowired
    private SerializeService serializeService;

    private List<Deps> storage;

    @Before
    public void initMocks() {
        storage = new ArrayList<>();
        Mockito.doAnswer(invocation -> new ArrayList<>(storage)).when(depsDao).findAll();
        Mockito.doAnswer(invocation -> {
            List<Deps> toSave = invocation.getArgument(0);
            int count = 0;
            for (Deps d : toSave) {
                d.setId(++count);
            }
            storage.addAll(toSave);
            return null;
        }).when(depsDao).saveAll(Mockito.anyList());
    }

    @Test
    public void testPositive() throws Exception {
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
        depsDao.saveAll(deps);
        serializeService.serializeToFile("output");
        Set<DepsDto> dtos = serializeService.deserializeFromFile("output");
        Assert.assertEquals(2, dtos.size());
        Files.deleteIfExists(Path.of("output"));
    }

    @Test(expected = DuplicatesInFileException.class)
    public void testNegative() throws Exception {
        URL res = getClass().getClassLoader().getResource("duplicates");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        serializeService.deserializeFromFile(absolutePath);
    }
}
