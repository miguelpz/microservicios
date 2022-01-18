package com.formacionbdi.microservicios.app.respuestas.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.microservicios.app.respuestas.client.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	RespuestaRepository repository;
	
	@Autowired
	private ExamenFeignClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {	
		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {		
		Examen examen = examenClient.obtenerExamenPorId(examenId);
//		List<Pregunta> preguntas = examen.getPreguntas();
//		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
//		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoPreguntaIdsn(alumnoId, preguntaIds);
//		respuestas = respuestas.stream().map(r ->{
//			preguntas.forEach(p ->{
//				if (p.getId() ==r.getPreguntaId()) {
//					r.setPregunta(p);
//				}
//			});
//			return r;
//		}).collect(Collectors.toList());
		
		return repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);					
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {		

		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds = (List<Long>) respuestasAlumno.stream()
				.filter(r -> r.getPregunta()!=null)
				.map(r -> r.getPregunta().getExamen().getId())
				.distinct().collect(Collectors.toList());
			
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

}
