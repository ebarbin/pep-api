package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import ar.edu.uade.pfi.pep.repository.document.Consultation;

public interface ConsultationRespositoryCustom {
	List<Consultation> getTeacherConsultations();
	List<Consultation> getStudentConsultations();
}
