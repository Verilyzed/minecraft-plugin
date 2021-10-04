package de.verilyzed.service;

import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.persistence.repository.BountyRepository;

public class BountyService {
    private static BountyRepository bountyRepository;

    public static void setBountyRepository(BountyRepository bountyRepository) {
        BountyService.bountyRepository = bountyRepository;
    }

    public static void addBounty(Bounty bounty) {
        bountyRepository.insertBounty(bounty);
    }

    public static Bounty getBountyByUuid(String uuid) {
        return bountyRepository.getBounty(uuid);
    }

    public static void removeBountyByName(String name) {
        bountyRepository.removeBounty(name);
    }
}
