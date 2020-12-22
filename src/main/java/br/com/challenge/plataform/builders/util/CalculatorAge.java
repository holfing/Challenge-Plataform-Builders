package br.com.challenge.plataform.builders.util;

import java.time.LocalDate;
import java.time.Period;

import br.com.challenge.plataform.builders.model.Client;

public class CalculatorAge {
	public static int idade(Client cliente) {
		
		return(int) Period.between(cliente.getDataNascimento(), LocalDate.now()).getYears();
	}
}
