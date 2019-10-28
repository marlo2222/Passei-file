package com.create.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.create.file.repository.DocumentoRepository;
import com.create.file.services.DocumentoService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/")
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
	
	@RequestMapping(value = "documento/adicionar/",method = RequestMethod.POST)
	@ApiOperation(value = "Recebe um ou varios arquivos")
	public ResponseEntity<?> addDocumento(@RequestParam("usuario") long usuario,@RequestParam("disciplina") long disciplina,
	 @RequestParam("titulo") String titulo, @RequestParam("tipo") int tipo, @RequestParam("arquivo")MultipartFile[] file) throws IOException, NoSuchAlgorithmException {
		return documentoService.addDocumentos(usuario, disciplina, titulo, tipo, file);
	}

	@RequestMapping(value = "/listar/usuario/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna os documentos de um usuario")
	public ResponseEntity<?> listarDocumentosIdUsuario(@PathVariable("id") long id){
		return documentoService.listarDocumentosUsuario(id);
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	@ApiOperation(value = "retorna todos os arquivos")
	public ResponseEntity<?> listar(){
		return new ResponseEntity<>(documentoRepository.findAll(), HttpStatus.OK);

	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = documentoService.download(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
		}

		// Fallback to the default content type if type could not be determined
		if(contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@DeleteMapping("delete/{id}")
	@ApiOperation(value = "deletar um arquivo")
	public ResponseEntity<?> deleteFile(@PathVariable("id") long id) throws IOException{
		return documentoService.delete(id);
	}
}
