package com.example.patterns.q1.dataMapper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class ClienteDataMapper {

    public Cliente_DM fromMap(Map<String, Object> m) {
        if (m == null)
            return null;
        Cliente_DM c = new Cliente_DM();
        Object idObj = m.get("id");
        if (idObj != null) {
            if (idObj instanceof Number) {
                c.setId(((Number) idObj).intValue());
            } else {
                try {
                    c.setId(Integer.parseInt(idObj.toString()));
                } catch (NumberFormatException e) {
                    c.setId(null);
                }
            }
        }
        Object nome = m.get("nome");
        Object cpf = m.get("cpf");
        Object tel = m.get("telefone");

        // Aplica tradutor (remoção de acentos) no nome e formata CPF/telefone
        c.setNome(nome != null ? translate(nome.toString()) : null);
        c.setCpf(cpf != null ? formatCpf(cpf.toString()) : null);
        c.setTelefone(tel != null ? formatTelefone(tel.toString()) : null);
        return c;
    }

    public Map<String, Object> toMap(Cliente_DM c) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", c.getId());
        m.put("nome", c.getNome());

        // Garante que CPF e telefone sejam escritos no formato esperado
        m.put("cpf", c.getCpf() != null ? formatCpf(c.getCpf()) : null);
        m.put("telefone", c.getTelefone() != null ? formatTelefone(c.getTelefone()) : null);
        return m;
    }

    // remove acentos e normaliza texto (ex: José -> Jose)
    public String translate(String input) {
        if (input == null)
            return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        return normalized.replaceAll("\\p{M}", "");
    }

    // Formata CPF para padrão ###.###.###-## quando possível
    public String formatCpf(String cpf) {
        if (cpf == null)
            return null;
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) {
            throw new IllegalArgumentException("CPF inválido: deve conter 11 dígitos numéricos");
        }
        return String
                .format("%s.%s.%s-%s", 
                    digits.substring(0, 3), 
                    digits.substring(3, 6), 
                    digits.substring(6, 9),
                    digits.substring(9));
    }

    // Formata telefone brasileiro: (AA) XXXX-XXXX ou (AA) 9XXXX-XXXX
    public String formatTelefone(String tel) {
        if (tel == null)
            return null;
        String digits = tel.replaceAll("\\D", "");
        if (digits.length() == 10) {
            // DDD + 8 dígitos
            return String.format("(%s) %s-%s", digits.substring(0, 2), digits.substring(2, 6), digits.substring(6));
        } else if (digits.length() == 11) {
            // DDD + 9 dígitos
            return String.format("(%s) %s-%s", digits.substring(0, 2), digits.substring(2, 7), digits.substring(7));
        } else {
            // tenta encontrar padrões já formatados ou retorna original
            // como fallback, tenta inserir parênteses no início se houver DDD
            if (digits.length() > 2) {
                String ddd = digits.substring(0, 2);
                String rest = digits.substring(2);
                // separa último 4 dígitos
                if (rest.length() > 4) {
                    String last4 = rest.substring(rest.length() - 4);
                    String front = rest.substring(0, rest.length() - 4);
                    return String.format("(%s) %s-%s", ddd, front, last4);
                }
            }
            return tel;
        }
    }
}
