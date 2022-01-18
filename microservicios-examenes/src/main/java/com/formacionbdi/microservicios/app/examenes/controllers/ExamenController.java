package com.formacionbdi.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {
	
	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds){
		return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasBypreguntaIds(preguntaIds));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar (@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id){
		
		if (result.hasErrors()) {
			return this.validar(result);
		}	
		
		Optional<Examen> o = service.findById(id);
		
		if (!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Examen examenDB = o.get();
		
		examenDB.setNombre(examen.getNombre());
		
		
		List<Pregunta> eliminadas = new ArrayList<>();
		
		
		examenDB.getPreguntas().forEach((pdb ->{
			if (!examen.getPreguntas().contains(pdb)) {
				eliminadas.add(pdb);
			}
		}));
		
		eliminadas.forEach(toDelete -> examenDB.getPreguntas().remove(toDelete));
		
//		examenDB.getPreguntas()
//		.stream()
//		.filter(pdb -> !examen.getPreguntas().contains(pdb))
//		.forEach(examenDB::removePregunta);
		
		examenDB.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDB));				
	}
	
	@GetMapping ("/filtrar/{term}")
	public ResponseEntity<?> filtrar (@PathVariable String term){
		return ResponseEntity.ok(service.findByNombre(term));
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
	
}
