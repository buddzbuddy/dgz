package com.webdatabase.dgz.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webdatabase.dgz.model.DatasourceField;
import com.webdatabase.dgz.query.utils.SearchCriteria;
import com.webdatabase.dgz.service.DatasourceFieldService;


@RestController
@RequestMapping("/query")
public class JsonquerybuilderController {


    
    @Autowired
    private DatasourceFieldService userApi;
    
    @PostMapping(path = "/test",
    		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<QueryResult> test(@RequestBody QueryCondition[] conditions)
    {
    	
    	List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("name", ":", "inn"));
        //params.add(new SearchCriteria("lastName", ":", "Doe"));

        List<DatasourceField> results = userApi.searchField(params);

    	System.out.print(results.size());
    	
    	QueryResult res = new QueryResult(true, "", null);
    	
    	
        return new ResponseEntity<QueryResult>(res, HttpStatus.OK);
    }
}

class QueryCondition {
	private String field;
	private Object val;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object val) {
		this.val = val;
	}
}
class QueryResult {
	private Boolean result;
	private String error;
	private Object[] data;
	
	public QueryResult() {}
	
	public QueryResult(Boolean result, String error, Object[] data) {
		this.result = result;
		this.error = error;
		this.data = data;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] result) {
		this.data = result;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}