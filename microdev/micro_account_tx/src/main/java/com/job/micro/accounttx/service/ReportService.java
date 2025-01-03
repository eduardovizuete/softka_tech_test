package com.job.micro.accounttx.service;

import com.job.micro.accounttx.dto.ClientAccTxReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    List<ClientAccTxReportDTO> txByClientAndDate(Long clientId, LocalDateTime startDate, LocalDateTime endDate);

}
