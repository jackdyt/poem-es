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
@RequestMapping("poem")
@CrossOrigin
@Slf4j
public class backPoemController {

    @Autowired
    private poemService poemService;



    @GetMapping("findByPage/{page}/{size}")
    public Map<String, Object> findByPage(@PathVariable("page") Integer page, @PathVariable("size")  Integer size){
        Map<String, Object> res = new HashMap<>();
        Long totalPoems = poemService.findTotal();
        res.put("totalPoems", totalPoems);

        List<poemDTO> poems = poemService.findByPage(page, size);
        res.put("poems", poems);

        Long totalPage = totalPoems % size == 0 ? totalPoems/size : totalPoems/size+1;
        res.put("totalPage", totalPage);
        res.put("page", page);
        return res;
    }

    @GetMapping("saveAll")
    public Map<String,Boolean> saveAll(){
        Map<String, Boolean> map = new HashMap<>();
        try{
            poemService.saveToEs();
            map.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
            map.put("success", false);
        }

        return map;
    }

    @GetMapping("deleteAll")
    public Map<String,Boolean> deleteAll(){
        Map<String, Boolean> map = new HashMap<>();
        try{
            poemService.deleteFromEs();
            map.put("success", true);
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
            map.put("success", false);
        }
        return map;
    }



}
