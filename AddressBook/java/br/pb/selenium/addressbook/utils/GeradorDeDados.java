package br.pb.selenium.addressbook.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class GeradorDeDados {
	
	public static String gerarEmail() {
		String email = "user" + RandomStringUtils.randomNumeric(4) + "@email.com";
		
		return email;
	}
	
	public static String gerarSenha() {
		String senha = RandomStringUtils.randomAlphanumeric(16);
		
		return senha; 
	}
	
	public static void main(String[] args) {
		System.out.println(gerarEmail());
		System.out.println(gerarSenha());
	}
}