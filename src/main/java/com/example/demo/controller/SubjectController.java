package com.example.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Subject;
import com.example.demo.repository.SubjectRepository;

@RestController
@RequestMapping("/api/v1")
public class SubjectController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	// Get all professors
	@GetMapping("subjects")
	public List<Subject> getAllSubjects(){
		return this.subjectRepository.findAll();
	}
	
	// Get Professor by ID
	@GetMapping("/subjects/{id}")
	public ResponseEntity<Subject> getSubjectById(@PathVariable(value = "id") Long subjectId)
			throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));
		return ResponseEntity.ok().body(subject);
	}
	
	@PostMapping("/subjects")
	public Subject createSubject(@RequestBody Subject subject) {
		return subjectRepository.save(subject);
	}

	@PutMapping("/subjects/{id}")
	public ResponseEntity<Subject> updateSubject(@PathVariable(value = "id") Long subjectId,
			@Validated @RequestBody Subject subjectDetail) throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));

		subject.setName(subjectDetail.getName());
		subject.setProfessorId(subjectDetail.getProfessorId());
		final Subject updatedSubject = subjectRepository.save(subject);
		return ResponseEntity.ok(updatedSubject);
	}

	@DeleteMapping("/subjects/{id}")
	public Map<String, Boolean> deleteSubject(@PathVariable(value = "id") Long subjectId)
			throws ResourceNotFoundException {
		Subject subject = subjectRepository.findById(subjectId)
				.orElseThrow(() -> new ResourceNotFoundException("Subject not found for this id :: " + subjectId));

		subjectRepository.delete(subject);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
