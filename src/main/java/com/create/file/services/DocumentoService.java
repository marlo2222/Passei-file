package com.create.file.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import com.create.file.configuration.DocumentServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.create.file.model.Documento;
import com.create.file.repository.DocumentoRepository;

@Service
public class DocumentoService {
	
	@Autowired
	DocumentoRepository documentoRepository;


	@Transactional
	public void addDocumentos(MultipartFile[] file) throws NoSuchAlgorithmException, IOException {
		for (MultipartFile multipartFile : file) {
			create(multipartFile);
		}
	}
	
	private void create(MultipartFile multipartFile) throws NoSuchAlgorithmException, IOException {
		Documento doc = new Documento();
		doc.setNome(multipartFile.getOriginalFilename());
		doc.setTipoArquivo(multipartFile.getContentType());
		doc.setSize(multipartFile.getSize());
		doc.setHash();
		//doc.setLinkDownload();
		salvarDocumento(multipartFile, doc.getHash());
		documentoRepository.save(doc);
	}

	private void salvarDocumento(MultipartFile multipartFile, String hash) throws IOException {
		Path localDestino = DocumentServiceImpl.docStorageLocation.resolve(hash);
		Files.copy(multipartFile.getInputStream(), localDestino);
	}

	public Documento getFile(long id){
		return documentoRepository.findById(id);
	}
}
