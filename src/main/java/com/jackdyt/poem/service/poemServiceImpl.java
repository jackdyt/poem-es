package com.jackdyt.poem.service;

import com.jackdyt.poem.dto.poemDTO;
import com.jackdyt.poem.es.PoemRepository;
import com.jackdyt.poem.mapper.poemMapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class poemServiceImpl implements poemService {
    @Autowired
    private poemMapper poemMapper;

    @Qualifier("elasticsearchClient")
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private PoemRepository poemRepository;


    @Override
    public List<poemDTO> findByPage(Integer page, Integer size) {
        int start = (page-1) *size;
        return poemMapper.findByPage(start, size);
    }

    @Override
    public Long findTotal() {
        Long count = poemMapper.findTotal();
        return count;
    }

    @Override
    public void saveToEs() {
        List<poemDTO> poemDTOS = poemMapper.findAll();
        poemRepository.deleteAll();
        poemRepository.saveAll(poemDTOS);
        return;

    }

    @Override
    public void deleteFromEs() {
        Iterable<poemDTO> poemDTOS = poemRepository.findAll();
        if (poemDTOS.iterator().hasNext()){
            poemRepository.deleteAll();
        }else{
            throw new RuntimeException("No document in current index");
        }
        return;
    }

    @Override
    public List<poemDTO> findByKeyword(String keyword, String type) {
        List<poemDTO> poems = new ArrayList<>();
        try{
            SearchRequest searchRequest = new SearchRequest("poems");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            if (StringUtils.isEmpty(keyword)){
                sourceBuilder.query(QueryBuilders.matchAllQuery());
            }else{
                sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "name","content","author"));
            }

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (!StringUtils.isEmpty(type)){
                boolQueryBuilder.filter(QueryBuilders.matchQuery("type", type));
            }
            //add specific filter
            sourceBuilder.postFilter(boolQueryBuilder);

            //add highlight
            sourceBuilder.highlighter(new HighlightBuilder().field("*").requireFieldMatch(false).preTags("<span style='color:red'>").postTags("</span>"));
            sourceBuilder.size(50);
            searchRequest.source(sourceBuilder);

            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //get the search result
            if (searchResponse.getHits().getTotalHits().value>0){
                poems = new ArrayList<>();
            }
            SearchHit[] hits = searchResponse.getHits().getHits();
            for (SearchHit hit: hits){
                poemDTO poem = new poemDTO();
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //get highlight part
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                poem.setId(hit.getId());
                poem.setName(sourceAsMap.get("name").toString());
                if (highlightFields.containsKey("name")){
                    poem.setName(highlightFields.get("name").fragments()[0].toString());
                }
                poem.setAuthor(sourceAsMap.get("author").toString());
                if (highlightFields.containsKey("author")) {
                    poem.setAuthor(highlightFields.get("author").fragments()[0].toString());
                }
                poem.setContent(sourceAsMap.get("content").toString());
                if (highlightFields.containsKey("content")) {
                    poem.setContent(highlightFields.get("content").fragments()[0].toString());
                }
                poem.setAuthordes(sourceAsMap.get("authordes").toString());
                if (highlightFields.containsKey("authordes")) {
                    poem.setAuthordes(highlightFields.get("authordes").fragments()[0].toString());
                }
                poem.getCategory().setName(sourceAsMap.get("category").toString());
                poem.setType(sourceAsMap.get("type").toString());

                poems.add(poem);
            }


        }catch (IOException e){
            e.printStackTrace();
        }
        return poems;
    }
}
