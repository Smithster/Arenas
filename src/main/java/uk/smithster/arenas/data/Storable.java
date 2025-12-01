package uk.smithster.arenas.data;

import static uk.smithster.arenas.Plugin.LOGGER;

public abstract interface Storable {

    static void load() {
        LOGGER.info("CALLING IN STORAGE ABSTRACT SHOULDN'T HAPPEN, DEFINE YOUR LOAD FUNCTION");
    };

    static void save() {
        LOGGER.info("CALLING IN STORAGE ABSTRACT SHOULDN'T HAPPEN, DEFINE YOUR SAVE FUNCTION");
    }
}
