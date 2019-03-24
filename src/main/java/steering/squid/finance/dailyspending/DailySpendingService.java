/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steering.squid.finance.dailyspending;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("{owner}/spending")
@Stateless
public class DailySpendingService {
    
    @PersistenceContext(unitName = "finance")
    EntityManager em;
            
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DailySpending> get(@PathParam("owner") String owner){
        Query q = em.createQuery("SELECT new steering.squid.finance.dailyspending.DailySpending(SUM(o.amount), o.transactionDate) FROM Transaction o WHERE o.owner = :owner GROUP BY o.transactionDate ORDER BY o.transactionDate");
        q.setParameter("owner", owner);
        return q.getResultList();
    }
            
}
