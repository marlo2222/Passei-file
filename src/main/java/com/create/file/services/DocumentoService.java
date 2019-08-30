package com.create.file.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

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
	public void addDocumentos(MultipartFile[] file) throws NoSuchAlgorithmException {
		for (MultipartFile multipartFile : file) {
			create(multipartFile);
		}
	}
	
	private void create(MultipartFile multipartFile) throws NoSuchAlgorithmException {
		Documento doc = new Documento();
		doc.setNome(multipartFile.getOriginalFilename());
		doc.setTipoArquivo(multipartFile.getContentType());
		doc.setSize(multipartFile.getSize());
		doc.setHash();
		doc.setLinkDownload();
		try {
			doc.setData(multipartFile.getBytes());
		} catch (IOException e) {
				e.printStackTrace();
		}
		documentoRepository.save(doc);
	}
	
	public Documento getFile(long id){
		return documentoRepository.findById(id);
	}
}
