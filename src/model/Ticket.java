package model;

import java.util.UUID;

public class Ticket {

    private UUID uuid;
    private String id;
    private Manifestation manifestation;
    private User owner;
    private Boolean active;
    private TicketType ticketType;
    private boolean deleted = false;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

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
