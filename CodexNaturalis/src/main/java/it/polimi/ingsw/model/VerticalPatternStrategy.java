package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.Resource;

import java.awt.Point;
import java.util.ArrayList;

public class VerticalPatternStrategy implements ObjectiveStrategy
{
    private final Resource PrimarySource;
    private final Resource SecondarySource;
    private final int Points;

    public VerticalPatternStrategy(Resource PrimarySource, Resource SecondarySource, int Points)
    {
        this.PrimarySource = PrimarySource;
        this.SecondarySource = SecondarySource;
        this.Points = Points;
    }

    /**
     * this method will be recognized if the Resource cards
     * on the PlayerDesk meet the requirements of the objective card.
     * in this case it will analyze the objective that requires two vertical cards and another one over one corner.
     * @param desk
     */
    public int isSatisfied (PlayerDesk desk){
        int NumberOfTimesVerifiedObjective = 0;
        //iterate over desk until I found a position where the card's color is the primarySource
        for(Point point : desk.getDesk().keySet())
        {
            if (desk.getDesk().get(point).getbackSideResource().equals(PrimarySource))
            {
                if(PrimarySource.equals(Resource.FUNGI_KINGDOM))
                {
                    if(CheckRightDownCorner(desk, Resource.FUNGI_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;

                }
                if(PrimarySource.equals(Resource.PLANT_KINGDOM))
                {
                    if(CheckLeftDownCorner(desk, Resource.PLANT_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
                if(PrimarySource.equals(Resource.ANIMAL_KINGDOM))
                {
                    if(CheckRightTopCorner(desk, Resource.ANIMAL_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
                if(PrimarySource.equals(Resource.INSECT_KINGDOM))
                {
                    if(CheckLeftTopCorner(desk, Resource.INSECT_KINGDOM, point))
                        NumberOfTimesVerifiedObjective++;
                }
            }
        }
        return NumberOfTimesVerifiedObjective * this.Points;
    }

    private boolean CheckRightDownCorner(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
        int i = 2;
        if(PrimarySource.equals(ResourceToSearch))
        {
            while(desk.getDesk().containsKey(new Point((int) point.getX(), (int) point.getY()+i)) && desk.getDesk().get(new Point((int) point.getX(), (int) point.getY()+i)).getbackSideResource().equals(Resource.FUNGI_KINGDOM))
            {
                //it goes down until it finds a card with a different resource
                i+=2;
            }
            //note: every card are space eachother of at least one cell
            i-=2;
            //note this if is useless because I already know that's the right card
            if(desk.getDesk().get(new Point((int) point.getX(), (int) point.getY()+i)).getbackSideResource().equals(PrimarySource))
            {
                //check if the card in the bottom right corner has the secondary Resource
                if(desk.getDesk().get(new Point((int) point.getX()+1, (int) point.getY()i+1)).getbackSideResource().equals(SecondarySource))
                {
                    i-=2;
                    if(desk.getDesk().get(new Point((int) point.getX(), (int) point.getY()+i)).getbackSideResource().equals(PrimarySource))
                    {
                        for(int j = 0; j <= 2; j = j + 2)
                            desk.getDesk().remove(new Point((int) point.getX(), (int) point.getY()+i+j));
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


    private boolean CheckLeftDownCorner(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
        int i = 2;
        if(PrimarySource.equals(ResourceToSearch))
        {
            while(desk.getDesk().containsKey(new Point((int) point.getX(), (int) point.getY()+i)) && desk.getDesk().get(new Point((int) point.getX(), (int)point.getY()+i)).getbackSideResource().equals(Resource.PLANT_KINGDOM))
            {
                //it goes down until it finds a card with a different resource
                i+=2;
            }
            //note: every card are space eachother of at least one cell
            i-=2;
            //note this if is useless because I already know that's the right card
            if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
            {
                //check if the card in the bottom left corner has the secondary Resource
                if(desk.getDesk().get(new Point(point.getX()-1, point.getY()i+1)).getbackSideResource().equals(SecondarySource))
                {
                    i-=2;
                    if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
                    {
                        for(int j = 0; j <= 2; j = j + 2)
                            desk.getDesk().remove(new Point((int)point.getX(), (int)point.getY()+i+j));
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


    private boolean CheckRightTopCorner(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
        int i = 2;
        if(PrimarySource.equals(ResourceToSearch))
        {
            while(desk.getDesk().containsKey(new Point((int)point.getX(), (int)point.getY()+i)) && desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(Resource.ANIMAL_KINGDOM))
            {
                //it goes down until it finds a card with a different resource
                i-=2;
            }
            //note: every card are space eachother of at least one cell
            i+=2;
            //note this if is useless because I already know that's the right card
            if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
            {
                //check if the card in the top right corner has the secondary Resource
                if(desk.getDesk().get(new Point((int)point.getX()+1, (int)point.getY()+i-1)).getbackSideResource().equals(SecondarySource))
                {
                    i-=2;
                    if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
                    {
                        for(int j = 0; j <= 2; j = j + 2)
                            desk.getDesk().remove(new Point((int)point.getX(), (int)point.getY()+i+j));
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



    private boolean CheckLeftTopCorner(PlayerDesk desk, Resource ResourceToSearch, Point point)
    {
    int i = 2;
    if(PrimarySource.equals(ResourceToSearch))
    {
        while(desk.getDesk().containsKey(new Point((int)point.getX(), (int)point.getY()+i)) && desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(Resource.INSECT_KINGDOM))
        {
            //it goes down until it finds a card with a different resource
            i-=2;
        }
        //note: every card are space eachother of at least one cell
        i+=2;
        //note this if is useless because I already know that's the right card
        if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
        {
            //check if the card in the top left corner has the secondary Resource
            if(desk.getDesk().get(new Point(point.getX()+1, point.getY()i-1)).getbackSideResource().equals(SecondarySource))
            {
                i-=2;
                if(desk.getDesk().get(new Point((int)point.getX(), (int)point.getY()+i)).getbackSideResource().equals(PrimarySource))
                {
                    for(int j = 0; j <= 2; j = j + 2)
                        desk.getDesk().remove(new Point((int)point.getX(), (int)point.getY()+i+j));
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