package de.minimum.hawapp.server.blackboard.util;

import de.minimum.hawapp.server.blackboard.api.Offer;

public interface KeyGenerator {

    String generate(Offer offer, int length);
}
