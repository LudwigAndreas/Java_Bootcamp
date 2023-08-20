package edu.school21.tanksserver.repositories;

import edu.school21.tanksserver.model.Room;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Scope("singleton")
public class RoomsRepositoryLocalImpl implements RoomsRepository {
    private Map<String, Room> rooms;

    private RoomsRepositoryLocalImpl() {
        this.rooms = new HashMap<>();
    }

    @Override
    public Optional<Room> findById(String s) {
        if (rooms.containsKey(s))
            return Optional.of(rooms.get(s));
        return Optional.empty();
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public void save(Room room) {
        rooms.put(room.getPasscode(), room);
    }

    @Override
    public void update(Room model) {
        rooms.remove(model.getName());
        rooms.put(model.getName(), model);
    }

    @Override
    public void delete(String s) {
        rooms.remove(s);
    }

    @Override
    public Optional<Room> findByPasscode(String passcode) {
        return findById(passcode);
    }
}
