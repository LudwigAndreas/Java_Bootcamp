package edu.school21.tanksserver.service;

import edu.school21.tanksserver.exception.JoinException;
import edu.school21.tanksserver.model.Room;
import edu.school21.tanksserver.model.User;
import edu.school21.tanksserver.repositories.RoomsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    protected RoomsRepository roomsRepository;

    public RoomServiceImpl(RoomsRepository roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    public Room createRoom(User user) {
        Room room = new Room(user);
        roomsRepository.save(room);
        return room;
    }

    public Room joinRoom(User user, String passcode) throws JoinException {
        Room room = roomsRepository.findByPasscode(passcode).orElseThrow(() -> new JoinException("Room not found"));
        if (room.isFull())
            throw new JoinException("Room is full");
        room.setGuest(user);
        roomsRepository.update(room);
        return room;
    }
}
