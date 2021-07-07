package com.jokenpo.demo.exception;

public class NegocioException  extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NegocioException(String mensagens) {
		super(mensagens);
	}
}
