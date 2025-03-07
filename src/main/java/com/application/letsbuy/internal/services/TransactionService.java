package com.application.letsbuy.internal.services;

import com.application.letsbuy.internal.dto.BalanceDtoResponse;
import com.application.letsbuy.internal.dto.TransactionResponseDto;
import com.application.letsbuy.internal.entities.Transaction;
import com.application.letsbuy.internal.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void save(Transaction transaction){
        this.transactionRepository.save(transaction);
    }

    public List<TransactionResponseDto> listTransactions(Long userId) {

        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        return TransactionResponseDto.convert(transactions);
    }
}
