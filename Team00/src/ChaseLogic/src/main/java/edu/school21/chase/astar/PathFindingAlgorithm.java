package edu.school21.chase.astar;

import edu.school21.chase.utils.Cell;

public interface PathFindingAlgorithm <T, C extends Cell<T>> {
    C findPath(C[][] map, C start, C end);
}
