package com.edu.studentms.resource;

import com.edu.studentms.model.Fee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/students/{studentId}/fees")
public class StudentFeesResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentFeesResource.class);
    private final WebClient webClient;

    public StudentFeesResource(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    public Mono<ResponseEntity<List<Fee>>> getAllFeesOfAStudent(@PathVariable @NotNull Integer studentId) {
        LOGGER.info("Getting fees for student: {}", studentId);
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("studentId", studentId).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()

                .onStatus(status -> status.value() >= 400,
                        clientResponse -> Mono.empty()
                ).toEntity(new ParameterizedTypeReference<List<Fee>>() {
                })
                .map(feeResponseEntity -> {
                    if (feeResponseEntity.getStatusCode().is2xxSuccessful()) {
                        LOGGER.info("fees data found for student: {}", studentId);
                        return ResponseEntity.ok(feeResponseEntity.getBody());
                    }
                    if (feeResponseEntity.getStatusCodeValue() == HttpStatus.NOT_FOUND.value()) {
                        LOGGER.error("No fees data found for student: {}", studentId);
                        return ResponseEntity.notFound().build();
                    }
                    if (feeResponseEntity.getStatusCode().is4xxClientError()) {
                        LOGGER.error("Bad request for student: {}", studentId);
                        return ResponseEntity.badRequest().body(feeResponseEntity.getBody());
                    }
                    LOGGER.error("Something went wrong for student: {}", studentId);
                    return ResponseEntity.internalServerError().body(feeResponseEntity.getBody());
                });
    }

    @PostMapping
    public Mono<ResponseEntity<Fee>> payFeesOfAStudent(@PathVariable @NotNull Integer studentId,
                                                       @RequestBody Fee fee) {
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(fee)
                .retrieve()
                .onStatus(status -> status.value() >= 400,
                        clientResponse -> Mono.empty()
                ).toEntity(new ParameterizedTypeReference<Fee>() {
                })
                .map(feeResponseEntity -> {
                    if (feeResponseEntity.getStatusCode().is2xxSuccessful()) {
                        LOGGER.info("fees data found for student: {}", studentId);
                        return feeResponseEntity;
                    }
                    if (feeResponseEntity.getStatusCodeValue() == HttpStatus.NOT_FOUND.value()) {
                        LOGGER.error("No fees data found for student: {}", studentId);
                        return ResponseEntity.notFound().build();
                    }
                    if (feeResponseEntity.getStatusCode().is4xxClientError()) {
                        LOGGER.error("Bad request for student: {}", studentId);
                        return ResponseEntity.badRequest().body(feeResponseEntity.getBody());
                    }
                    LOGGER.error("Something went wrong for student: {}", studentId);
                    return ResponseEntity.internalServerError().body(feeResponseEntity.getBody());
                });

    }
}
