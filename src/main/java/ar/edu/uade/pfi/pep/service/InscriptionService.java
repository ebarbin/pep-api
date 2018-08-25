package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.InscriptionRepository;
import ar.edu.uade.pfi.pep.repository.document.Inscription;

@Component
public class InscriptionService {

	@Autowired
	private InscriptionRepository repository;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private RequestDataHolder requestDataHolder;

	public Inscription createInscription(Inscription inscription) {
		
		inscription.setInscriptionDate(new Date());
		inscription = this.repository.save(inscription);
		
		this.workspaceService.createWorkspaceByInscription(inscription);
		/*new Workspace(inscription.getStudent(), inscription.getCourse(),
				inscription.getCourse().getProblems().get(0), false));*/
		
		return inscription;
	}

	public void deleteInscription(String inscriptionId) {
		Inscription inscription = this.repository.findById(inscriptionId).get();
		this.workspaceService.deleteByStudentAndCourse(inscription.getStudent(), inscription.getCourse());
		this.repository.deleteById(inscriptionId);
	}

	public List<Inscription> getInscriptions() {
		return this.repository.findByStudentUserId(this.requestDataHolder.getUserId());
	}
}
