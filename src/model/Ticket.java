package model;

import java.util.UUID;

public class Ticket {

    private UUID uuid;
    private String id;
    private Manifestation manifestation;
    private User owner;
    private Boolean active;
    private TicketType ticketType;

    public Ticket(UUID uuid, String id, Manifestation manifestation, User owner, Boolean active, TicketType ticketType) {
        this.uuid = uuid;
        this.id = id;
        this.manifestation = manifestation;
        this.owner = owner;
        this.active = active;
        this.ticketType = ticketType;
    }

    public Ticket() {
    }
}
