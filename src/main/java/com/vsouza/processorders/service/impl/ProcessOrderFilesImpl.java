package com.vsouza.processorders.service.impl;

import com.vsouza.processorders.dto.request.OrderFileRequest;
import com.vsouza.processorders.service.ProcessOrderFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessOrderFilesImpl implements ProcessOrderFiles {

    private static final Pattern LINE_PATTERN = Pattern.compile(
            "(?<userId>\\d{10})" +
                    "\\s+" +
                    "(?<userName>\\S[\\S ]{0,43}\\S)" +
                    "\\s*" + "(?<orderId>\\d{10})" +
                    "(?<productId>\\d{10})" +
                    "\\s*" +
                    "(?<value>\\d+\\.\\d+)" +
                    "(?<date>\\d{8})");

    //le o arquivo e transforma em DTO
    @Override
    public List<OrderFileRequest> processFile(MultipartFile file) {
        validateFile(file);
        List<OrderFileRequest> orders = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LINE_PATTERN.matcher(line.trim());
                if (matcher.find()) {
                    OrderFileRequest orderData = OrderFileRequest.builder()
                            .userId(Long.parseLong(matcher.group("userId").trim()))
                            .userName(matcher.group("userName").trim())
                            .orderId(Long.parseLong(matcher.group("orderId").trim()))
                            .productId(Long.parseLong(matcher.group("productId").trim()))
                            .value(BigDecimal.valueOf(Double.parseDouble(matcher.group("value").trim())))
                            .date(LocalDate.parse(matcher.group("date").trim(), formatter))
                            .build();

                    orders.add(orderData);
                }
            }
            return orders;
        } catch (Exception e) {
            log.error("Erro ao ler arquivo: ", e);
            return Collections.emptyList();
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty");
        }
    }
}
