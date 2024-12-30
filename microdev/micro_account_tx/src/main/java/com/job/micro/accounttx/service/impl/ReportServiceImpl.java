package com.job.micro.accounttx.service.impl;

import com.job.micro.accounttx.dto.ClientAccTxReportDTO;
import com.job.micro.accounttx.entity.Account;
import com.job.micro.accounttx.entity.Transaction;
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

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Override
    public List<ClientAccTxReportDTO> txByClientAndDate(Long clientId, Instant startDate, Instant endDate) {
        List<ClientAccTxReportDTO> transactions = new ArrayList<>();

        List<Account> accounts = accountRepository.findAllAccountsByClientId(clientId);

        accounts.forEach(account -> {
            List<Transaction> txsByAccountIdByRangeDates = transactionRepository.findAllByAccountIdAndDateBetween(
                    account.getId(), startDate, endDate);

            txsByAccountIdByRangeDates
                    .stream()
                    .map(this::getClientAccTxReportDTO)
                    .forEachOrdered(transactions::add);
        });

        return transactions;
    }

    private ClientAccTxReportDTO getClientAccTxReportDTO(Transaction tx) {
        ClientAccTxReportDTO reportDto = new ClientAccTxReportDTO();
        reportDto.setDate(tx.getDate());
        reportDto.setName(tx.getAccount().getClient().getName());
        reportDto.setNumberAcc(tx.getAccount().getNumber());
        reportDto.setTypeAcc(tx.getAccount().getType());
        reportDto.setBalanceBeforeTx(tx.getBalanceBeforeTx());
        reportDto.setStatus(tx.getAccount().getStatus());
        reportDto.setAmount(tx.getAmount());
        reportDto.setTypeTx(tx.getType());
        reportDto.setBalanceTx(tx.getBalance());
        return reportDto;
    }

}
