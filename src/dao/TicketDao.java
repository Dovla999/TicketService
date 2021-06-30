package dao;

import controller.UserController;
import model.LoyaltyProgram;
import model.Manifestation;
import model.Ticket;
import model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
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
                .filter(Ticket::getActive)
                .filter(ticket -> ticket.getManifestation().getUuid().equals(manifestation.getUuid())).count();
    }

    public List<Ticket> getTicketsForUser(User currentUser, Map<String, String> sfs) {
        var list = tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(Ticket::getActive)
                .filter(ticket -> ticket.getOwner().getUuid().equals(currentUser.getUuid()))
                .collect(Collectors.toList());
        if (sfs.getOrDefault("dateStart", "1900-01-01").equals("")) sfs.put("dateStart", "1900-01-01");
        if (sfs.getOrDefault("dateEnd", "1900-01-01").equals("")) sfs.put("dateEnd", "3021-01-01");
        list = list.stream()
                .filter(manifestation -> manifestation.getManifestation().getName().toLowerCase().contains(sfs.getOrDefault("name", manifestation.getManifestation().getName()).toLowerCase()))
                //.filter(manifestation -> manifestation.getManifestation().getLocation().getAddress().toLowerCase().contains(sfs.getOrDefault("location", manifestation.getManifestation().getLocationAddres()).toLowerCase()))
                .filter(manifestation -> manifestation.getManifestation().getDateTime().plusDays(1).toLocalDate().isAfter(LocalDate.parse(sfs.getOrDefault("dateStart", "1900-01-01"))))
                .filter(manifestation -> manifestation.getManifestation().getDateTime().minusDays(1).toLocalDate().isBefore(LocalDate.parse(sfs.getOrDefault("dateEnd", "3021-01-01"))))
                .filter(manifestation -> manifestation.getTicketPrice() >= Double.parseDouble(sfs.getOrDefault("priceStart", "0")))
                .filter(manifestation -> manifestation.getTicketPrice() <= Double.parseDouble(sfs.getOrDefault("priceEnd", "99999999999")))
                .collect(Collectors.toList());

        if (sfs.containsKey("filterType") && !sfs.get("filterType").equals("ALL")) {
            list = list.stream()
                    .filter(manifestation -> manifestation.getTicketType().toString().equals(sfs.get("filterType")))
                    .collect(Collectors.toList());
        }


        if (sfs.getOrDefault("sortDirection", "ASC").equals("DESC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "DATE":
                    list.sort(Comparator.comparing(ticket -> ticket.getManifestation().getDateTime()));
                    Collections.reverse(list);
                    break;
                case "TICKET_PRICE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Ticket::getTicketPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    list.sort(Comparator.comparing(ticket -> ticket.getManifestation().getName()));
                    Collections.reverse(list);
                    break;
            }
        }
        if (sfs.getOrDefault("sortDirection", "ASC").equals("ASC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "DATE":
                    list.sort(Comparator.comparing(ticket -> ticket.getManifestation().getDateTime()));

                    break;
                case "TICKET_PRICE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Ticket::getTicketPrice))
                            .collect(Collectors.toList());
                    break;
                default:
                    list.sort(Comparator.comparing(ticket -> ticket.getManifestation().getName()));
                    break;
            }
        }
        return list;
    }

    public List<Ticket> getTicketsForSeller(User currentUser) {
        return tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(Ticket::getActive)
                .filter(ticket -> ticket.getManifestation().getCreator().getUuid().equals(currentUser.getUuid()))
                .collect(Collectors.toList());
    }

    public boolean deleteTicket(String id) {
        tickets.values()
                .stream()
                .filter(comment -> comment.getUuid().equals(UUID.fromString(id)))
                .findFirst()
                .ifPresent(
                        comment -> comment.setDeleted(true)
                );
        return true;
    }

    public List<Ticket> getTicketForCancel(String id) {
        tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(ticket -> ticket.getOwner().getUuid().equals(UserController.currentUser.getUuid()))
                .filter(ticket -> ticket.getManifestation().getDateTime().isBefore(LocalDateTime.now().plusDays(7)))
                .filter(Ticket::getActive)
                .filter(ticket -> ticket.getUuid().equals(UUID.fromString(id)))
                .findFirst()
                .ifPresent(
                        ticket -> {
                            ticket.getOwner().setPoints(
                                    ticket.getOwner().getPoints() - ticket.getTicketPrice() / 1000 * 133 * 4);
                            ticket.setActive(false);
                            ticket.getOwner().setLoyaltyCategory(LoyaltyProgram.INSTANCE.getLoyaltyCategoryByPoints((int) Math.round(ticket.getOwner().getPoints())));
                            ticket.getManifestation().setTicketsRemaining(ticket.getManifestation().getTicketsRemaining() + 1);
                            ticket.getOwner().getCancellations().add(LocalDateTime.now());
                        }
                );
        return tickets.values()
                .stream()
                .filter(ticket -> !ticket.isDeleted())
                .filter(ticket -> ticket.getOwner().getUuid().equals(UserController.currentUser.getUuid()))
                .filter(Ticket::getActive)
                .collect(Collectors.toList());
    }
}
