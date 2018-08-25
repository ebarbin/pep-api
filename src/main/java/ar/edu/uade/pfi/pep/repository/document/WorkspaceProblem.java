package ar.edu.uade.pfi.pep.repository.document;

public class WorkspaceProblem {

	private Problem problem;
	
	private String solution;
	
	private boolean active;
	
	public WorkspaceProblem() {}

	public WorkspaceProblem(Problem problem) {
		this.problem = problem;
	}
	
	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
