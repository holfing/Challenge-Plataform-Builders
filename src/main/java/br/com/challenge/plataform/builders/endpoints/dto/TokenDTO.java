package br.com.challenge.plataform.builders.endpoints.dto;

public class TokenDTO {

	private String token;
	private String tipo;

	public TokenDTO(String token, String tipo) {

		this.token = token;
		this.tipo = tipo;
	}

}
