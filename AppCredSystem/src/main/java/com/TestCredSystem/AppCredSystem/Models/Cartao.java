package com.TestCredSystem.AppCredSystem.Models;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Cartao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)
	private String conta_cc;
	
	private String nome_cc;
	
	private Integer agencia_cc;

	
	
	private String data_cc;
	
	@ManyToOne
	private Usuario usuario;

	public String getNome_cc() {
		return nome_cc;
	}

	public void setNome_cc(String nome_cc) {
		this.nome_cc = nome_cc;
	}

	public Integer getAgencia_cc() {
		return agencia_cc;
	}

	public void setAgencia_cc(Integer agencia_cc) {
		this.agencia_cc = agencia_cc;
	}

	public String getConta_cc() {
		return conta_cc;
	}

	public void setConta_cc(String conta_cc) {
		this.conta_cc = conta_cc;
	}

	public String getData_cc() {
		return data_cc;
	}

	public void setData_cc(String data_cc) {
		this.data_cc = data_cc;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
