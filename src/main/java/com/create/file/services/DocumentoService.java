package com.create.file.services;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.create.file.model.Documento;
import com.create.file.repository.DocumentoRepository;

@Service
public class DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;

	RestTemplate rest;

	private String url = "http://localhost:8085/uploadFile";
	@Transactional
	public ResponseEntity<?> addDocumentos(MultipartFile[] file) throws NoSuchAlgorithmException, IOException {
		for (MultipartFile multipartFile : file) {
			create(multipartFile);
		}
		return new ResponseEntity<>("Adicionado com sucesso", HttpStatus.ACCEPTED);
	}
	
	private void create(MultipartFile multipartFile) throws NoSuchAlgorithmException, IOException {
		Documento doc = new Documento();
		doc.setFileName(multipartFile.getOriginalFilename());
		doc.setFiletype(multipartFile.getContentType());
		doc.setSize(multipartFile.getSize());
		//doc.setHash();
		doc.setFileDownloadUri();
		//salvarDocumento(multipartFile, doc.getHash());
		documentoRepository.save(doc);
		submit(multipartFile);
	}

	private void submit(MultipartFile file){
		rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file);
		HttpEntity<MultiValueMap<String, Object>> resquestEntity = new HttpEntity<>(body,headers);
		rest.postForEntity(url, resquestEntity, String.class);
	}
	public Documento getFile(long id){
		return documentoRepository.findById(id);
	}
}
