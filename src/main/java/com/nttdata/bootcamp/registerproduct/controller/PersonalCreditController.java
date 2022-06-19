package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.entity.PersonalCredit;
import com.nttdata.bootcamp.registerproduct.exception.ResumenException;
import com.nttdata.bootcamp.registerproduct.service.PersonalCreditService;
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
@RequestMapping("/api/register/credit/personal")
public class PersonalCreditController {
    private final Logger log = LoggerFactory.getLogger(PersonalCreditController.class);

    @Autowired
    private PersonalCreditService creditService;

    @GetMapping
    public Mono<ResponseEntity<Flux<PersonalCredit>>> findAll(){
        log.info("PersonalCreditController findAll ->");
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(creditService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PersonalCredit>> findById(@PathVariable String id) {
        log.info("PersonalCreditController findById ->");
        return creditService.findById(id)
                .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<PersonalCredit> customerCreditMono) {
        log.info("PersonalCreditController create ->");
        Map<String, Object> result = new HashMap<>();
        return customerCreditMono.flatMap(c -> creditService.create(c).map(p -> ResponseEntity
                .created(URI.create("/api/register/credit/personal/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result))).onErrorResume(ResumenException::errorResumenException);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("PersonalCreditController delete ->");
        return creditService.findById(id)
                .flatMap(e -> creditService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
