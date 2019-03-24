package steering.squid.finance.transaction;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import steering.squid.finance.currentbalance.CurrentBalance;
import steering.squid.finance.currentbalance.CurrentBalanceService;

@Path("{owner}/transaction")
@Stateless
public class TransactionService {
 
    @PersistenceContext(unitName = "finance")
    private EntityManager em;
    
    @Inject
    private CurrentBalanceService currentBalanceService;

    public TransactionService() {
    }

    public TransactionService(EntityManager em, CurrentBalanceService currentBalanceService) {
        this.em = em;
        this.currentBalanceService = currentBalanceService;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAllByOwner(@PathParam("owner") String owner){
        Query q = em.createQuery("SELECT o FROM Transaction o WHERE o.owner = :owner", 
                Transaction.class);
        q.setParameter("owner", owner);
        return q.getResultList();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Transaction create(@PathParam("owner") String owner, Transaction transaction){
        CurrentBalance currentBalance = currentBalanceService.get(owner);
        Long newBalance = currentBalance.getBalance() - transaction.getAmount();
        currentBalance.setBalance(newBalance);
        transaction.setOwner(owner);
        transaction.setId(null);
        transaction.setBalance(newBalance);
        em.persist(transaction);
        return transaction;
    }
    
}
