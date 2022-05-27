package com.edu.feesms.resource;

import com.edu.feesms.model.Fee;
import com.edu.feesms.repo.FeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/fees")
public class FeeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeeResource.class);

    @Autowired
    private FeeRepo repo;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Fee>> getAllFees(@RequestParam(required = false) @Min(1) Integer studentId) {

        if (Objects.isNull(studentId)) {
            LOGGER.info("fetching all fees");
            return ResponseEntity.ok(repo.findAll());
        }

        LOGGER.info("fetching all fees for student: {}", studentId);
        Optional<List<Fee>> studentFees = repo.findByStudentId(studentId);
        if (studentFees.isPresent() && studentFees.get().size() > 0) {
            return ResponseEntity.ok(studentFees.get());
        }

        LOGGER.error("No fees found for student: {}", studentId);
        return ResponseEntity.notFound().build();

    }

    @GetMapping(path = "/{feeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fee> getSingleFee(@PathVariable Integer feeId) {
        Optional<Fee> fee = repo.findById(feeId);
        if (fee.isPresent()) {
            LOGGER.info("Fee {}, found.", feeId);
            return ResponseEntity.ok(fee.get());
        }

        LOGGER.error("Fee {}, not found.", feeId);
        return ResponseEntity.notFound().build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fee> createFee(@RequestBody Fee fee) throws URISyntaxException {
        Fee newFee = repo.save(fee);
        LOGGER.info("New Fee {}, added.", newFee.getId());
        return ResponseEntity.created(new URI(newFee.getId().toString())).body(newFee);
    }

    @DeleteMapping("/{feeId}")
    public ResponseEntity<Void> deleteFee(@PathVariable Integer feeId) {
        repo.deleteById(feeId);
        LOGGER.info("Fee {}, deleted.", feeId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{feeId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fee> updateFee(@PathVariable Integer feeId, @RequestBody Fee fee) {
        boolean isIdFound = repo.existsById(feeId);
        if (!isIdFound) {
            LOGGER.error("Fee with id {}, not found.", fee.getId());
            return ResponseEntity.notFound().build();
        }

        fee.setId(feeId);
        Fee updatedFee = repo.save(fee);
        LOGGER.info("Fee {}, updated.", fee.getId());
        return ResponseEntity.ok(updatedFee);
    }

}
