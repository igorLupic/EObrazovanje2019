package eObrazovanje.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import eObrazovanje.Model.Document;
import eObrazovanje.Repository.DocumentRepository;



@Service
public class DocumentService {

	@Autowired
	DocumentRepository documentRepository;
	
	public Document findOne(Long id) {
		return documentRepository.findOne(id);
	}

	public List<Document> findAll() {
		return documentRepository.findAll();
	}
	
	public Page<Document> findAll(Pageable page) {
		return documentRepository.findAll(page);
	}

	public Document save(Document document) {
		return documentRepository.save(document);
	}

	public void remove(Long id) {
		documentRepository.delete(id);
	}

}
