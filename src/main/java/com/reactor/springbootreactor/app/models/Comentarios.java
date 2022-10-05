package com.reactor.springbootreactor.app.models;

import java.util.ArrayList;
import java.util.List;

public class Comentarios {
	
	private List<String> comentarios = new ArrayList<>();
	
	public Comentarios() {
	
	}


	public void addComentarios(String comentario) {
		this.comentarios.add(comentario);
	}


	@Override
	public String toString() {
		return "Comentarios [comentarios=" + comentarios + "]";
	}

	
	
	
	

}
