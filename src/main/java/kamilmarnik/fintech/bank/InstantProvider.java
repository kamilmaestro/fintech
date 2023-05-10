package kamilmarnik.fintech.bank;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class InstantProvider {
    private Clock clock;

    public InstantProvider() {
        this.clock = Clock.systemUTC();
    }

    public Instant now(){
         return Instant.now(clock);
     }
     public void setClock(Instant currentDate){
        this.clock=Clock.fixed(currentDate, ZoneId.systemDefault());
     }
}
