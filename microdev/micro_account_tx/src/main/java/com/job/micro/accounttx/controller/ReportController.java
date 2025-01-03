package com.job.micro.accounttx.controller;

import com.job.micro.accounttx.dto.ClientAccTxReportDTO;
import com.job.micro.accounttx.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
        name = "Report API service - ReportController",
        description = "ReportController exposes REST APIs for report service"
)

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private ReportService reportService;

    @Operation(
            summary = "Get report transactions by client id and range of dates REST API",
            description = "Get report transactions by client id and range of dates REST API is used to get a report of transactions from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 OK"
    )
    // Build REST API get report of account statement transactions by client and range of dates
    // http://localhost:8081/api/reports?
    //                                  clientId=1&
    //                                  startDate=2024-05-16T09:41:00Z&
    //                                  endDate=2024-05-17T09:42:00Z
    @GetMapping
    public ResponseEntity<List<ClientAccTxReportDTO>> getAccountStatement(@RequestParam Long clientId,
                                                                          @RequestParam LocalDateTime startDate,
                                                                          @RequestParam LocalDateTime endDate) {
        List<ClientAccTxReportDTO> report = reportService.txByClientAndDate(clientId, startDate, endDate);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

}
