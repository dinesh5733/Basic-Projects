package net.bankingapp.service.impl;

import net.bankingapp.dto.AccountDTO;
import net.bankingapp.dto.mapper.AccountMapper;
import net.bankingapp.entity.Account;
import net.bankingapp.repository.AccountRepository;
import net.bankingapp.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {

        Account account = AccountMapper.mapToAccount(accountDTO);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO getAccountById(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists..!"));

        return AccountMapper.mapToAccountDTO(account);
    }

    @Override
    public AccountDTO deposit(Long id, Double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists..!"));

        Double totalAmount = account.getBalance() + amount;
        account.setBalance(totalAmount);

        Account saveddAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(saveddAccount);
    }

    @Override
    public AccountDTO withdraw(Long id, Double amount) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists..!"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount..!");
        }

        Double total = account.getBalance() - amount;
        account.setBalance(total);

        Account saveddAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDTO(saveddAccount);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {

        List<Account> allAccounts = accountRepository.findAll();

        return allAccounts.stream().map((allAccounts1) -> AccountMapper.mapToAccountDTO(allAccounts1))
                .collect(Collectors.toList());


    }

    @Override
    public void  deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists..!"));

        accountRepository.deleteById(id);
    }
}
