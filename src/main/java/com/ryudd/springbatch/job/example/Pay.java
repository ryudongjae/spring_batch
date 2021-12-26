package com.ryudd.springbatch.job.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pay {

    @Id@GeneratedValue
    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime localDateTime;

    public Pay(Long amount, String txName, LocalDateTime localDateTime) {
        this.amount = amount;
        this.txName = txName;
        this.localDateTime = localDateTime;
    }

    public Pay(Long id, Long amount, String txName, LocalDateTime localDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.localDateTime = localDateTime;
    }
}
