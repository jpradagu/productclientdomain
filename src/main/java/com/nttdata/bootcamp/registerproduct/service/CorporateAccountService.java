package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CorporateAccount;
import com.nttdata.bootcamp.registerproduct.repository.CorporateAccountRepository;
import com.nttdata.bootcamp.registerproduct.response.CorporateAccountResponse;
import com.nttdata.bootcamp.registerproduct.response.CustomerResponse;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import com.nttdata.bootcamp.registerproduct.service.external.dto.CommercialCustomer;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CorporateAccount service.
 */
@Service
@Slf4j
public class CorporateAccountService {

  public static final Integer MINOR = -1;

  @Autowired
  private CorporateAccountRepository corporateAccountRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;

  /**
   * build CustomerResponse from CommercialCustomer.
   */
  private CustomerResponse buildFromCommercial(CommercialCustomer commercialCustomer) {
    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setDocumentNumber(commercialCustomer.getRuc());
    customerResponse.setName(commercialCustomer.getReasonSocial());
    customerResponse.setEmail(commercialCustomer.getEmail());
    customerResponse.setId(commercialCustomer.getId());
    customerResponse.setPhone(commercialCustomer.getPhone());
    customerResponse.setType("RUC");
    return customerResponse;
  }

  /**
   * build Corporate Account Response.
   */
  public Mono<CorporateAccountResponse> buildCorporateAccount(CorporateAccount account,
                                                              TypeAccount typeAccount,
                                                              CommercialCustomer commercial) {
    CorporateAccountResponse c = new CorporateAccountResponse();
    c.setAccountNumber(account.getAccountNumber());
    c.setTypeAccount(typeAccount);
    c.setAmount(account.getAmount());
    c.setCode(account.getCode());
    c.setCustomer(buildFromCommercial(commercial));
    c.setHolders(account.getHolders());
    c.setId(account.getId());
    c.setOpeningDate(account.getOpeningDate());
    c.setSigners(account.getSigners());
    c.setState(account.getState());
    return Mono.just(c);
  }


  /**
   * findAll Corporate account response.
   */
  public Flux<CorporateAccountResponse> findAll() {
    log.info("CorporateAccountService findAll ->");
    return corporateAccountRepository.findAll()
        .flatMap(this::buildCorporateResponse);
  }


  /**
   * find CorporateAccount by id.
   */
  public Mono<CorporateAccount> findById(String id) {
    log.info("CorporateAccountService findById ->");
    return corporateAccountRepository.findById(id);
  }

  /**
   * Create corporate account.
   */
  public Mono<CorporateAccount> create(CorporateAccount corporateAccount) {
    log.info("CorporateAccountService create ->");
    return externalAccountService
        .findCustomerCommercialById(corporateAccount.getCustomerId())
        .flatMap(customer -> externalProductService
            .findTypeAccountById(corporateAccount.getAccountId())
            .flatMap(typeAccount -> {
              if (typeAccount.getAllowCompany().equals(true)) {
                if (corporateAccount.getAmount()
                    .compareTo(typeAccount.getMinimumOpeningAmount()) == MINOR) {
                  return Mono.error(
                      new RuntimeException("The corporate account requires a minimum amount"));
                }
                return corporateAccountRepository.save(corporateAccount);
              } else {
                return Mono.error(
                    new RuntimeException("Bank account not allowed for commercial account"));
              }
            })
        );
  }

  /**
   * delete corporateAccount.
   */
  public Mono<Void> delete(CorporateAccount corporateAccount) {
    log.info("CorporateAccountService delete ->");
    return corporateAccountRepository.delete(corporateAccount);
  }

  /**
   * build CorporateResponse.
   */
  public Mono<CorporateAccountResponse> buildCorporateResponse(CorporateAccount corporateAccount) {
    return Mono.just(corporateAccount).flatMap(
        account -> externalAccountService.findCustomerCommercialById(account.getCustomerId())
            .flatMap(commercial -> externalProductService
                .findTypeAccountById(account.getAccountId())
                .flatMap(typeAccount -> buildCorporateAccount(account, typeAccount,
                    commercial))));
  }
}
