package steering.squid.finance.currentbalance;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
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

@Path("{owner}/currentbalance")
@Stateless
public class CurrentBalanceService {
    
    @PersistenceContext(unitName = "finance")
    private EntityManager em;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CurrentBalance get(@PathParam("owner") String owner){
        Query q = em.createQuery("SELECT o FROM CurrentBalance o WHERE o.owner = :owner ORDER BY o.id DESC", CurrentBalance.class);
        q.setMaxResults(1);
        q.setParameter("owner", owner);
        List<CurrentBalance> result = q.getResultList();
         if(result.isEmpty()){
            CurrentBalance currentBalance = new CurrentBalance();
            currentBalance.setBalance(0L);
            currentBalance.setOwner(owner);
            em.persist(currentBalance);
            return currentBalance;
        }
        else
            return result.get(0);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CurrentBalance create(@PathParam("owner") String owner, CurrentBalance currentBalance){
        currentBalance.setOwner(owner);
        em.persist(currentBalance);
        return currentBalance;
    }
    
    
}
