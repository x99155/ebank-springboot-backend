package com.process.ebank.web;

import com.process.ebank.dto.*;
import com.process.ebank.service.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AccountOperationRestController {

    private final AccountOperationService accountOperationService;

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDto> getHistory(@PathVariable String accountId) {
        return this.accountOperationService.accountHistory(accountId);
    }

    /* pagination
    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDto getHistoryHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        return accountOperationService.getAccountHistory(accountId, page, size);
    }

     */

    @PostMapping("/accounts/debit")
    public void debit(@RequestBody DebitDto debitDto) {
        this.accountOperationService.debit(debitDto.getAccountId(), debitDto.getAmount(), debitDto.getDescription());
    }

    @PostMapping("/accounts/credit")
    public void credit(@RequestBody CreditDto creditDto) {
        this.accountOperationService.credit(creditDto.getAccountId(), creditDto.getAmount(), creditDto.getDescription());
    }

    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TranferRequestDto tranferRequestDto) {
        System.out.println("**********************************");
        System.out.println(tranferRequestDto.getAccountIdSource());
        System.out.println(tranferRequestDto.getAccountIdDestination());
        System.out.println(tranferRequestDto.getAmount());
        System.out.println("**********************************");

        this.accountOperationService.transfer(tranferRequestDto.getAccountIdSource(), tranferRequestDto.getAccountIdDestination(), tranferRequestDto.getAmount());
    }


}
