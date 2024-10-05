package com.process.ebank;

import com.process.ebank.dto.CurrentAccountDto;
import com.process.ebank.dto.CustomerDto;
import com.process.ebank.dto.SavingAccountDto;
import com.process.ebank.entity.AccountOperation;
import com.process.ebank.entity.CurrentAccount;
import com.process.ebank.entity.Customer;
import com.process.ebank.entity.SavingAccount;
import com.process.ebank.enums.AccountStatus;
import com.process.ebank.enums.OperationType;
import com.process.ebank.exception.BalanceNotSufficentException;
import com.process.ebank.exception.BankAccountNotFoundException;
import com.process.ebank.exception.CustomerNotFoundException;
import com.process.ebank.repository.AccountOperationRepository;
import com.process.ebank.repository.BankAccountRepository;
import com.process.ebank.repository.CustomerRepository;
import com.process.ebank.service.AccountOperationService;
import com.process.ebank.service.BankAccountService;
import com.process.ebank.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CustomerService customerService,
						   BankAccountService bankAccountService,
						   AccountOperationService accountOperationService) {

		return args -> {
			// Je crée trois customers
			Stream.of("Boris", "Juana", "Yven", "Sophie", "Nina")
					.forEach(name -> {
						CustomerDto customer = new CustomerDto();
						customer.setName(name);
						customer.setEmail(name.toLowerCase()+"@gmail.com");
						customerService.saveCustomer(customer);
					});

			// Pour chaque customer je crée un compte current et saving
			customerService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random() * 900, 200, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random() * 1200, 2.5, customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});

			// Pour chaque compte je crée 10 opération de débit et de crédit
			bankAccountService.bankAccountList().forEach(bankAccount -> {
				try {

					for (int i = 0; i < 5; i++) {
						String accountId;
						if (bankAccount instanceof SavingAccountDto) {
							accountId = ((SavingAccountDto) bankAccount).getId();
						} else {
							accountId = ((CurrentAccountDto) bankAccount).getId();
						}

						System.out.println("************************************");
						System.out.println(accountId);
						System.out.println("************************************");

						accountOperationService.credit(accountId, 100 + Math.random() * 1200, "Transfer to ...");
						accountOperationService.debit(accountId, 100 + Math.random() * 200, "Transfer from ...");
					}

				} catch (BankAccountNotFoundException | BalanceNotSufficentException e) {
					e.printStackTrace();
				}
			});
		};
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Boris", "Juana", "Yven")
					.forEach(name -> {
						Customer customer = new Customer();
						customer.setName(name);
						customer.setEmail(name.toLowerCase()+"@gmail.com");
						customerRepository.save(customer);
					});

			customerRepository.findAll().forEach(customer -> {

				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 900);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(customer);
				currentAccount.setOverDraft(200);
				currentAccount.setCreatedAt(new Date());
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 900);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(customer);
				savingAccount.setInterestRate(2.5);
				savingAccount.setCreatedAt(new Date());
				bankAccountRepository.save(savingAccount);
			});

			bankAccountRepository.findAll().forEach(bankAccount -> {
				for (int i = 0; i < 5; i++) {
					AccountOperation operation = new AccountOperation();
					operation.setOperationDate(new Date());
					operation.setAmount(Math.random() * 1200);
					operation.setType(Math.random() > 0.5? OperationType.DEBIT: OperationType.CREDIT);
					operation.setBankAccount(bankAccount);
					operation.setDescription("from ... to ...");
					accountOperationRepository.save(operation);
				}
			});


		};
	}

}
