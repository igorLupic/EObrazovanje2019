package eObrazovanje.DTO;

import eObrazovanje.Model.Exam;

public class ExamDTO {
	
	private Long id;
	private Integer points;
	private boolean pass;
	private Long studentID;
	private String subjectName;
	
	public ExamDTO(Exam exam){
		this.id = exam.getId();
		this.points = exam.getPoints();
		this.pass = exam.getPass();
		this.studentID = exam.getStudent().getId();
		this.subjectName = exam.getSubject().getName();
	}
	
	public ExamDTO(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public boolean isPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public Long getStudentID() {
		return studentID;
	}
	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	
}
