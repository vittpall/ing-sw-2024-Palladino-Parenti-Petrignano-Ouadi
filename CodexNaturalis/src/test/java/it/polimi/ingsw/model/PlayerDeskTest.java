package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDeskTest {

    @Test
    void getTotalResources_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        for(Resource res : desk.getTotalResources().keySet()){
            assertEquals(0, desk.getTotalResources().get(res));
        }
    }
    @Test
    void getTotalResources_whenDeskIsNotEmpty() {
        PlayerDesk desk = new PlayerDesk();
        Resource backResource = Resource.INSECT_KINGDOM;
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(backResource, null, null, 0, null, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        for(Resource res : desk.getTotalResources().keySet()){
            if(res== backResource)
                assertEquals(1, desk.getTotalResources().get(res));
            else
                assertEquals(0, desk.getTotalResources().get(res));
        }
    }
    @Test
    void getTotalObjects_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            assertEquals(0, desk.getTotalObjects().get(obj));
        }
    }

    @Test
    void getTotalObjects_whenDeskIsNotEmpty(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        corners[0] = new Corner(CornerObject.MANUSCRIPT);
        corners[1]=new Corner(CornerObject.INKWELL);
        for(int i=2;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard card = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        card.setPlayedFaceDown(false);
        desk.addCard(card, new Point(0, 0));
        desk.updateDesk(card, new Point(0,0));
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            if(obj== CornerObject.QUILL)
                assertEquals(0, desk.getTotalObjects().get(obj));
            else
                assertEquals(1, desk.getTotalObjects().get(obj));
        }
    }
    @Test
    void getDesk_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        assertEquals(new HashMap<>(), desk.getDesk());
    }
