package com.example.consumer.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transactions implements Serializable {
  public static final String DAY_TIME_PATTERN = "MM-dd-yyyy";

  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private UUID id;

  @Column
  private String amount;

  @Column
  private String iban;

  @JsonFormat(pattern = DAY_TIME_PATTERN, shape = JsonFormat.Shape.STRING)
  @DateTimeFormat(pattern = DAY_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE)
  @Column
  private LocalDate date;

  @Column
  private String description;

  public void setId(String id) {
    this.id = UUID.fromString(id);
  }
}
