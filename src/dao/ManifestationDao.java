package dao;

import model.Manifestation;

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

}
