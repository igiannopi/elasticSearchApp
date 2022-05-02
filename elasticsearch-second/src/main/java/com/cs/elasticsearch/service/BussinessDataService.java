package com.cs.elasticsearch.service;

import com.cs.elasticsearch.document.BussinessDataRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cs.elasticsearch.helper.Mappings;
//import com.cs.elasticsearch.repository.BussinessDataRepository;
import com.cs.elasticsearch.search.SearchRequestDTO;
import com.cs.elasticsearch.search.util.SearchUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BussinessDataService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOG = LoggerFactory.getLogger(BussinessDataService.class);

    private final RestHighLevelClient client;
    
	//private final BussinessDataRepository bussinessDataRepository;


    @Autowired
    public BussinessDataService(RestHighLevelClient client) {
        this.client = client;
    }
    


    /**
     * Used to search for Bussiness Data based on data provided in the {@link SearchRequestDTO} DTO
     *
     * @param dto DTO containing info about what to search for.
     * @return Returns a list of found bussiness data.
     */
    public List<BussinessDataRecord> search(final SearchRequestDTO dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Mappings.DATA_MANAGEMENT_INDEX,
                dto
        );

        return searchInternal(request);
    }


    private List<BussinessDataRecord> searchInternal(final SearchRequest request) {
        if (request == null) {
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = response.getHits().getHits();
            final List<BussinessDataRecord> bsData = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
            	bsData.add(
                        MAPPER.readValue(hit.getSourceAsString(), BussinessDataRecord.class)
                );
            }

            return bsData;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Boolean index(final BussinessDataRecord bussinessDataRecord) {
        try {
            final String bussinessDataAsString = MAPPER.writeValueAsString(bussinessDataRecord);

            final IndexRequest request = new IndexRequest(Mappings.DATA_MANAGEMENT_INDEX);
            request.id(bussinessDataRecord.getUid());
            request.source(bussinessDataAsString, XContentType.JSON);

            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            return response != null && response.status().equals(RestStatus.OK);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public BussinessDataRecord getById(final String bussinessDataRecordId) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Mappings.DATA_MANAGEMENT_INDEX, bussinessDataRecordId),
                    RequestOptions.DEFAULT
            );
            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            return MAPPER.readValue(documentFields.getSourceAsString(), BussinessDataRecord.class);
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}