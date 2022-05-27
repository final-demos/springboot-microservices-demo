package com.edu.feesms.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Entity
@Table
public class Fee {
    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "student_id")
    private Integer studentId;
    private BigDecimal amount;
    @Column(name = "fee_month")
    @Enumerated(EnumType.STRING)
    private Month feeMonth;
    @Column(name = "fee_year", length = 4)
    @Size(min = 4, max = 4)
    @Schema(example = "2022")
    private int feeYear;
    @Column(name = "date_of_payment")
    @Schema(hidden = true)
    private LocalDate dateOfPayment = LocalDate.now();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Month getFeeMonth() {
        return feeMonth;
    }

    public void setFeeMonth(Month feeMonth) {
        this.feeMonth = feeMonth;
    }

    public int getFeeYear() {
        return feeYear;
    }

    public void setFeeYear(int feeYear) {
        this.feeYear = feeYear;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
}
