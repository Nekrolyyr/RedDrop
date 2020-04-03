package ch.versusvirus.reddrop.data_access;

import java.util.List;

import ch.versusvirus.reddrop.logic.model.DonationListEntry;

public interface LocationParser {
    List<DonationListEntry> parse(String input);
}
