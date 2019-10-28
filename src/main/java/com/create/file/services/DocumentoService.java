package com.create.file.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.create.file.model.Documento;
import com.create.file.repository.DocumentoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	FileStorageService fileStorageService;

	public ResponseEntity<?> listarDocumentosUsuario(long id){
		return new ResponseEntity<>(documentoRepository.findAllByIdUsuario(id), HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> addDocumentos(long usuario, long disciplina, String titulo, long tipo, MultipartFile[] file) throws NoSuchAlgorithmException {
		for (MultipartFile multipartFile : file) {
			System.out.println("File Name: " + multipartFile.getOriginalFilename());
        	System.out.println("File Content Type: " + multipartFile.getContentType());
			create(usuario, disciplina, titulo, tipo, multipartFile);
		}
		return new ResponseEntity<>("Adicionado com sucesso", HttpStatus.ACCEPTED);
	}
	private void create(long usuario, long disciplina, String titulo, long tipo, MultipartFile multipartFile) throws NoSuchAlgorithmException {
		Documento doc = new Documento();
		doc.setFileName(multipartFile.getOriginalFilename());
		doc.setFiletype(multipartFile.getContentType());
		doc.setSize(multipartFile.getSize());
		doc.setFileDownloadUri();
		doc.setHash();
		doc.setData(new Date());
		doc.setTipo(tipo);
		doc.setTitulo(titulo);
		doc.setIdUsuario(usuario);
		doc.setIdDisciplina(disciplina);
		documentoRepository.save(doc);
		fileStorageService.create(multipartFile);
	}

	public Resource download(String filename){
		return fileStorageService.loadFileAsResource(filename);
	}

	public ResponseEntity<?> delete(long id) throws IOException {
		Documento documento = documentoRepository.getOne(id);
		if (documento == null){
			return new ResponseEntity<>("Não foi encontrado nada", HttpStatus.NOT_FOUND);
		}
		documentoRepository.delete(documento);
		fileStorageService.delete(documento);
		return new ResponseEntity<>("Removido com sucesso", HttpStatus.OK);
	}
}
