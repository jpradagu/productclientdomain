package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenException;
import com.nttdata.bootcamp.registerproduct.model.CorporateCredit;
import com.nttdata.bootcamp.registerproduct.service.CorporateCreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/register/credit/corporate")
public class CorporateCreditController {

	private final Logger log = LoggerFactory.getLogger(CorporateCreditController.class);
	@Autowired
	private CorporateCreditService corporateCreditService;

	@GetMapping
	public Mono<ResponseEntity<Flux<CorporateCredit>>> findAll() {
		log.info("CorporateCreditController findAll ->");
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(corporateCreditService.findAll()));
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<CorporateCredit>> findById(@PathVariable String id) {
		log.info("CorporateCreditController findById ->");
		return corporateCreditService.findById(id)
				.map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> create(
			@Valid @RequestBody Mono<CorporateCredit> corporateCreditMono) {
		log.info("CorporateAccountController create ->");
		Map<String, Object> result = new HashMap<>();
		return corporateCreditMono
				.flatMap(c -> corporateCreditService.create(c)
						.map(p -> ResponseEntity
								.created(URI.create("/api/register/credit/corporate/".concat(p.getId())))
								.contentType(MediaType.APPLICATION_JSON).body(result)))
				.onErrorResume(ResumenException::errorResumenException);
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		log.info("CorporateAccountController delete ->");
		return corporateCreditService.findById(id).flatMap(
				e -> corporateCreditService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
}
