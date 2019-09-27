package eObrazovanje.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eObrazovanje.DTO.ProfessorRoleDTO;
import eObrazovanje.Model.Professor;
import eObrazovanje.Model.ProfessorRole;
import eObrazovanje.Model.Subject;
import eObrazovanje.Service.ProfessorRoleService;
import eObrazovanje.Service.ProfessorService;
import eObrazovanje.Service.SubjectService;



@RestController
@RequestMapping(value = "api/professorRoles")
public class ProfessorRoleController {

	@Autowired
	ProfessorRoleService professorRoleService;

	 @Autowired
	 SubjectService subjectService;

	@Autowired
	ProfessorService professorService;
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<ProfessorRoleDTO>> getProfessorRoles() {
		List<ProfessorRole> professorRoles = professorRoleService.findAll();
		List<ProfessorRoleDTO> profesorRolesDTO = new ArrayList<ProfessorRoleDTO>();
		for (ProfessorRole professorRole : professorRoles) {
			profesorRolesDTO.add(new ProfessorRoleDTO(professorRole));
		}
		return new ResponseEntity<>(profesorRolesDTO, HttpStatus.OK);
	}
	@PreAuthorize("hasAnyRole('ROLE_PROFESSOR','ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProfessorRoleDTO> getProfessorRole(@PathVariable Long id) {
		ProfessorRole professorRole = professorRoleService.findOne(id);
		if (professorRole == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(new ProfessorRoleDTO(professorRole), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProfessorRole>> getProfessorRolesPage(Pageable page) {
		Page<ProfessorRole> professorRoles = professorRoleService.findAll(page);

		return new ResponseEntity<>(professorRoles, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ProfessorRoleDTO> saveProfessorRole(@RequestBody ProfessorRoleDTO professorRoleDTO) {
		if (professorRoleDTO.getProfessorDTO() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if (professorRoleDTO.getSubjectDTO() == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		 Subject subject=subjectService.findOne(professorRoleDTO.getSubjectDTO().getId());
		 if (subject == null)
		 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Professor professor = professorService.findOne(professorRoleDTO.getProfessorDTO().getId());
		if (professor == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		ProfessorRole professorRole = new ProfessorRole();
		professorRole.setProfessor(professor);
		 professorRole.setSubject(subject);
		professorRole.setRole(professorRoleDTO.getRole());
		professorRole = professorRoleService.save(professorRole);
		return new ResponseEntity<>(new ProfessorRoleDTO(professorRole), HttpStatus.OK);

	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ProfessorRoleDTO> updateProfessorRole(@RequestBody ProfessorRoleDTO professorRoleDTO) {
		ProfessorRole professorRole = professorRoleService.findOne(professorRoleDTO.getId());
		if (professorRole == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		professorRole.setRole(professorRoleDTO.getRole());
		professorRole = professorRoleService.save(professorRole);

		return new ResponseEntity<>(new ProfessorRoleDTO(professorRole), HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteResponsePayment(@PathVariable Long id) {
		ProfessorRole professorRole = professorRoleService.findOne(id);
		if (professorRole == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			professorRoleService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);

		}

	}

}
