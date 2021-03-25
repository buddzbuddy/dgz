package com.webdatabase.dgz.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webdatabase.dgz.model.Answer;
import com.webdatabase.dgz.repository.AnswerRepository;

@Service
@Transactional
public class AnswerService {
	@Autowired
	private AnswerRepository answerRepository;
	
	public List<Answer> listAll(){
		return answerRepository.findAll(Sort.by("answer").ascending());
	}
	
	
}
