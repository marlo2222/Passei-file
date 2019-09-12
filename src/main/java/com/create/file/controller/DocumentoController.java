package com.create.file.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.create.file.model.Documento;
import com.create.file.repository.DocumentoRepository;
import com.create.file.services.DocumentoService;

@RestController
@RequestMapping("/api/documento")
@Api(value = "API de documentos")
//@CrossOrigin("*/")
public class DocumentoController {
	
	@Autowired
	DocumentoService documentoService;

	@GetMapping("/")
	public String home(){
		return "/swagger-ui.html#!/";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST, consumes = "multipart/form-data")
	@ApiOperation(value = "Recebe um ou varios arquivos")
	public void addDocumento(@RequestParam("documento") MultipartFile[] multipartFiles) throws NoSuchAlgorithmException, IOException {
		
		documentoService.addDocumentos(multipartFiles);
	}
	
/*	@GetMapping(value = "/download/{id}")
	@ApiOperation(value = "download de um arquivo		")
	public HttpEntity<byte[]> downloadFile(@PathVariable("id") long id)throws IOException{
		
		Documento doc = documentoService.getFile(id);

		
		HttpHeaders header = new HttpHeaders();
		
		header.add("Content-Disposition", "attachment;filename=\""+ doc.getNome() +"\"");
		
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, header);
		
		return entity;
	}*/
}
