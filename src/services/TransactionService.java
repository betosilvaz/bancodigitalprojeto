package services;

import entities.Account;
import repositories.AccountRepository;
import repositories.TransactionRepository;

import java.util.ArrayList;

public class TransactionService {

    public static void deposit(Account account, double value) {
        account.deposit(value);
        AccountRepository.save(account);
        TransactionRepository.registerDeposit(account.getAccountNumber(), value);
    }

    public static void draw(Account account, double value) {
        account.draw(value);
        AccountRepository.save(account);
        TransactionRepository.registerDraw(account.getAccountNumber(), value);
    }

    public static void transfer(int sender_number, int receiver_number, double value) {
        Account sender = AccountRepository.search(sender_number);
        Account receiver = AccountRepository.search(receiver_number);

        sender.draw(value);
        receiver.deposit(value);

        TransactionRepository.registerTransfer(sender_number, receiver_number, value);
    }

    public static void bankStatement(int account_number) {
        ArrayList<String[]> result = TransactionRepository.getBankStatement(account_number);
        for(String[] s : result) {
            System.out.printf("transaction_id: %s | type: %s | sender: %s | receiver: %s | value: %s\n",
                    s[0], s[1], s[2], s[3], s[4]);
        }
    }
}
