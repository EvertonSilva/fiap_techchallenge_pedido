package br.com.edu.fiap.techchallengelanchonete.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Nome {

    private String valor;

    public String getPrimeiro() {
        if (valor == null) 
            return "";
        return valor.contains(" ") ? valor.split(" ")[0] : valor;
    }

    public String getSobrenomes() {
        if (valor == null)
            return "";
        return valor.replace(getPrimeiro(), "").trim();
    }
}
