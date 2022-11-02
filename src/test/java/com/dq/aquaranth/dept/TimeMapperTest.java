package com.dq.aquaranth.dept;

import com.dq.aquaranth.dept.mapper.TimeMapper1;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class TimeMapperTest {

    @Autowired(required = false)
    TimeMapper1 timeMapper;

    @Test
    void getTimeTest() {
        log.info(timeMapper.getTime());
    }

}
