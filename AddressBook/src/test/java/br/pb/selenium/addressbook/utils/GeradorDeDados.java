package br.pb.selenium.addressbook.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.javafaker.Faker;

public class GeradorDeDados {
	
	public static String gerarEmail() {
		String email = "user" + RandomStringUtils.randomNumeric(4) + "@email.com";
		return email;
	}
	
	public static String gerarSenha() {
		String senha = RandomStringUtils.randomAlphanumeric(16);	
		return senha; 
	}
	
	public static String gerarPrimeiroNome() {
		String primeiroNome = new Faker(Locale.ENGLISH).firstName();
		return primeiroNome;
	}
	
	public static String gerarUltimoNome() {
		String ultimoNome = new Faker(Locale.ENGLISH).lastName();
		return ultimoNome;		
	}
	
	public static String gerarEndereco() {
		String endereco = new Faker(Locale.ENGLISH).streetName();
		return endereco + ", " + RandomStringUtils.randomNumeric(3);
	}
	
	public static String gerarCidade() {
		String cidade = new Faker(Locale.ENGLISH).cityPrefix();
		return cidade;
	}
	
	public static String gerarCep() {
		String cep = RandomStringUtils.randomNumeric(8);
		return cep;
	}
	
	public static String gerarIdade() {
		String idade = RandomStringUtils.randomNumeric(2);
		return idade;
	}
	
	public static String gerarWebSite() {
		String url = "https://www." + RandomStringUtils.randomAlphabetic(10) + ".com";
		return url;
	}
	
	public static String gerarTelefone() {
		String telefone = "(011)" + RandomStringUtils.randomNumeric(9);
		return telefone;
	}
	
	public static String gerarTextoNota() {
		String textoNota = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris convallis mi eros, a hendrerit nisl sollicitudin ac. Phasellus tempor mollis ullamcorper. Nunc nec velit lacus. Etiam finibus consectetur nibh, viverra hendrerit turpis viverra eu. In hac habitasse platea dictumst. Suspendisse sollicitudin eros sed vehicula vestibulum. Maecenas varius ex eros, a consequat justo congue sit amet. Aenean a ullamcorper nulla, ac ullamcorper nisi. Quisque ac fermentum lorem. Fusce sollicitudin ante sit amet sapien laoreet, in porta est faucibus.";
		return textoNota;
	}
	
	public static String gerarDataComDiferencaDias(Integer qtdDias) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, - qtdDias);
		return genDataFormatada(cal.getTime());
	}
	
	public static String genDataFormatada(Date data) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(data);
	}
}