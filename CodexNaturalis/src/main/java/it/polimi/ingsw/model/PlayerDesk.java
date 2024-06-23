package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.model.exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.exceptions.RequirementsNotMetException;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * this class represents the player's desk.
 * desk represents the position of every card that the player has played
 * availablePlaces represents the places where the player can put a card
 * forbiddenPlaces represents the places where the player can not put a card
 * totalResources takes account of how many Resources are visible into the player's desk
 * totalObjects takes account of how many CornerObjects are visible into the player's desk
 */
public class PlayerDesk implements Serializable {
    private final HashMap<Point, GameCard> desk;
    private final HashSet<Point> availablePlaces;
    private final HashSet<Point> forbiddenPlaces;
    private final EnumMap<Resource, Integer> totalResources;
    private final EnumMap<CornerObject, Integer> totalObjects;

    /**
     * constructor
     * it creates desk, forbiddenPlaces and availablePlaces and adds the Pint (0,0) to availablePlaces
     * it initializes totalResources and totalObjects
     */
    public PlayerDesk() {
        Point k = new Point(0, 0);
        desk = new LinkedHashMap<>();
        forbiddenPlaces = new HashSet<>();
        availablePlaces = new HashSet<>();
        availablePlaces.add(k);
        totalResources = new EnumMap<>(Resource.class);
        for (Resource res : Resource.values()) {
            totalResources.put(res, 0);
        }
        totalObjects = new EnumMap<>(CornerObject.class);
        for (CornerObject obj : CornerObject.values()) {
            totalObjects.put(obj, 0);
        }
    }

    /**
     * copy constructor
     * @param deskToCopy the desk that needs to be copied
     */
    public PlayerDesk(PlayerDesk deskToCopy) {
        this.desk = new LinkedHashMap<>(deskToCopy.getDesk());
        this.availablePlaces = new HashSet<>(deskToCopy.getAvailablePlaces());
        this.forbiddenPlaces = new HashSet<>(deskToCopy.getForbiddenPlaces());
        this.totalResources = new EnumMap<>(deskToCopy.getTotalResources());
        this.totalObjects = new EnumMap<>(deskToCopy.getTotalObjects());
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
     * @return desk
     */
    public HashMap<Point, GameCard> getDesk() {
        return new LinkedHashMap<>(desk);
    }

    /**
     * @return availablePlaces
     */
    public HashSet<Point> getAvailablePlaces() {
        return new HashSet<>(availablePlaces);
    }

    /**
     * @return forbiddenPlaces
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
     * adds the couple <(x,y), card> into the desk
     *
     * @param card  The GameCard to be added to the desk.
     * @param point The point on the desk where the card is to be added.
     * @return the points that the player gains after the move
     */
    public int addCard(GameCard card, Point point) throws PlaceNotAvailableException {
        int pointsToAdd;
        if (availablePlaces.remove(point)) {
            desk.put(point, card);
            this.updateDesk(card, point);
            pointsToAdd = card.isPlayedFaceDown() ? 0 : this.getPointsToAdd(card, point);
        } else
            throw new PlaceNotAvailableException("Place" + point + " not available");
        return pointsToAdd;
    }

    /**
     * update the desk when a new card is put:
     * covers the card's adjacent corners
     * removes from totalObjects and totalResources the objects and resources covered by the new card
     * adds to totalObjects and totalResources the objects and resources of the new card
     * update the forbiddenPlaces and availablePlaces lists
     *
     * @param card  the card that has been put
     * @param point the card's position
     */
    private void updateDesk(GameCard card, Point point) {
        Corner[] cardCorners = card.getCorners();
        int addIfFaceDown = card.isPlayedFaceDown() ? 4 : 0;
        for (int i = 0; i < 4; i++) {
            Point pos;
            int cornerToCover;
            pos = switch (i) {
                case 0 -> new Point(point.x - 1, point.y + 1);
                case 1 -> new Point(point.x + 1, point.y + 1);
                case 2 -> new Point(point.x + 1, point.y - 1);
                default -> new Point(point.x - 1, point.y - 1);
            };
            cornerToCover = (i + 2) % 4;
            if (desk.containsKey(pos)) {
                int cardToCoverFacedDown = desk.get(pos).isPlayedFaceDown() ? 4 : 0;
                if (desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getObject() != null) {
                    int obj = totalObjects.get(desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getObject());
                    obj--;
                    totalObjects.put(desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getObject(), obj);
                } else if (desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getResource() != null) {
                    int res = totalResources.get(desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getResource());
                    res--;
                    totalResources.put(desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).getResource(), res);
                }
                desk.get(pos).getCorner(cornerToCover + cardToCoverFacedDown).coverCorner();
            } else if (cardCorners[i + addIfFaceDown].isHidden()) {
                availablePlaces.remove(pos);
                forbiddenPlaces.add(pos);
            } else if (!forbiddenPlaces.contains(pos)) {
                availablePlaces.add(pos);
            }
            if (!(cardCorners[i + addIfFaceDown]).isHidden()) {
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
        if (card.isPlayedFaceDown() && !(card instanceof StarterCard)) {
            int res = totalResources.get(card.getBackSideResource());
            res++;
            totalResources.put(card.getBackSideResource(), res);
        } else if (card instanceof StarterCard && !card.isPlayedFaceDown() && card.getFrontSideResources() != null) {
            for (Resource resource : card.getFrontSideResources()) {
                int res = totalResources.get(resource);
                res++;
                totalResources.put(resource, res);
            }
        }
    }

    /**
     * it calculates the points to give to the player thanks to the placing of the card
     *
     * @param card the card added
     * @param p    the card's position
     * @return the points that the player gains thanks to that card
     */
    private int getPointsToAdd(GameCard card, Point p) {
        if (card.getPointType().equals(PointType.GENERAL))
            return card.getPoints();
        if (card.getPointType().equals(PointType.INKWELL)) {
            return card.getPoints() * totalObjects.get(CornerObject.INKWELL);
        }
        if (card.getPointType().equals(PointType.MANUSCRIPT))
            return card.getPoints() * totalObjects.get(CornerObject.MANUSCRIPT);
        if (card.getPointType().equals(PointType.QUILL))
            return card.getPoints() * totalObjects.get(CornerObject.QUILL);
        int i = 0;
        if (desk.containsKey(new Point(p.x - 1, p.y + 1))) i++;
        if (desk.containsKey(new Point(p.x + 1, p.y + 1))) i++;
        if (desk.containsKey(new Point(p.x + 1, p.y - 1))) i++;
        if (desk.containsKey(new Point(p.x - 1, p.y - 1))) i++;
        return card.getPoints() * i;
    }
}