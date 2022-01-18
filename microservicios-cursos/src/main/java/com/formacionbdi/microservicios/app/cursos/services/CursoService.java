package com.formacionbdi.microservicios.app.cursos.services;
        

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;


public interface CursoService extends CommonService<Curso> {

	public Curso findCursosByAlumnoId(Long id);
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno (Long alumnoId);
	public Iterable<Alumno> obtenerAlumnosPorCurso (List<Long> ids);
	public void eliminarCursoAlumnoPorid(Long id);
}
