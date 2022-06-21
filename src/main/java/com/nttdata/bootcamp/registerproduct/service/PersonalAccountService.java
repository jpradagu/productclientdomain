package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.PersonalAccount;
import com.nttdata.bootcamp.registerproduct.repository.PersonalAccountRepository;
import com.nttdata.bootcamp.registerproduct.response.CustomerResponse;
import com.nttdata.bootcamp.registerproduct.response.PersonalAccountResponse;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import com.nttdata.bootcamp.registerproduct.service.external.dto.PersonalCustomer;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service PersonalAccount.
 */
@Service
@Slf4j
public class PersonalAccountService {

  public static final Integer MINOR = -1;

  @Autowired
  private PersonalAccountRepository personalAccountRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;

  /**
   * build from personalCustomer.
   */
  private CustomerResponse buildFromPersonal(PersonalCustomer personal) {
    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setDocumentNumber(personal.getDni());
    customerResponse.setEmail(personal.getEmail());
    customerResponse.setId(personal.getId());
    customerResponse.setName(personal.getName());
    customerResponse.setPhone(personal.getPhone());
    customerResponse.setType("DNI");
    return customerResponse;
  }

  /**
   * build from personalAccount.
   */
  private Mono<PersonalAccountResponse> buildPersonalAccount(PersonalAccount account,
                                                             TypeAccount typeAccount,
                                                             PersonalCustomer customer) {
    PersonalAccountResponse p = new PersonalAccountResponse();
    p.setAccountNumber(account.getAccountNumber());
    p.setAccount(typeAccount);
    p.setAmount(account.getAmount());
    p.setCode(account.getCode());
    p.setCustomer(buildFromPersonal(customer));
    p.setId(account.getId());
    p.setOpeningDate(account.getOpeningDate());
    p.setState(account.getState());
    return Mono.just(p);
  }

  /**
   * findAll PersonalAccountResponse.
   */
  public Flux<PersonalAccountResponse> findAll() {
    log.info("PersonalAccountService findAll ->");
    return personalAccountRepository.findAll()
        .flatMap(account -> externalAccountService.findCustomerPersonalById(
                account.getCustomerId())
            .flatMap(customer -> externalProductService.findTypeAccountById(account.getAccountId())
                .flatMap(typeAccount -> buildPersonalAccount(account, typeAccount, customer))));
  }

  /**
   * find PersonalAccount.
   */
  public Mono<PersonalAccount> findById(String id) {
    log.info("PersonalAccountService findById ->");
    return personalAccountRepository.findById(id);
  }

  /**
   * create personal account.
   */
  public Mono<PersonalAccount> create(PersonalAccount personalAccount) {
    log.info("PersonalAccountService create ->");
    return externalAccountService
        .findCustomerPersonalById(personalAccount.getCustomerId())
        .flatMap(customer -> externalProductService
            .findTypeAccountById(personalAccount.getAccountId())
            .flatMap(typeAccount -> personalAccountRepository
                .findByCustomerId(customer.getId())
                .collectList().flatMap(lst -> {
                  if (typeAccount.getAllowPerson().equals(true)) {
                    if (!lst.isEmpty()) {
                      return Mono.error(
                          new RuntimeException("Only one bank account per person is allowed"));
                    }
                    if (personalAccount.getAmount()
                        .compareTo(typeAccount.getMinimumOpeningAmount()) == MINOR) {
                      return Mono.error(
                          new RuntimeException("The personal account requires a minimum amount"));
                    }
                    return personalAccountRepository.save(personalAccount);
                  } else {
                    return Mono.error(
                        new RuntimeException("Bank account not allowed for personal customer"));
                  }
                })));
  }

  /**
   * delete personal account.
   */
  public Mono<Void> delete(PersonalAccount personalAccount) {
    log.info("AccountBankService delete ->");
    return personalAccountRepository.delete(personalAccount);
  }


}
