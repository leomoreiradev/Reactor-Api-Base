package com.reactor.springbootreactor.app;

import java.awt.Adjustable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactor.springbootreactor.app.models.Comentarios;
import com.reactor.springbootreactor.app.models.Usuario;
import com.reactor.springbootreactor.app.models.UsuarioComentarios;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//exemploInterable();
		//exemploFlatMap();
		//exemploToString();
		//exemploCollectList();
		//exemploUsuarioComentariosFlaMap();
		//exemploUsuarioComentariosZipWith();
		//exemploUsuarioComentariosZipWithForma2();
		//exemploUsuarioComentariosZipWithRange();
		//exemploInterval();
		 exemplodelayElements();
		
	}
	
//Usando o delayElements	
public void exemplodelayElements() {
		Flux<Integer> range = Flux.range(1, 12)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> log.info(i.toString()));
			range.blockLast();
	}


//Usando o interval	
public void exemploInterval() {
	Flux<Integer> range = Flux.range(1, 12);
	Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));
	
	range.zipWith(delay, (r,d)-> r)
	.doOnNext(i -> log.info(i.toString()))
	.blockLast();
		
}
	
	
//Usandoo range
public void exemploUsuarioComentariosZipWithRange() {
	Flux.just(1,2,3,4)
	.map(i -> (i*2))
	.zipWith(Flux.range(0,4), (um, dois)-> String.format("Primeiros Fluxo: %d, Segundo Fluxo %d", um, dois))
	.subscribe(texto -> log.info(texto));
		
}

//Unindo 1° FLUXO e 2° FLUXO com o zipWith forma 2	
public void exemploUsuarioComentariosZipWithForma2() {
		Mono<Usuario> usuarioMono = Mono.fromCallable(()-> new Usuario("John", "Doe") ); //1° FLUXO
		
		Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(()-> { //2° FLUXO
			Comentarios comentarios = new Comentarios();
			comentarios.addComentarios("Comentario 1");
			comentarios.addComentarios("Comentario 2");
			comentarios.addComentarios("Comentario 3");
			
			return comentarios;
		});
		
		//Unindo 1° FLUXO e 2° FLUXO com o zipWith
		Mono<UsuarioComentarios> usuarioComComentarios = usuarioMono
				.zipWith(comentariosUsuarioMono)
				.map(tuple -> {
					Usuario u = tuple.getT1();
					Comentarios c = tuple.getT2();
					return new UsuarioComentarios(u, c);
				});
		
		usuarioComComentarios.subscribe(uc -> log.info("Unindo 1° FLUXO e 2° FLUXO com o zipWith forma 2: {} ", uc.toString()));
	}		

	
//Unindo 1° FLUXO e 2° FLUXO com o zipWith	
public void exemploUsuarioComentariosZipWith() {
		Mono<Usuario> usuarioMono = Mono.fromCallable(()-> new Usuario("John", "Doe") ); //1° FLUXO
		
		Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(()-> { //2° FLUXO
			Comentarios comentarios = new Comentarios();
			comentarios.addComentarios("Comentario 1");
			comentarios.addComentarios("Comentario 2");
			comentarios.addComentarios("Comentario 3");
			
			return comentarios;
		});
		
		//Unindo 1° FLUXO e 2° FLUXO com o zipWith
		Mono<UsuarioComentarios> usuarioComComentarios = usuarioMono.zipWith(comentariosUsuarioMono, (usuario, comentariosUsuario) -> new UsuarioComentarios(usuario, comentariosUsuario));
		
		usuarioComComentarios.subscribe(uc -> log.info("Unindo 1° FLUXO e 2° FLUXO com o zipWith: {} ", uc.toString()));
	}	
	
//Unindo dois fluxos	
public void exemploUsuarioComentariosFlaMap() {
	Mono<Usuario> usuarioMono = Mono.fromCallable(()-> new Usuario("John", "Doe") );
	
	Mono<Comentarios> comentariosUsuarioMono = Mono.fromCallable(()-> {
		Comentarios comentarios = new Comentarios();
		comentarios.addComentarios("Comentario 1");
		comentarios.addComentarios("Comentario 2");
		comentarios.addComentarios("Comentario 3");
		
		return comentarios;
	});
	
	
	usuarioMono.flatMap(u -> comentariosUsuarioMono.map(c -> new UsuarioComentarios(u, c)))
	.subscribe(uc -> log.info(uc.toString()));
}


