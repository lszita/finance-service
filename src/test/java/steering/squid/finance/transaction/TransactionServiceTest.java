/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steering.squid.finance.transaction;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.mockito.Mockito.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import steering.squid.finance.currentbalance.CurrentBalance;
import steering.squid.finance.currentbalance.CurrentBalanceService;

/**
 *
 * @author Lajos Szita
 */
public class TransactionServiceTest {
    
    CurrentBalanceService currentBalanceService = mock(CurrentBalanceService.class);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    EntityManager em;
    
    public TransactionServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        em = emf.createEntityManager();
        CurrentBalance currentBalance = new CurrentBalance();
        currentBalance.setBalance(1000L);
        when(currentBalanceService.get("test")).thenReturn(currentBalance);
    }
    
    @After
    public void tearDown() {
        em.close();
    }

    /**
     * Test of getAllByOwner method, of class TransactionService.
     */
    @Test
    public void testGetAllByOwner(){
        TransactionService ts = new TransactionService(em,currentBalanceService);
        List<Transaction> allByOwner = ts.getAllByOwner("maki");
        
        assertEquals(0,allByOwner.size());
    }

    /**
     * Test of create method, of class TransactionService.
     */
    @Test
    public void testCreate(){
        TransactionService ts = new TransactionService(em,currentBalanceService);
        Transaction transaction = new Transaction();
        transaction.setAmount(0L);
        ts.create("test", transaction);
        assertEquals(Long.valueOf(0),transaction.getAmount());
    }
    
}
