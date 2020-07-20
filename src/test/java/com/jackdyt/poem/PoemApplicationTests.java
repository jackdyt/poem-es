package com.jackdyt.poem;

import com.jackdyt.poem.controller.backPoemController;
import com.jackdyt.poem.service.poemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class PoemApplicationTests {

    @Autowired
    private com.jackdyt.poem.service.poemService poemService;
    @Test
    void contextLoads() {
        System.out.println( poemService.findByPage(1,10));
    }

}
