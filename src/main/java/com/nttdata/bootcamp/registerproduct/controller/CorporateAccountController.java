package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.entity.CorporateAccount;
import com.nttdata.bootcamp.registerproduct.exception.ResumenException;
import com.nttdata.bootcamp.registerproduct.service.CorporateAccountService;
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
@RequestMapping("/api/register/account/corporate")
public class CorporateAccountController {

    private final Logger log= LoggerFactory.getLogger(CorporateAccountController.class);
    @Autowired
    private CorporateAccountService corporateAccountService;

    @GetMapping
    public Mono<ResponseEntity<Flux<CorporateAccount>>> findAll(){
        log.info("CorporateAccountController findAll ->");
        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(corporateAccountService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CorporateAccount>> findById(@PathVariable String id) {
        log.info("CorporateAccountController findById ->");
        return corporateAccountService.findById(id)
                .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<CorporateAccount> accountBankMono) {
        log.info("CorporateAccountController create ->");
        Map<String, Object> result = new HashMap<>();
        return accountBankMono.flatMap(c -> corporateAccountService.create(c).map(p -> ResponseEntity
                .created(URI.create("/api/register/account/corporate/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result))).onErrorResume(ResumenException::errorResumenException);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        log.info("CorporateAccountController delete ->");
        return corporateAccountService.findById(id)
                .flatMap(e -> corporateAccountService.delete(e).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
