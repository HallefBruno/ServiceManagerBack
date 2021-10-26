package com.manager.service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ServicoPrestadoDTO {

    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;
    @NotEmpty(message = "{campo.preco.obrigatorio}")
    private String preco;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{campo.data.obrigatorio}")
    private LocalDate dataServicoPrestado;
    @NotNull(message = "{campo.cliente.obrigatorio}")
    private Long clienteId;

}
