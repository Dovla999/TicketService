package dao;

import model.Manifestation;
import model.Ticket;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TicketDao {
    private HashMap<UUID, Ticket> tickets = new HashMap<>();

    public TicketDao() {
    }

    public TicketDao(HashMap<UUID, Ticket> tickets) {
        this.tickets = tickets;
    }

    public HashMap<UUID, Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(HashMap<UUID, Ticket> tickets) {
        this.tickets = tickets;
    }


    public void addTicket(Ticket t) {
        tickets.put(t.getUuid(), t);
    }

    public int numberOfTickets(Manifestation manifestation) {
        return (int) tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(ticket -> ticket.getManifestation().getUuid().equals(manifestation.getUuid())).count();
    }

    public List<Ticket> getTicketsForUser(User currentUser) {
        return tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(ticket -> ticket.getOwner().getUuid().equals(currentUser.getUuid()))
                .collect(Collectors.toList());
    }
}
