package com.create.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.create.file.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
	//Documento findByHash(String hash);
	//Documento findById(long id);
}
