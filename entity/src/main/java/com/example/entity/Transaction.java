package com.example.entity;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
public class Transaction implements Serializable {
  public static final String DAY_TIME_PATTERN = "MM-dd-yyyy";

  @Id
  private UUID id;

  @Column
  private String amount;

  @Column
  private String iban;

  @Column
  @JsonFormat(pattern = DAY_TIME_PATTERN, shape = JsonFormat.Shape.STRING)
//  @DateTimeFormat(pattern = DAY_TIME_PATTERN, iso = DateTimeFormat.ISO.DATE)
//  @JsonSerialize(using = LocalDateSerializer.class)
//  @JsonDeserialize(using= LocalDateDeserializer.class)
  private LocalDate date;

  @Column
  private String description;

}

