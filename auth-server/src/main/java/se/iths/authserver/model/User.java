package se.iths.authserver.model;

/**
 * @param password I en riktig applikation ska lösenord hashas!
 */
public record User(String username, String password) {
}