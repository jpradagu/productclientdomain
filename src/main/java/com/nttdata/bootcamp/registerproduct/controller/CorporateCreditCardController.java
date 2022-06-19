package com.nttdata.bootcamp.registerproduct.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.registerproduct.exception.ResumenException;
import com.nttdata.bootcamp.registerproduct.model.CorporateCreditCard;
import com.nttdata.bootcamp.registerproduct.service.CorporateCreditCardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/register/credit-card/corporate")
public class CorporateCreditCardController {

	private final Logger log = LoggerFactory.getLogger(CorporateCreditCardController.class);
	@Autowired
	private CorporateCreditCardService corporateCreditCardService;

	@GetMapping
	public Mono<ResponseEntity<Flux<CorporateCreditCard>>> findAll() {
		log.info("CorporateCreditCardController findAll ->");
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(corporateCreditCardService.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<CorporateCreditCard>> findById(@PathVariable String id) {
		log.info("CorporateCreditCardController findById ->");
		return corporateCreditCardService.findById(id)
				.map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> create(
			@Valid @RequestBody Mono<CorporateCreditCard> corporateCreditCardMono) {
		log.info("CorporateCreditCardController create ->");
		Map<String, Object> result = new HashMap<>();
		return corporateCreditCardMono
				.flatMap(c -> corporateCreditCardService.create(c)
						.map(p -> ResponseEntity
								.created(URI.create("/api/register/credit-card/corporate/".concat(p.getId())))
								.contentType(MediaType.APPLICATION_JSON).body(result)))
				.onErrorResume(ResumenException::errorResumenException);
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		log.info("CorporateCreditCardController delete ->");
		return corporateCreditCardService.findById(id)
				.flatMap(e -> corporateCreditCardService.delete(e)
						.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
