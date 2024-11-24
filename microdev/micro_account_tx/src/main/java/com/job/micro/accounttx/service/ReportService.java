package com.job.micro.accounttx.service;

import com.job.micro.accounttx.dto.ClientAccTxReportDTO;

import java.time.Instant;
import java.util.List;

public interface ReportService {

    List<ClientAccTxReportDTO> txByClientAndDate(Long clientId, Instant startDate, Instant endDate);

}
