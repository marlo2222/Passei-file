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
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.create.file.model.Documento;
import com.create.file.repository.DocumentoRepository;
import com.create.file.services.DocumentoService;

@RestController
@RequestMapping("/api/documento")
@Api(value = "API de documentos")
public class DocumentoController {
	
	@Autowired
	DocumentoService documentoService;

	@Autowired
	DocumentoRepository documentoRepository;

	@GetMapping("/")
	public String home(){
		return "/swagger-ui.html#!/";
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ApiOperation(value = "Recebe um ou varios arquivos")
	public ResponseEntity<?> addDocumento(@RequestBody Documento documento){
		return documentoService.addDocumentos(documento);
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	@ApiOperation(value = "retorna todos os arquivos")
	public ResponseEntity<?> listar(){
		return new ResponseEntity<>(documentoRepository.findAll(), HttpStatus.OK);

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
