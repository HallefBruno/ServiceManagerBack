package com.manager.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicoPrestado implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(nullable = false, name = "cliente_id")
    private Cliente cliente;
    
    @Column(nullable = false, length = 255)
    private String descricao;
    
    @Column(nullable = false)
    private BigDecimal valor;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false, name = "data_servico_prestado")
    private LocalDate dataServicoPrestado;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.descricao = StringUtils.strip(this.descricao);
    }
    
}
