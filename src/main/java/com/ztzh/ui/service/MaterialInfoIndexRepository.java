package com.ztzh.ui.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import com.ztzh.ui.bo.MaterialInfoIndex;

@Component
public interface MaterialInfoIndexRepository extends ElasticsearchRepository<MaterialInfoIndex,Long>{

}
