package com.manager.service.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Entity
@Data
public class Usuario implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "{campo.username.obrigatorio}")
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotEmpty(message = "{campo.password.obrigatorio}")
    @Column(nullable = false)
    private String password;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.username = StringUtils.strip(this.username);
        this.password = StringUtils.strip(this.password);
    }
    
}
