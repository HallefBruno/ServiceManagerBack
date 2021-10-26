package com.manager.service.util;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ConversorBigDecimal {
    
    public BigDecimal converterStringForBigDeciaml(String value) {
        if(value==null) return null;
        value = value.replace(".", "").replace(",", ".");
        return new BigDecimal(value);
    }
}
