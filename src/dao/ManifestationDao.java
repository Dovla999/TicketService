package dao;

import model.Manifestation;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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
}
