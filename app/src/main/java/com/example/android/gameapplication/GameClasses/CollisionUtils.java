package com.example.android.gameapplication.GameClasses;

/**
 * @author Tony Shu
 * @date 09/10/2022
 * @desc detect all kind of collisions, ideally this is follows the singleton design pattern
 */
public final class CollisionUtils {

    /**
     *
     * @param jumper
     * @param board
     * @return true if the jumper falls onto the given board, false otherwise.
     */
    public static Boolean JumperBoardCollision(Jumper jumper,Board board)
    {
        Integer jumperPosX = jumper.getPosX();
        Integer jumperPosY = jumper.getPosY();
        Integer boardPosY = board.getPosY() - board.getBoardHeight()/2;
        if (jumperPosY >= boardPosY - 10 && jumperPosY <= boardPosY + 10)
        {
            if(jumperPosX>= board.getXLeft() && jumperPosX<= board.getXRight())
            {
                return true;
            }
        }
        return false;

    }
}
