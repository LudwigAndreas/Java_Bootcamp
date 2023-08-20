package edu.school21.tanksserver.service;

import edu.school21.tanksserver.exception.JoinException;
import edu.school21.tanksserver.model.Room;
import edu.school21.tanksserver.model.User;

public interface RoomService {

    Room createRoom(User user);

    Room joinRoom(User user, String passcode) throws JoinException;
}
