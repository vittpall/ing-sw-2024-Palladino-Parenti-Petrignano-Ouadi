package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.Resource;

import java.util.*;


public class PlayerDesk {
    private HashMap<int[], GameCard> desk;
    private HashSet<int[]> availablePlaces;
    private HashSet<int[]> forbiddenPlaces;

    /**
     * constructor
     * it creates desk, forbiddenPlaces and availablePlaces and adds the array (0,0) to availablePlaces
     */
    public PlayerDesk() {
        int[] k= new int[2];
        k[0]=0;
        k[1]=0;
        desk= new HashMap<>();
        forbiddenPlaces=new HashSet<>();
        availablePlaces= new HashSet<>();
        availablePlaces.add(k);
    }

    /**
     *
     * @return a copy of the attribute desk
     */
    public HashMap<int[], GameCard> getDesk(){
        return new HashMap<>(desk);
    }

    /**
     * @return the copy of the attribute availablePlaces
     */
    public HashSet<int[]> getAvailablePlaces(){
        return new HashSet<>(availablePlaces);
    }

    /**
     * @return the copy of the attribute forbiddenPlaces
     */
    public HashSet<int[]> getForbiddenPlaces(){
        return new HashSet<>(forbiddenPlaces);
    }

    public boolean checkTotalRequirements(EnumMap<Resource, Integer> requirements)
            throws RequirementsNotMetException{
        boolean requirementsMet=true;
        if(requirements.get(Resource.PLANT_KINGDOM)!=null && requirements.get(Resource.PLANT_KINGDOM)>0){
            requirementsMet=this.checkRequirements(requirements.get(Resource.PLANT_KINGDOM), Resource.PLANT_KINGDOM);
            if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
        }
        if(requirements.get(Resource.ANIMAL_KINGDOM)!=null && requirements.get(Resource.ANIMAL_KINGDOM)>0){
            requirementsMet=this.checkRequirements(requirements.get(Resource.ANIMAL_KINGDOM), Resource.ANIMAL_KINGDOM);
            if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
        }
        if(requirements.get(Resource.FUNGI_KINGDOM)!=null && requirements.get(Resource.FUNGI_KINGDOM)>0){
            requirementsMet=this.checkRequirements(requirements.get(Resource.FUNGI_KINGDOM), Resource.FUNGI_KINGDOM);
            if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
        }
        if(requirements.get(Resource.INSECT_KINGDOM)!=null && requirements.get(Resource.INSECT_KINGDOM)>0){
            requirementsMet=this.checkRequirements(requirements.get(Resource.INSECT_KINGDOM), Resource.INSECT_KINGDOM);
            if(!requirementsMet) throw new RequirementsNotMetException("requirements not met");
        }
        return requirementsMet;
    }
    /**
     * check if the user's desk has the requirements needed
     * @param numResourceNeeded represents the type of the resource required
     * @param resource represents how many resources are needed
     * @return true if the requirements are met; false is they are not
     */
    private boolean checkRequirements(int numResourceNeeded, Resource resource) {
        int nResourcesPresent=0;
        for(GameCard card : desk.values()){
            if(card.isPlayedFaceDown()){
                if(card.getCardResourceBack().equals(resource)) {
                    nResourcesPresent++;
                    if(nResourcesPresent>=numResourceNeeded)return true;
                }
            }else{
                for(Resource res: card.getCardResourcesFront()){
                    if(res.equals(resource)){
                        nResourcesPresent++;
                        if(nResourcesPresent>=numResourceNeeded)return true;
                    }
                }
                for(Corner cardCorner: card.getCorners()){
                    if((!cardCorner.isHidden())&&cardCorner.getResource().equals(resource)){
                        nResourcesPresent++;
                        if(nResourcesPresent>=numResourceNeeded)return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * adds the couple <(x,y), card> into the desk and calls coverCorner
     * @param card
     * @param x
     * @param y
     * @return the points that the player gains after the move
     */
    public int addCard(GameCard card, Integer x, Integer y) {
        int pointsToAdd=0;
        int[] k=new int[2];
        k[0]=x;
        k[1]=y;
        if(availablePlaces.remove(k)) {
            pointsToAdd = this.getPointsToAdd(card);
            desk.put(k, card);
            Corner[] cardCorners=card.getCorners();
            int addIfFaceDown= card.isPlayedFaceDown() ? 4 : 0;
            for(int i=0;i<4;i++){
                int[]pos;
                int cornerToCover;
                switch(i){
                    case 0:
                        pos= new int[]{x - 1, y - 1};
                    case 1:
                        pos=new int[]{x + 1, y - 1};
                    case 2:
                        pos=new int[]{x + 1, y + 1};
                    default:
                        pos=new int[]{x - 1, y + 1};
                }
                cornerToCover=(i+2)%4;
                if(desk.containsKey(pos)){
                    int cardToCoverFacedDown=desk.get(pos).isPlayedFaceDown() ? 4 : 0;
                    desk.get(pos).getCorners()[cornerToCover+cardToCoverFacedDown].coverCorner();
                }else if(cardCorners[i+addIfFaceDown].isHidden()){
                    availablePlaces.remove(pos);
                    forbiddenPlaces.add(pos);
                }else if(!forbiddenPlaces.contains(pos)){
                    availablePlaces.add(pos);
                }
            }
        }
        return pointsToAdd;
    }

    /**
     * @param card
     * @return the points that the player gains thanks to that card
     */
    private int getPointsToAdd(GameCard card){
        //tenere traccia del numero di oggetti totali in un attributo del desk e fare gli if a
        //seconda del PointType
    }

}