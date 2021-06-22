package dao;

import model.Manifestation;

import java.util.HashMap;
import java.util.UUID;

public class ManifestationDao {
    private HashMap<UUID, Manifestation> manifestations;

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
}
