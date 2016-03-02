package ru.lanit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "ref_reputation_source")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RefReputationSource implements Serializable {
    @Id
    private Integer id;
    private String name;
    @Column(name = "trust_index")
    private BigDecimal trustIndex;
}