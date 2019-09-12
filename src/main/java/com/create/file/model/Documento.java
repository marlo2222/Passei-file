package com.create.file.model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Documento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(name = "nome")
	private String 	nome;
	
	@Column(name = "tipo")
	private String tipoArquivo;
	
	@Column(name = "size")
	private long size = 0;
	
	@Column(name = "link")
	private String linkDownload;
		
	@Column(name = "hash", nullable = false, unique = true)
	private String hash;
		



	public static final int RADIX = 16;

	public long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}


	public String getTipoArquivo() {
		return tipoArquivo;
	}


	public long getSize() {
		return size;
	}


	public static int getRadix() {
		return RADIX;
	}
	

	public String getHash() {
		return hash;
	}
	
	public void setId(long id) {
		this.id = id;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setHash() throws NoSuchAlgorithmException {
		String hashName = new StringBuilder()
				.append(this.nome)
				.append(this.tipoArquivo)
				.append(this.size)
				.append(new Date().getTime()).toString();
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(hashName.getBytes(StandardCharsets.UTF_8));
		this.hash = new BigInteger(1, messageDigest.digest()).toString(RADIX);
	}


	public String getLinkDownload() {
		return linkDownload;
	}

	public void setLinkDownload() {
		this.linkDownload = ServletUriComponentsBuilder
								.fromCurrentContextPath()
								.path("/api/documento/download/")
								.path(this.hash)
								.toUriString();
	}
	
}
