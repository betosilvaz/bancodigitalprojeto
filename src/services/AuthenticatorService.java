package services;

import entities.*;
import repositories.AccountRepository;
import repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.DriverManager;

public class AuthenticatorService {

    public static Account registerNewAccount(int type, String email, String name, String cpf, String password) {
        if(!CustomerRepository.exists(cpf))
            CustomerRepository.create(email, name, cpf);
        Customer customer = CustomerRepository.search(cpf);
        if(AccountRepository.exists(type, cpf))
            throw new RuntimeException("This CPF already has an account of this type.");
        Account account = null;
        if(type == 1) {
            account = new SavingsAccount(customer, password);
        } else if(type == 2) {
            account = new CheckingAccount(customer, password);
        }
        if(account == null) return null;
        AccountRepository.create(account);
        account = AccountRepository.search(type, cpf);
        return account;
    }

    public static Account authenticate(int type, String cpf, String password) {
        Account account = AccountRepository.search(type, cpf);
        if(account != null && account.getPassword().equals(password)) return account;
        return null;
    }

}
