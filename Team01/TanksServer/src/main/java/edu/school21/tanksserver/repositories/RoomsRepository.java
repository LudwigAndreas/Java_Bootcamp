package edu.school21.tanksserver.repositories;

import edu.school21.tanksserver.model.Room;

import java.util.Optional;

public interface RoomsRepository extends CrudRepository<String, Room> {
    Optional<Room> findByPasscode(String passcode);
}
