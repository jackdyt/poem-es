package com.jackdyt.poem.es;


import com.jackdyt.poem.dto.poemDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PoemRepository extends ElasticsearchRepository<poemDTO, String> {
}
