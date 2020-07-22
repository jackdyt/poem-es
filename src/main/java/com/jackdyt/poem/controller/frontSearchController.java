package com.jackdyt.poem.controller;

import com.jackdyt.poem.dto.poemDTO;
import com.jackdyt.poem.service.poemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Slf4j
public class frontSearchController {
    @Autowired
    private poemService poemService;

    @GetMapping("search/{type}/{keyword}")
    public Map<String, Object> search(@PathVariable("type") String type,
                                      @PathVariable("keyword") String keyword){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.equals("all", type)) type = null;
        try{
            List<poemDTO> poems =   poemService.findByKeyword(keyword, type);
            map.put("poems", poems);
            map.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
            map.put("success", false);
        }
        return map;
    }
}
