package eObrazovanje.DTO;

import java.util.List;


public class TempSubjectDTO {
	
	private List<StudentDTO> studentsDTO;

	public TempSubjectDTO() {

	}

	public TempSubjectDTO(List<StudentDTO> studentsDTO) {
		super();
		this.studentsDTO = studentsDTO;
	}

	public List<StudentDTO> getStudentsDTO() {
		return studentsDTO;
	}

	public void setStudentsDTO(List<StudentDTO> studentsDTO) {
		this.studentsDTO = studentsDTO;
	}
	
	
	
}