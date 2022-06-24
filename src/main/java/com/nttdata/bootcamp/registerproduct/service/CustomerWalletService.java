package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CustomerWallet;
import com.nttdata.bootcamp.registerproduct.model.PaymentWallet;
import com.nttdata.bootcamp.registerproduct.repository.CustomerWalletRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerWalletService.
 */
@Service
public class CustomerWalletService {

  @Autowired
  private CustomerWalletRepository customerWalletRepository;

  @Autowired
  private CustomerDebitCardService customerDebitCardService;

  @Autowired
  private PaymentWalletService paymentWalletService;

  /**
   * findAll CustomerWallet.
   */
  public Flux<CustomerWallet> findAll() {
    return customerWalletRepository.findAll();
  }

  /**
   * findById CustomerWallet.
   */
  public Mono<CustomerWallet> findById(String id) {
    return customerWalletRepository.findById(id);
  }

  /**
   * findByPhone CustomerWallet.
   */
  @Cacheable(value = "customerWalletFindCache")
  public Mono<CustomerWallet> findByPhone(String phone) {
    return customerWalletRepository.findByPhone(phone);
  }

  /**
   * create CustomerWallet.
   */
  public Mono<CustomerWallet> create(CustomerWallet customerWallet) {
    return customerWalletRepository.save(customerWallet);
  }

  /**
   * update CustomerWallet.
   */
  public Mono<CustomerWallet> update(CustomerWallet customerWallet, String id) {
    return customerWalletRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("Customer wallet card not found")))
        .flatMap(p -> customerWalletRepository.save(customerWallet));
  }

  /**
   * delete CustomerWallet.
   */
  public Mono<Void> delete(CustomerWallet customerWallet) {
    return customerWalletRepository.delete(customerWallet);
  }

  /**
   * create walletTransaction.
   */
  public Mono<PaymentWallet> walletTransaction(PaymentWallet paymentWallet) {
    return findByPhone(paymentWallet.getPhone()).switchIfEmpty(
            Mono.error(new RuntimeException("Phone not exist")))
        .flatMap(receiver -> findById(paymentWallet.getCustomerWalletId())
            .flatMap(emmitter -> buildPaymentTransaction(emmitter.getId(),
                paymentWallet.getAmount().negate())
                .flatMap(isSuccess -> buildPaymentTransaction(receiver.getId(),
                    paymentWallet.getAmount())
                    .flatMap(isOk -> {
                      paymentWallet.setReceiverWalletId(receiver.getId());
                      paymentWalletService.sendPayment(paymentWallet, "payment_wallet");
                      return Mono.just(paymentWallet);
                    }))));
  }

  private Mono<Boolean> buildPaymentTransaction(String customerWalletId, BigDecimal amount) {
    return customerDebitCardService
        .findByCustomerWalletId(customerWalletId)
        .flatMap(customerDebitCard -> {
          if (customerDebitCard.getAmount().compareTo(amount) < 0) {
            return Mono.error(new RuntimeException("Insufficient amount"));
          }
          customerDebitCard.setAmount(customerDebitCard.getAmount().add(amount));
          return customerDebitCardService.update(customerDebitCard, customerDebitCard.getId())
              .flatMap(debitCardTx -> Mono.just(true));
        });
  }
}
