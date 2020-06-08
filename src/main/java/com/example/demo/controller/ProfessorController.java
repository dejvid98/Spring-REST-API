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
import com.example.demo.model.Professor;
import com.example.demo.repository.ProfessorRepository;

@RestController
@RequestMapping("/api/v1")
public class ProfessorController {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	// Get all professors
	@GetMapping("professors")
	public List<Professor> getAllProfessors(){
		return this.professorRepository.findAll();
	}
	
	// Get Professor by ID
	@GetMapping("/professors/{id}")
	public ResponseEntity<Professor> getProfessorById(@PathVariable(value = "id") Long professorId)
			throws ResourceNotFoundException {
		Professor professor = professorRepository.findById(professorId)
				.orElseThrow(() -> new ResourceNotFoundException("Professor not found for this id :: " + professorId));
		return ResponseEntity.ok().body(professor);
	}
	
	@PostMapping("/professors")
	public Professor createProfessor(@RequestBody Professor professor) {
		return professorRepository.save(professor);
	}

	@PutMapping("/professors/{id}")
	public ResponseEntity<Professor> updateProfessor(@PathVariable(value = "id") Long professorId,
			@Validated @RequestBody Professor professorDetails) throws ResourceNotFoundException {
		Professor professor = professorRepository.findById(professorId)
				.orElseThrow(() -> new ResourceNotFoundException("Professor not found for this id :: " + professorId));

		professor.setLastName(professorDetails.getLastName());
		professor.setFirstName(professorDetails.getFirstName());
		final Professor updatedProfessor = professorRepository.save(professor);
		return ResponseEntity.ok(updatedProfessor);
	}

	@DeleteMapping("/professors/{id}")
	public Map<String, Boolean> deleteProfessor(@PathVariable(value = "id") Long professorId)
			throws ResourceNotFoundException {
		Professor professor = professorRepository.findById(professorId)
				.orElseThrow(() -> new ResourceNotFoundException("Professor not found for this id :: " + professorId));

		professorRepository.delete(professor);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
