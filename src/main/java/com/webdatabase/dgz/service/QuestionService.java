package com.webdatabase.dgz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webdatabase.dgz.model.Question;
import com.webdatabase.dgz.repository.QuestionRepository;

@Service
@Transactional
public class QuestionService {
	@Autowired
	private QuestionRepository questionRepository;
	
	public List<Question> listAll(){
		return questionRepository.findAll(Sort.by("Question").ascending());
	}
}
