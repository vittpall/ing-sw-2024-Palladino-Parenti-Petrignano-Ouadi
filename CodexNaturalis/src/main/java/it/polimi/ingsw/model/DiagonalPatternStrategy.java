package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class DiagonalPatternStrategy implements ObjectiveStrategy{

    private Resource PrimarySource;
    private int Points;

    public DiagonalPatternStrategy(Resource PrimarySource, int Points)
    {
        this.PrimarySource = PrimarySource;
        this.Points = Points;
    }


    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     * @param desk
     */
    public int isSatisfied (PlayerDesk desk){

        int i = 1;
        int NumberOfTimesVerifiedObjective = 0;

        //iterate over desk until i found a position where the card's color is the primarysource
        for(Point point : desk)
        {
            if (desk.get(point).getCardResourceBack().equals(PrimarySource))
            {
                if(PrimarySource.equals(Resource.INSECT_KINGDOM))
                {
                    if(CheckPrincipalDiagonal(desk, Resource.INSECT_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;

                }
                if(PrimarySource.equals(Resource.PLANT_KINGDOM))
                {
                    if(CheckPrincipalDiagonal(desk, Resource.PLANT_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
                if(PrimarySource.equals(Resource.FUNGI_KINGDOM))
                {
                    if(CheckSecodaryDiagonal(desk, Resource.FUNGI_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
                if(PrimarySource.equals(Resource.ANIMAL_KINGDOM))
                {
                    if(CheckSecodaryDiagonal(desk, Resource.ANIMAL_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
            }
        }
        return NumberOfTimesVerifiedObjective * this.Points;
    }


    private boolean CheckPrincipalDiagonal(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
        int i=0;
        if(this.PrimarySource.equals(ResourceToSearch))
        {
            while(desk.contains(new Point(point.getX()+i, point.getY()-i)) && desk.get(new Point(point.getX()+i, point.getY()-i).getCardResourceBack().equals(ResourceToSearch)
            {
                //until i found the card on the left bottom corner which is different from the searched resource
                i++;
            }
            i--;
            if(desk.contains(new Point(point.getX()-i, point.getY()+i)) && desk.get(new Point(pnoint.getX()-i, point.getY()+i)).getCardResourceBack().equals(PrimarySource))
            {
                i++;
                if(desk.contains(new Point(point.getX()-i, point.getY()+i)) &&  desk.get(new Point(point.getX()-i, point.getY()+i)).getCardResourceBack().equals(PrimarySource))
                {
                    i++;
                    if(desk.contains(new Point(point.getX()-i, point.getY()+i)) && desk.get(new Point(point.getX()-i, point.getY()+i)).getCardResourceBack().equals(PrimarySource))
                    {
                        for(int j = 0; j <= 2; j++)
                            desk.remove(new Point(point.getX()-i+j, point.getY()+i-j));
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
        return true;
    }

    private boolean CheckSecodaryDiagonal(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
        int i=0;
        if(this.PrimarySource.equals(ResourceToSearch))
        {
            while(desk.contains(new Point(point.getX()-i, point.getY()+i)) && desk.get(new Point(point.getX()-i, point.getY()+i).getCardResourceBack().equals(ResourceToSearch)
            {
                //until i found the card on the left bottom corner which is different from the searched resource
                i++;
            }
            i--;
            if(desk.contains(new Point(point.getX()+i, point.getY()-i)) && desk.get(new Point(pnoint.getX()+i, point.getY()-i)).getCardResourceBack().equals(PrimarySource))
            {
                i++;
                if(desk.contains(new Point(point.getX()+i, point.getY()-i)) &&  desk.get(new Point(point.getX()+i, point.getY()-i)).getCardResourceBack().equals(PrimarySource))
                {
                    i++;
                    if(desk.contains(new Point(point.getX()+i, point.getY()-i)) && desk.get(new Point(point.getX()+i, point.getY()-i)).getCardResourceBack().equals(PrimarySource))
                    {
                        for(int j = 0; j <= 2; j++)
                            desk.remove(new Point(point.getX()+i-j, point.getY()-i+j));
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            else
                return false;
        }
        else
            return false;
        return true;
    }
}