@Test
    void getDesk_whenDeskIsNotEmpty() {
        PlayerDesk desk = new PlayerDesk();
        Point avPos = desk.getAvailablePlaces().iterator().next();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, avPos);
        desk.updateDesk(cardToAdd, avPos);
        HashMap<Point, GameCard> expectedDesk = new HashMap<>();
        expectedDesk.put(avPos, cardToAdd);
        assertEquals(expectedDesk, desk.getDesk());
    }

    @Test
    void updateDesk_updateTotalResourcesWhenACornerIsCovered(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1)
                corners[i] = new Corner(Resource.ANIMAL_KINGDOM);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        for(Resource res : desk.getTotalResources().keySet()){
            if(res!= Resource.ANIMAL_KINGDOM)
                assertEquals(0, desk.getTotalResources().get(res));
            else
                assertEquals(1, desk.getTotalResources().get(res));
        }
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            assertEquals(0, desk.getTotalObjects().get(obj));
        }
        Point avPos = desk.getAvailablePlaces().iterator().next();
        for(int i=0; i<corners.length; i++){
            if(i==1)
                corners[i] = new Corner(Resource.FUNGI_KINGDOM);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.updateDesk(cardToAdd, avPos);
        for(Resource res : desk.getTotalResources().keySet()){
            if(res!= Resource.FUNGI_KINGDOM)
                assertEquals(0, desk.getTotalResources().get(res));
            else
                assertEquals(1, desk.getTotalResources().get(res));
        }
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            assertEquals(0, desk.getTotalObjects().get(obj));
        }
    }
    @Test
    void updateDesk_updateTotalObjectsWhenACornerIsCovered(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1)
                corners[i] = new Corner(CornerObject.INKWELL);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            if(obj==CornerObject.INKWELL)
                assertEquals(1, desk.getTotalObjects().get(obj));
            else
                assertEquals(0, desk.getTotalObjects().get(obj));
        }
        for(Resource res : desk.getTotalResources().keySet()){
                assertEquals(0, desk.getTotalResources().get(res));
        }
        Point avPos = desk.getAvailablePlaces().iterator().next();
        for(int i=0; i<corners.length; i++){
            if(i==1)
                corners[i] = new Corner(CornerObject.MANUSCRIPT);
            else
                corners[i] = new Corner(true);        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.updateDesk(cardToAdd, avPos);
        for(CornerObject obj : desk.getTotalObjects().keySet()){
            if(obj==CornerObject.MANUSCRIPT)
                assertEquals(1, desk.getTotalObjects().get(obj));
            else
                assertEquals(0, desk.getTotalObjects().get(obj));
        }
        for(Resource res : desk.getTotalResources().keySet()){
            assertEquals(0, desk.getTotalResources().get(res));
        }
    }
    @Test
    void updateDesk_updateAvailablePlacesWhenACardIsPlacedAndACornerIsCovered(){
        PlayerDesk desk = new PlayerDesk();
        assertEquals(new Point(0,0), desk.getAvailablePlaces().iterator().next());
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1||i==2)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        HashSet<Point> expectedAvailablePlaces = new HashSet<>();
        expectedAvailablePlaces.add(new Point(1,1));
        expectedAvailablePlaces.add(new Point(1,-1));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());


        for(int i=0;i< corners.length;i++){
            if(i==4||i==7)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(1,1));
        desk.updateDesk(cardToAdd, new Point(1,1));
        expectedAvailablePlaces.clear();
        expectedAvailablePlaces.add(new Point(0,2));
        expectedAvailablePlaces.add(new Point(1,-1));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
    }
    @Test
    void updateDesk_updateForbiddenPlacesWhenACardIsPlacedAndACornerIsCovered(){
        PlayerDesk desk = new PlayerDesk();
        assertEquals(new HashSet<>(),desk.getForbiddenPlaces());
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1||i==2)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        HashSet<Point> expectedForbiddenPlaces = new HashSet<>();
        expectedForbiddenPlaces.add(new Point(-1,1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());


        for(int i=0;i< corners.length;i++){
            if(i==4||i==7)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(1,1));
        desk.updateDesk(cardToAdd, new Point(1,1));
        expectedForbiddenPlaces.clear();
        expectedForbiddenPlaces.add(new Point(-1,1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        expectedForbiddenPlaces.add(new Point(2,2));
        expectedForbiddenPlaces.add(new Point(2,0));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());
    }

    @Test
    void updateDesk_updateCornersOfTheCoveredCardWhenACardIsPlacedOverOneOfItsCorners(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1||i==2)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));

        for(int i=0;i< corners.length;i++){
            if(i==4||i==7)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(1,1));
        desk.updateDesk(cardToAdd, new Point(1,1));

        for(int i=0;i< corners.length;i++){
            if(i==2)
                assertFalse(desk.getDesk().get(new Point(0,0)).getCorner(i).isHidden());
            else
                assertTrue(desk.getDesk().get(new Point(0,0)).getCorner(i).isHidden());
        }
    }
    @Test
    void updateDesk_doNotRemoveAForbiddenPlaceWhenItCouldBeAvailableForOnlyOneAdjacentCard(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1||i==0)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        for(int i=0;i< corners.length;i++){
            if(i==1||i==2)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(1,1));
        desk.updateDesk(cardToAdd, new Point(1,1));

        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(false);
        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(-1,1));
        desk.updateDesk(cardToAdd, new Point(-1,1));
        HashSet<Point> expectedAvailablePlaces = new HashSet<>();
        expectedAvailablePlaces.add(new Point(2,2));
        expectedAvailablePlaces.add(new Point(2,0));
        expectedAvailablePlaces.add(new Point(-2,2));
        expectedAvailablePlaces.add(new Point(-2,0));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
        HashSet<Point> expectedForbiddenPlaces = new HashSet<>();
        expectedForbiddenPlaces.add(new Point(1,-1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        expectedForbiddenPlaces.add(new Point(0,2));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());
    }
    @Test
    void getAvailablePlaces_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        HashSet<Point> expectedAvailablePlaces = new HashSet<>();
        expectedAvailablePlaces.add(new Point(0,0));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
    }
    @Test
    void getAvailablePlaces_whenDeskIsNotEmpty() {
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==0)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        HashSet<Point> expectedAvailablePlaces = new HashSet<>();
        expectedAvailablePlaces.add(new Point(-1,1));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
    }
    @Test
    void getForbiddenPlaces_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        assertEquals(new HashSet<>(), desk.getForbiddenPlaces());
    }
    @Test
    void getForbiddenPlaces_whenDeskIsNotEmpty() {
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==0)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));
        desk.updateDesk(cardToAdd, new Point(0,0));
        HashSet<Point> expectedForbiddenPlaces = new HashSet<>();
        expectedForbiddenPlaces.add(new Point(1,1));
        expectedForbiddenPlaces.add(new Point(1,-1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());
    }
    @Test
    void checkRequirements() {
    }

    @Test
    void addCard() {
    }

}