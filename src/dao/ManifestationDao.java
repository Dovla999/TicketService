package dao;

import controller.TicketController;
import model.Manifestation;
import model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ManifestationDao {
    private HashMap<UUID, Manifestation> manifestations = new HashMap<>();

    public ManifestationDao(HashMap<UUID, Manifestation> manifestations) {
        this.manifestations = manifestations;
    }


    public ManifestationDao() {
    }

    public HashMap<UUID, Manifestation> getManifestations() {
        return manifestations;
    }

    public void setManifestations(HashMap<UUID, Manifestation> manifestations) {
        this.manifestations = manifestations;
    }


    public List<Manifestation> allManifestations() {
        return manifestations.values()
                .stream()
                .filter(manifestation -> !manifestation.isDeleted())
                .collect(Collectors.toList());
    }

    public void addManifestation(Manifestation manifestation) {
        if (!manifestations.containsKey(manifestation.getUuid()))
            manifestations.put(manifestation.getUuid(), manifestation);
    }

    public Manifestation findById(String id) {
        return manifestations.getOrDefault(UUID.fromString(id), null);
    }

    public boolean changeActiveById(String id) {
        var m = findById(id);
        if (m != null) m.setActive(!m.getActive());
        return m != null;
    }

    public List<Manifestation> getManifestationsForSeller(User currentUser) {
        return manifestations.values()
                .stream()
                .filter(manifestation -> !manifestation.isDeleted())
                .filter(manifestation -> manifestation.getCreator().getUuid().equals(currentUser.getUuid()))
                .collect(Collectors.toList());
    }

    public boolean deleteManifestation(String id) {
        findById(id).setDeleted(true);
        return true;
    }

    public List<String> getTypes() {
        return manifestations.values()
                .stream()
                .filter(manifestation -> !manifestation.isDeleted())
                .map(Manifestation::getType).distinct().collect(Collectors.toList());
    }

    public List<Manifestation> applySFS(Map<String, String> sfs, List<Manifestation> manifestations) {
        if (sfs.getOrDefault("dateStart", "1900-01-01").equals("")) sfs.put("dateStart", "1900-01-01");
        if (sfs.getOrDefault("dateEnd", "1900-01-01").equals("")) sfs.put("dateEnd", "3021-01-01");
        var list = manifestations.stream()
                .filter(manifestation -> manifestation.getName().toLowerCase().contains(sfs.getOrDefault("name", manifestation.getName()).toLowerCase()))
                .filter(manifestation -> manifestation.getLocation().getAddress().toLowerCase().contains(sfs.getOrDefault("location", manifestation.getLocationAddres()).toLowerCase()))
                .filter(manifestation -> manifestation.getDateTime().minusDays(1).toLocalDate().isAfter(LocalDate.parse(sfs.getOrDefault("dateStart", "1900-01-01"))))
                .filter(manifestation -> manifestation.getDateTime().plusDays(1).toLocalDate().isBefore(LocalDate.parse(sfs.getOrDefault("dateEnd", "3021-01-01"))))
                .filter(manifestation -> manifestation.getTicketPrice() >= Double.parseDouble(sfs.getOrDefault("priceStart", "0")))
                .filter(manifestation -> manifestation.getTicketPrice() <= Double.parseDouble(sfs.getOrDefault("priceEnd", "99999999999")))
                .collect(Collectors.toList());

        if (sfs.containsKey("filterType") && !sfs.get("filterType").equals("ALL")) {
            list = list.stream()
                    .filter(manifestation -> manifestation.getType().equals(sfs.get("filterType")))
                    .collect(Collectors.toList());
        }

        if (sfs.containsKey("filterSoldOut") && sfs.get("filterSoldOut").equals("YES")) {
            list = list.stream()
                    .filter(manifestation -> manifestation.getCapacity() >= TicketController.ticketDao.numberOfTickets(manifestation))
                    .collect(Collectors.toList());
        }

        if (sfs.containsKey("filterSoldOut") && sfs.get("filterSoldOut").equals("NO")) {
            list = list.stream()
                    .filter(manifestation -> manifestation.getCapacity() > TicketController.ticketDao.numberOfTickets(manifestation))
                    .collect(Collectors.toList());
        }

        if (sfs.getOrDefault("sortDirection", "ASC").equals("DESC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "DATE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getDateTime).reversed())
                            .collect(Collectors.toList());
                    break;
                case "TICKET_PRICE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getTicketPrice).reversed())
                            .collect(Collectors.toList());
                    break;
                case "LOCATION":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getLocationAddres).reversed())
                            .collect(Collectors.toList());
                    break;
                default:
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getName).reversed())
                            .collect(Collectors.toList());
                    break;
            }
        }
        if (sfs.getOrDefault("sortDirection", "ASC").equals("ASC")) {
            switch (sfs.getOrDefault("sortCrit", "NAME")) {
                case "DATE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getDateTime))
                            .collect(Collectors.toList());
                    break;
                case "TICKET_PRICE":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getTicketPrice))
                            .collect(Collectors.toList());
                    break;
                case "LOCATION":
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getLocationAddres))
                            .collect(Collectors.toList());
                    break;
                default:
                    list = list.stream()
                            .sorted(Comparator.comparing(Manifestation::getName))
                            .collect(Collectors.toList());
                    break;
            }
        }
        return list;
    }

    public List<Manifestation> getAllForAdmin(Map<String, String> sfs) {
        return applySFS(sfs, manifestations.values()
                .stream()
                .filter(manifestation -> !manifestation.isDeleted())
                .collect(Collectors.toList()));
    }
}
