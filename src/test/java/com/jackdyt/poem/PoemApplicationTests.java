package com.jackdyt.poem;

import com.jackdyt.poem.dto.poemDTO;
import com.jackdyt.poem.service.poemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PoemApplicationTests {

    @Autowired
    private poemService poemService;
    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();

        poemService.saveToEs();
        List<poemDTO> poems = poemService.findByKeyword("天地","唐诗");
        System.out.println(poems.get(0).getContent());
    }

}
