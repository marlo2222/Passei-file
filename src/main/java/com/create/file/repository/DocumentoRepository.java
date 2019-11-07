package com.create.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.create.file.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	@Query("SELECT f FROM Documento f WHERE f.idUsuario = :idPassado")
	List<Documento> findAllByIdUsuario(@Param("idPassado") long id);

	Long countByIdUsuario(long idUsuario);

	List<Documento> findAllByIdDisciplina(long idDisciplina);

    Documento findById(long id);
	

	//Documento findByHash(String hash);
	//Documento findById(long id);
}