//transformando um flux em mono
public void exemploCollectList() throws Exception {
		
		List<Usuario> usuariosList = new ArrayList<>();
		
		usuariosList.add(new Usuario("Andres", "Guzman"));
		usuariosList.add(new Usuario("Pedro", "Fulano"));
		usuariosList.add(new Usuario("Andres", "Guzman"));
		usuariosList.add(new Usuario("Maria", "Fulana"));
		usuariosList.add(new Usuario("Diego", "Sultano"));
		usuariosList.add(new Usuario("Juan", "Mengano"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));
		
		
		Flux.fromIterable(usuariosList)
				.collectList() // tranforma flux em mono
				.subscribe(lista -> {
					lista.forEach(item -> log.info(item.toString()));
				});
		
	}
	

//mudando de um fluxo para string
public void exemploToString() throws Exception {
		
		List<Usuario> usuariosList = new ArrayList<>();
		
		usuariosList.add(new Usuario("Andres", "Guzman"));
		usuariosList.add(new Usuario("Pedro", "Fulano"));
		usuariosList.add(new Usuario("Andres", "Guzman"));
		usuariosList.add(new Usuario("Maria", "Fulana"));
		usuariosList.add(new Usuario("Diego", "Sultano"));
		usuariosList.add(new Usuario("Juan", "Mengano"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));
		
		
		Flux.fromIterable(usuariosList)
				.map(usuario -> usuario.getNome().toUpperCase().concat(" ").concat(usuario.getSobreNome().toUpperCase()))
				.flatMap(nome -> 
				    nome.contains("bruce".toUpperCase()) ? Mono.just(nome) : Mono.empty()
				)
				.map(nome -> {
					return nome.toLowerCase();
				}).subscribe(u -> log.info(u.toString()));
		
	}


//usando flatmap
public void exemploFlatMap() throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
		
		usuariosList.add("Andres Guzman");
		usuariosList.add("Pedro Fulano");
		usuariosList.add("Andres Guzman");
		usuariosList.add("Maria Fulana");
		usuariosList.add("Diego Sultano");
		usuariosList.add("Juan Mengano");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");
		
		
		Flux.fromIterable(usuariosList)
				.map(nome -> new Usuario(nome.split(" ")[0].toUpperCase(), nome.split(" ")[1].toUpperCase()))
				.flatMap(usuario -> 
					usuario.getNome().equalsIgnoreCase("bruce") ? Mono.just(usuario) : Mono.empty()
//					if(usuario.getNome().equalsIgnoreCase("bruce")) {
//						return Mono.just(usuario);
//					} else {
//						return Mono.empty();
//					}

	
				)
				.map(usuario -> {
					String nome = usuario.getNome().toLowerCase();
					usuario.setNome(nome);
					return usuario;
				}).subscribe(u -> log.info(u.toString()));
		
	}


//criando um flux atraves de um  Flux.fromIterable
public void exemploInterable() throws Exception {
		
		List<String> usuariosList = new ArrayList<>();
		
		usuariosList.add("Andres Guzman");
		usuariosList.add("Pedro Fulano");
		usuariosList.add("Andres Guzman");
		usuariosList.add("Maria Fulana");
		usuariosList.add("Diego Sultano");
		usuariosList.add("Juan Mengano");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");
		
		
		Flux<String> nomes = Flux.fromIterable(usuariosList); //Flux.just("Andres Guzman", "Pedro Fulano", "Maria Fulana", "Diego Sultano", "Juan Mengano", "Bruce Lee", "Bruce Willis");
				//.doOnNext(elemento -> System.out.println(elemento));
				Flux<Usuario> usuarios = nomes.map(nome -> new Usuario(nome.split(" ")[0].toUpperCase(), nome.split(" ")[1].toUpperCase()))
				.filter(usuario -> usuario.getNome().toLowerCase().equals("bruce"))
				.doOnNext(usuario -> {
					if(usuario == null) {
						throw new RuntimeException("Nomes não podem ser vazios");
					}
					
					System.out.println(usuario.getNome().concat(" ").concat(usuario.getSobreNome()));
					
				}).map(usuario -> {
					String nome = usuario.getNome().toLowerCase();
					usuario.setNome(nome);
					return usuario;
				});
				
		
			usuarios.subscribe(e -> log.info(e.toString()),error -> log.error(error.getMessage()), new Runnable() {
			
			@Override
			public void run() {
				log.info("Finalizado a execução do observavel com exito");
				
			}
		});
		
	}

}
