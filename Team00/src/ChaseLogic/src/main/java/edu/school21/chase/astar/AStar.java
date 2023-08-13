package edu.school21.chase.astar;


import edu.school21.chase.utils.Cell;
import edu.school21.chase.utils.Vector2;

import java.util.LinkedList;
import java.util.Comparator;
import java.util.Objects;

public class AStar implements PathFindingAlgorithm<Integer, Cell<Integer>> {

    static class AStarNodeComparator implements Comparator<AStarNode>
    {
        public int compare(AStarNode c1, AStarNode c2)
        {
            if (Objects.equals(c1.getH(), c2.getH()))
                return c1.getG().compareTo(c2.getG());
            return c1.getH().compareTo(c2.getH());
        }
    }

    private LinkedList<AStarNode>   open;
    private LinkedList<AStarNode>   closed;
    private Cell<Integer>[][] map;
    char[]                          objectChars;

//    private final char              wallChar;
//    private final char              enemyChar;
//    private final char              playerChar;
//    private final char              emptyChar;
//    private final char              goalChar;

    public AStar()
    {
        this(null);
    }
    
    public AStar(Cell<Integer>[][] map)
    {
        this.map = map;
    }

    public Vector2<Integer> calculateDirection(Vector2<Integer> start, Vector2<Integer> goal)
    {
        AStarNode   curr;
        this.open   = new LinkedList<AStarNode>();
        this.closed = new LinkedList<AStarNode>();

        if (heuristicCost(start, goal) < 2)
            return (goal);

        open.add(new AStarNode(start.getX(), start.getY(),
                0, heuristicCost(start, goal)));
        while (!open.isEmpty())
        {
            open.sort(new AStarNodeComparator());
            curr = open.pop();
            closed.add(curr);
            if (heuristicCost(curr, goal) < 2)
            {
                curr = regeneratePath(start, curr);
                if (curr == null)
                    return (start);
                return curr;
            }
            addAllNeighbours(curr, goal);
        }
        return null;
    }

    private AStarNode regeneratePath(Vector2<Integer> start, AStarNode path)
    {
        if (path == null)
            return null;
        while (path != null && heuristicCost(start, path) > 1)
            path = path.getParent();
        return path;
    }

    private boolean isWall(Vector2<Integer> pos)
    {
        return (map[pos.getY()][pos.getX()].isPassable());
    }

    private void addAllNeighbours(AStarNode curr, Vector2<Integer> goal)
    {
        int	        i;
        AStarNode   node = null;
        AStarNode   pos = new AStarNode(0, 0, 0, 0);

        i = -3;
        while (++i < 3)
        {
            if (i != 0)
            {
                pos.setX(curr.getX() + (i % 2));
                pos.setY(curr.getY() + (i / 2));
                if (0 <= pos.getX() && pos.getX() < map.length
                    && 0 <= pos.getY() && pos.getY() < map.length
                    && !isWall(pos)
                    && closed.stream().noneMatch(c -> c.getX() == pos.getX() && c.getY() == pos.getY()))
                {
                    node = open.stream().filter(c -> c.equals(pos))
                            .findAny().orElse(null);
                    if (node != null && node.getG() > curr.getG() + 1)
                        node.setG(curr.getG() + 1);
                    if (node == null)
                        open.add(new AStarNode(pos.getX(), pos.getY(),
                                curr.getG() + 1, heuristicCost(pos, goal), curr));
                }
            }
        }
    }

    private int	heuristicCost(Vector2<Integer> lhs, Vector2<Integer> rhs)
    {
        return (Math.abs(lhs.getX() - rhs.getX()) + Math.abs(lhs.getY() - rhs.getY()));
    }

    @Override
    public Cell<Integer> findPath(Cell<Integer>[][] map, Cell<Integer> start, Cell<Integer> end) {
        if (map != null)
            this.map = map;
        if (this.map == null)
            return null;

        Vector2<Integer> next_move = calculateDirection(start, end);
        if (next_move == null)
            return null;
        return map[next_move.getY()][next_move.getX()];
    }
}
