package eObrazovanje.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eObrazovanje.Model.Obligation;
import eObrazovanje.Repository.ObligationRepository;



@Service
public class ObligationService {

	@Autowired
	ObligationRepository obligationRepository;
	
    public Obligation findOne(Long id) {
		return obligationRepository.findOne(id);
	}

	public List<Obligation> findAll() {
		return obligationRepository.findAll();
	}
	
	public Page<Obligation> findAll(Pageable page) {
		return obligationRepository.findAll(page);
	}

	public Obligation save(Obligation Obligation) {
		return obligationRepository.save(Obligation);
	}

	public void remove(Long id) {
		obligationRepository.delete(id);
	}

}

