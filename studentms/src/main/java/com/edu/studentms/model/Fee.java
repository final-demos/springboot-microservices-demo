package com.edu.studentms.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

public class Fee {
    @Schema(hidden = true)
    private Integer id;
    private Integer studentId;
    private BigDecimal amount;
    private Month feeMonth;
    private int feeYear;

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
}
