package com.create.file.model;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "documento")
public class Documento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(name = "nome")
	private String fileName;
	
	@Column(name = "tipo")
	private String filetype;
	
	@Column(name = "size")
	private long size = 0;
	
	@Column(name = "link")
	private String fileDownloadUri;

	@Column(name = "hash", nullable = false, unique = true)
	private String hash;

	@Column(name = "dataInserido")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date data;

	@Column(name = "tipo_documento")
	private int tipo;

	@Column(name = "titulo_documento", length = 50)
	private String titulo;

	public static final int RADIX = 16;

	public void setHash() throws NoSuchAlgorithmException {
		String hashName = new StringBuilder()
				.append(this.fileName)
				.append(this.filetype)
				.append(this.size)
				.append(new Date().getTime()).toString();
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(hashName.getBytes(StandardCharsets.UTF_8));
		this.hash = new BigInteger(1, messageDigest.digest()).toString(RADIX);
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri() {
		this.fileDownloadUri = ServletUriComponentsBuilder
								.fromCurrentContextPath()
								.path("/api/downloadFile/")
								.path(this.fileName)
								.toUriString();
	}
	
}
