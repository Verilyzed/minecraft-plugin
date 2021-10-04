package de.verilyzed.service;

import de.verilyzed.exceptions.UpdateFailedException;
import de.verilyzed.persistence.model.Bounty;
import de.verilyzed.persistence.repository.BountyRepository;
import org.jetbrains.annotations.NotNull;

public class BountyService {
    private static BountyRepository bountyRepository;

    public static void setBountyRepository(BountyRepository bountyRepository) {
        BountyService.bountyRepository = bountyRepository;
    }

    public static void addBounty(Bounty bounty, @NotNull String name) throws UpdateFailedException {
        bountyRepository.insertBounty(bounty, name);
    }

    public static void removeBountyByName(String name) {
        bountyRepository.removeBounty(name);
    }
}
