package com.jackdyt.poem.controller;

import com.jackdyt.poem.dto.poemDTO;
import com.jackdyt.poem.service.poemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("poem")
@CrossOrigin
public class backPoemController {

    @Autowired
    private poemService poemService;

    @GetMapping("findByPage/{page}/{size}")
    public Map<String, Object> findByPage(@PathVariable("page") Integer page, @PathVariable("size")  Integer size){
//        page = 1;
//        size = 10;
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


}
