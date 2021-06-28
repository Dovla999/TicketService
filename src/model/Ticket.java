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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        return uuid != null ? uuid.equals(ticket.uuid) : ticket.uuid == null;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
