package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.enumeration.CornerObject;

import java.awt.*;
import java.util.*;

public class PlayerDesk {
    private final HashMap<Point, GameCard> desk;
    private final HashSet<Point> availablePlaces;
    private final HashSet<Point> forbiddenPlaces;
    private final EnumMap<Resource, Integer> totalResources;
    private final EnumMap<CornerObject, Integer> totalObjects;

    /**
     * constructor
     * it creates desk, forbiddenPlaces and availablePlaces and adds the array (0,0) to availablePlaces
     */
    public PlayerDesk() {
        Point k = new Point(0, 0);
        desk = new HashMap<>();
        forbiddenPlaces = new HashSet<>();
        availablePlaces = new HashSet<>();
        availablePlaces.add(k);
        totalResources = new EnumMap<>(Resource.class);
        for(Resource res : Resource.values()){
            totalResources.put(res, 0);
        }
        totalObjects = new EnumMap<>(CornerObject.class);
        for(CornerObject obj : CornerObject.values()){
            totalObjects.put(obj, 0);
        }
    }

    /**
     * @return totalResources
     */
    public EnumMap<Resource, Integer> getTotalResources() {
        return new EnumMap<>(totalResources);
    }

    /**
     * @return totalObjects
     */
    public EnumMap<CornerObject, Integer> getTotalObjects() {
        return new EnumMap<>(totalObjects);
    }

    /**
     * @return a copy of the attribute desk
     */
    public HashMap<Point, GameCard> getDesk() {
        return new HashMap<>(desk);
    }

    /**
     * @return the copy of the attribute availablePlaces
     */
    public HashSet<Point> getAvailablePlaces() {
        return new HashSet<>(availablePlaces);
    }

    /**
     * @return the copy of the attribute forbiddenPlaces
     */
    public HashSet<Point> getForbiddenPlaces() {
        return new HashSet<>(forbiddenPlaces);
    }

    /**
     * check if the requirements sent as a parameter are met into the player's desk
     *
     * @param requirements is a map of the requirements to check
     * @throws RequirementsNotMetException if the requirements are not met
     */
    public void checkRequirements(EnumMap<Resource, Integer> requirements)
            throws RequirementsNotMetException {
        for (Map.Entry<Resource, Integer> requirement : requirements.entrySet()) {
            Resource resource = requirement.getKey();
            Integer requiredAmount = requirement.getValue();
            Integer totalAmount = totalResources.get(resource);

            if (totalAmount == null) {
                totalAmount = 0;
            }

            if (requiredAmount != null && requiredAmount > totalAmount) {
                throw new RequirementsNotMetException("requirements not met for " + resource.get());
            }
        }
    }

    /**
     * adds the couple <(x,y), card> into the desk and calls coverCorner
     *
     * @param card
     * @param x
     * @param y
     * @return the points that the player gains after the move
     */
    public int addCard(GameCard card, Integer x, Integer y) {
        int pointsToAdd = 0;
        Point k = new Point(x, y);
        if (availablePlaces.remove(k)) {
            pointsToAdd = card.isPlayedFaceDown() ? 0 : this.getPointsToAdd(card, k);
            desk.put(k, card);
            Corner[] cardCorners = card.getCorners();
            int addIfFaceDown = card.isPlayedFaceDown() ? 4 : 0;
            for (int i = 0; i < 4; i++) {
                Point pos;
                int cornerToCover;
                switch (i) {
                    case 0:
                        pos = new Point(x - 1, y - 1);
                        break;
                    case 1:
                        pos = new Point(x + 1, y - 1);
                        break;
                    case 2:
                        pos = new Point(x + 1, y + 1);
                        break;
                    default:
                        pos = new Point(x - 1, y + 1);
                        break;
                }
                cornerToCover = (i + 2) % 4;
                if (desk.containsKey(pos)) {
                    int cardToCoverFacedDown = desk.get(pos).isPlayedFaceDown() ? 4 : 0;
                    if (desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getObject() != null) {
                        int obj = totalObjects.get(desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getObject());
                        obj--;
                        totalObjects.put(desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getObject(), obj);
                    } else if (desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getResource() != null) {
                        int res = totalResources.get(desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getResource());
                        res--;
                        totalResources.put(desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].getResource(), res);
                    }
                    desk.get(pos).getCorners()[cornerToCover + cardToCoverFacedDown].coverCorner();
                } else if (cardCorners[i + addIfFaceDown].isHidden()) {
                    availablePlaces.remove(pos);
                    forbiddenPlaces.add(pos);
                } else if (!forbiddenPlaces.contains(pos)) {
                    availablePlaces.add(pos);

                }
                if (!card.isPlayedFaceDown() && !cardCorners[i + addIfFaceDown].isHidden()) {
                    if (cardCorners[i + addIfFaceDown].getResource() != null) {
                        int res = totalResources.get(cardCorners[i + addIfFaceDown].getResource());
                        res++;
                        totalResources.put(cardCorners[i + addIfFaceDown].getResource(), res);
                    } else if (cardCorners[i + addIfFaceDown].getObject() != null) {
                        int obj = totalObjects.get(cardCorners[i + addIfFaceDown].getObject());
                        obj++;
                        totalObjects.put(cardCorners[i + addIfFaceDown].getObject(), obj);
                    }
                }
            }
            if (card.isPlayedFaceDown()) {
                int res = totalResources.get(card.getbackSideResource());
                res++;
                totalResources.put(card.getbackSideResource(), res);
            } else {
                for (Resource resource : card.getfrontSideResources()) {
                    int res = totalResources.get(resource);
                    res++;
                    totalResources.put(resource, res);
                }
            }
        }
        return pointsToAdd;
    }

    /**
     * @param card
     * @return the points that the player gains thanks to that card
     */
    private int getPointsToAdd(GameCard card, Point p) {
        if (card.getPointType().equals(PointType.GENERAL))
            return card.getPoints();
        if (card.getPointType().equals(PointType.INKWELL))
            return card.getPoints() * totalObjects.get(CornerObject.INKWELL);
        if (card.getPointType().equals(PointType.MANUSCRIPT))
            return card.getPoints() * totalObjects.get(CornerObject.MANUSCRIPT);
        if (card.getPointType().equals(PointType.QUILL))
            return card.getPoints() * totalObjects.get(CornerObject.QUILL);
        int i = 0;
        if (desk.containsKey(new Point(p.x - 1, p.y - 1))) i++;
        if (desk.containsKey(new Point(p.x + 1, p.y - 1))) i++;
        if (desk.containsKey(new Point(p.x + 1, p.y + 1))) i++;
        if (desk.containsKey(new Point(p.x - 1, p.y + 1))) i++;
        return card.getPoints() * i;

    }

}