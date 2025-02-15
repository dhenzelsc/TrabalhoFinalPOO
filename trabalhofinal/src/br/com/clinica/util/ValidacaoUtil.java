package br.com.clinica.util;

import java.util.regex.Pattern;

public class ValidacaoUtil {
    
    public static boolean validarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        
        if (cpfLimpo.length() != 11 || cpfLimpo.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digitos = new int[11];
            for (int i = 0; i < 11; i++) {
                digitos[i] = Integer.parseInt(cpfLimpo.substring(i, i + 1));
            }

            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                soma1 += digitos[i] * (10 - i);
                soma2 += digitos[i] * (11 - i);
            }
            
            int resto1 = (soma1 * 10) % 11;
            resto1 = (resto1 == 10) ? 0 : resto1;
            
            int resto2 = (soma2 + resto1 * 2) * 10 % 11;
            resto2 = (resto2 == 10) ? 0 : resto2;

            return (resto1 == digitos[9]) && (resto2 == digitos[10]);
            
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarNome(String nome) {
        return Pattern.matches("[\\p{L} ]+", nome) && nome.length() >= 3;
    }

    public static boolean validarCRM(String crm) {
        return Pattern.matches("\\d{4,6}/[A-Z]{2}", crm);
    }

    public static String formatarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return cpfLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    
    public static String formatarCRM(String crm) {
        String crmLimpo = crm.replaceAll("[^0-9A-Za-z]", "");
        
        if (crmLimpo.length() >= 5 && !crmLimpo.contains("/")) {
            return crmLimpo.substring(0, 5) + "/" + crmLimpo.substring(5).toUpperCase();
        }
        return crmLimpo.toUpperCase();
    }
}