package com.equipe19.validator;

import com.equipe19.model.Cliente;
import java.util.HashMap;
import java.util.Map;

public class ClienteValidator {

    public static Map<String, String> validar(Cliente cliente) {
        Map<String, String> erros = new HashMap<>();

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            erros.put("nome", "O nome é obrigatório.");
        }

        String cpfLimpo = cliente.getCpf() != null ? cliente.getCpf().replaceAll("\\D", "") : "";
        if (!validarCPF(cpfLimpo)) {
            erros.put("cpf", "CPF inválido.");
        }

        String foneLimpo = cliente.getTelefone() != null ? cliente.getTelefone().replaceAll("\\D", "") : "";
        if (foneLimpo.length() < 10 || foneLimpo.length() > 11) {
            erros.put("telefone", "O telefone deve conter 10 ou 11 dígitos.");
        }

        if (cliente.getEndereco() == null || cliente.getEndereco().trim().isEmpty()) {
            erros.put("endereco", "O endereço não pode ficar em branco.");
        }

        return erros;
    }

    private static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }
        try {
            int sm = 0;
            int num;
            for (int i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * (10 - i));
            }
            int r = 11 - (sm % 11);
            char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            for (int i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm = sm + (num * (11 - i));
            }
            r = 11 - (sm % 11);
            char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}