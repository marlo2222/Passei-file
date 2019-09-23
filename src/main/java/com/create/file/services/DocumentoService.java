package com.create.file.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
;

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

	@Transactional
	public ResponseEntity<?> addDocumentos(MultipartFile[] file) throws NoSuchAlgorithmException {
		for (MultipartFile multipartFile : file) {
			create(multipartFile);
		}
		return new ResponseEntity<>("Adicionado com sucesso", HttpStatus.ACCEPTED);
	}
	private void create(MultipartFile multipartFile) throws NoSuchAlgorithmException {
		Documento doc = new Documento();
		doc.setFileName(multipartFile.getOriginalFilename());
		doc.setFiletype(multipartFile.getContentType());
		doc.setSize(multipartFile.getSize());
		doc.setFileDownloadUri();
		doc.setHash();
		doc.setData(new Date());
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
