package com.cs.elasticsearch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.elasticsearch.document.BussinessDataRecord;
import com.cs.elasticsearch.search.SearchRequestDTO;
import com.cs.elasticsearch.service.BussinessDataService;

@RestController
@RequestMapping("/api/bussinessdata")
public class BussinessDataController {

	private final BussinessDataService service;

	@Autowired
	public BussinessDataController(BussinessDataService bussinessDataService) {
		this.service = bussinessDataService;
	}

	@GetMapping("/{id}")
	public BussinessDataRecord findById(@PathVariable final String id) {
		return service.getById(id);
	}

	/*
	 * @PostMapping public void save(@RequestBody final BussinessDataRecord
	 * bussinessDataRecord) { service.save(bussinessDataRecord); }
	 */

	@PostMapping
	public void index(@RequestBody final BussinessDataRecord bussinessDataRecord) {
		service.index(bussinessDataRecord);
	}

	@PostMapping("/search")
	public List<BussinessDataRecord> search(@RequestBody final SearchRequestDTO dto) {
		return service.search(dto);
	}

}
