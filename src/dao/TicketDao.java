package dao;

import model.Ticket;

import java.util.HashMap;
import java.util.UUID;

public class TicketDao {
    private HashMap<UUID, Ticket> tickets;

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
}
