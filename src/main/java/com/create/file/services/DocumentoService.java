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

@Service
public class DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	FileStorageService fileStorageService;

	/*public ResponseEntity<?> adicionar(Documento documento){
		documentoRepository.save(documento);
		return new ResponseEntity<>("Deu certo", HttpStatus.CREATED);
	}*/


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
		doc.setFileDownloadUri();
		//salvarDocumento(multipartFile, doc.getHash());
		documentoRepository.save(doc);
		fileStorageService.create(multipartFile);
		//submit(multipartFile);
	}

	public Resource download(String filename){
		return fileStorageService.loadFileAsResource(filename);
	}

	/*private void submit(MultipartFile file){
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
	}*/
}
