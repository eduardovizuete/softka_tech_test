package com.job.micro.accounttx.service.impl;

import com.job.micro.accounttx.dto.ClientAccTxReportDTO;
import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Client;
import com.job.micro.accounttx.entity.Transaction;
import com.job.micro.accounttx.exception.AccountIdNotFoundException;
import com.job.micro.accounttx.repository.AccountRepository;
import com.job.micro.accounttx.repository.TransactionRepository;
import com.job.micro.accounttx.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    public static final String ACCOUNT_ID_NOT_FOUND_IN_DB = "Account id not found in db! : ";

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Override
    public List<ClientAccTxReportDTO> txByClientAndDate(Long accountId, Instant startDate, Instant endDate) {
        List<ClientAccTxReportDTO> transactions = new ArrayList<>();

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(ACCOUNT_ID_NOT_FOUND_IN_DB + accountId));

        List<Transaction> txsByAccountIdByRangeDates = transactionRepository.findAllByAccountIdAndDateBetween(
                account.getId(), startDate, endDate);

        txsByAccountIdByRangeDates
                .stream()
                .map(
                        tx -> getClientAccTxReportDTO(account, tx, account.getClient()))
                .forEachOrdered(transactions::add);

        return transactions;
    }

    private static ClientAccTxReportDTO getClientAccTxReportDTO(Account account, Transaction tx, Client client) {
        ClientAccTxReportDTO reportDto = new ClientAccTxReportDTO();
        reportDto.setDate(tx.getDate());
        reportDto.setName(client.getName());
        reportDto.setNumberAcc(account.getNumber());
        reportDto.setTypeAcc(account.getType());
        reportDto.setBalanceBeforeTx(tx.getBalanceBeforeTx());
        reportDto.setStatus(account.getStatus());
        reportDto.setAmount(tx.getAmount());
        reportDto.setTypeTx(tx.getType());
        reportDto.setBalanceTx(tx.getBalance());
        return reportDto;
    }

}
