package eObrazovanje.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eObrazovanje.Model.Document;


public interface DocumentRepository extends JpaRepository<Document, Long> {

}
