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

import java.time.Instant;
import java.util.*;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Override
    public List<ClientAccTxReportDTO> txByClientAndDate(Long accountId, Instant startDate, Instant endDate) {
        List<ClientAccTxReportDTO> transactions = new ArrayList<>();

        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException("Account id not found in db! : " + accountId));

        List<Transaction> txsByAccountIdByRangeDates = transactionRepository.findAllByAccountIdAndDateBetween(
                account.getId(), startDate, endDate);

        for (Transaction tx : txsByAccountIdByRangeDates) {
            ClientAccTxReportDTO reportDto = getClientAccTxReportDTO(account, tx, account.getClient());
            transactions.add(reportDto);
        }

//        txsByAccountIdByRangeDates
//                .stream()
//                .map(
//                        tx -> getClientAccTxReportDTO(account, tx, client))
//                .forEachOrdered(transactions::add);

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
