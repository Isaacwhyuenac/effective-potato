package com.example.producer.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transactions implements Serializable {
  public static final String DAY_TIME_PATTERN = "MM-dd-yyyy";

  @Id
//  @GeneratedValue(strategy = GenerationType.AUTO)
//  @Column(columnDefinition = "uuid")
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
