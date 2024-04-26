package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Exceptions.PlaceNotAvailableException;
import it.polimi.ingsw.model.Exceptions.RequirementsNotMetException;
import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.PointType;
import it.polimi.ingsw.model.enumeration.Resource;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.EnumMap;
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
    void getTotalResources_whenDeskIsNotEmpty() throws PlaceNotAvailableException {
        PlayerDesk desk = new PlayerDesk();
        Resource backResource = Resource.INSECT_KINGDOM;
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(backResource, null, null, 0, null, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(0,0));
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
    void getTotalObjects_whenDeskIsNotEmpty() throws PlaceNotAvailableException {
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
    void getDesk_whenDeskIsNotEmpty() throws PlaceNotAvailableException {
        PlayerDesk desk = new PlayerDesk();
        Point avPos = desk.getAvailablePlaces().iterator().next();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, avPos);
        HashMap<Point, GameCard> expectedDesk = new HashMap<>();
        expectedDesk.put(avPos, cardToAdd);
        assertEquals(expectedDesk, desk.getDesk());
    }
    @Test
    void getAvailablePlaces_whenDeskIsEmpty() {
        PlayerDesk desk = new PlayerDesk();
        HashSet<Point> expectedAvailablePlaces = new HashSet<>();
        expectedAvailablePlaces.add(new Point(0,0));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
    }
    @Test
    void getAvailablePlaces_whenDeskIsNotEmpty() throws PlaceNotAvailableException {
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
    void getForbiddenPlaces_whenDeskIsNotEmpty() throws PlaceNotAvailableException {
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
        HashSet<Point> expectedForbiddenPlaces = new HashSet<>();
        expectedForbiddenPlaces.add(new Point(1,1));
        expectedForbiddenPlaces.add(new Point(1,-1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());
    }
    @Test
    void checkRequirements_checkIfItThrowsTheException() throws PlaceNotAvailableException {
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1||i==0)
                corners[i] = new Corner(Resource.FUNGI_KINGDOM);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));

        EnumMap<Resource, Integer> requirements=new EnumMap<>(Resource.class);
        requirements.put(Resource.FUNGI_KINGDOM, 2);
        requirements.put(Resource.INSECT_KINGDOM, 1);
        assertThrows(RequirementsNotMetException.class, ()->desk.checkRequirements(requirements));
    }
    @Test
    void checkRequirements_checkIfItDoesNotThrowTheExceptionWhenRequirementsAreMet() throws PlaceNotAvailableException {
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            if(i==1)
                corners[i] = new Corner(Resource.FUNGI_KINGDOM);
            else if(i==2)
                corners[i] = new Corner(Resource.INSECT_KINGDOM);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(0,0));

        EnumMap<Resource, Integer> requirements=new EnumMap<>(Resource.class);
        requirements.put(Resource.FUNGI_KINGDOM, 1);
        requirements.put(Resource.INSECT_KINGDOM, 1);
        assertDoesNotThrow(()->desk.checkRequirements(requirements));
    }
    @Test
    void addCard_updateTotalResourcesWhenACornerIsCovered() throws PlaceNotAvailableException {
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
        desk.addCard(cardToAdd, new Point(1,1));
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
    void addCard_updateTotalObjectsWhenACornerIsCovered() throws PlaceNotAvailableException {
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
        desk.addCard(cardToAdd, new Point(1,1));
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
    void addCard_updateAvailablePlacesWhenACardIsPlacedAndACornerIsCovered() throws PlaceNotAvailableException {
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
        expectedAvailablePlaces.clear();
        expectedAvailablePlaces.add(new Point(0,2));
        expectedAvailablePlaces.add(new Point(1,-1));
        assertEquals(expectedAvailablePlaces, desk.getAvailablePlaces());
    }
    @Test
    void addCard_updateForbiddenPlacesWhenACardIsPlacedAndACornerIsCovered() throws PlaceNotAvailableException {
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
        expectedForbiddenPlaces.clear();
        expectedForbiddenPlaces.add(new Point(-1,1));
        expectedForbiddenPlaces.add(new Point(-1,-1));
        expectedForbiddenPlaces.add(new Point(2,2));
        expectedForbiddenPlaces.add(new Point(2,0));
        assertEquals(expectedForbiddenPlaces, desk.getForbiddenPlaces());
    }
    @Test
    void addCard_updateCornersOfTheCoveredCardWhenACardIsPlacedOverOneOfItsCorners() throws PlaceNotAvailableException {
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

        for(int i=0;i< corners.length;i++){
            if(i==4||i==7)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(Resource.ANIMAL_KINGDOM, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(true);
        desk.addCard(cardToAdd, new Point(1,1));

        for(int i=0;i< corners.length;i++){
            if(i==2)
                assertFalse(desk.getDesk().get(new Point(0,0)).getCorner(i).isHidden());
            else
                assertTrue(desk.getDesk().get(new Point(0,0)).getCorner(i).isHidden());
        }
    }
    @Test
    void addCard_doNotRemoveAForbiddenPlaceWhenItCouldBeAvailableForOnlyOneAdjacentCard() throws PlaceNotAvailableException {
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
        for(int i=0;i< corners.length;i++){
            if(i==1||i==2)
                corners[i] = new Corner(false);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(1,1));

        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(false);
        }
        cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        desk.addCard(cardToAdd, new Point(-1,1));
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
    void addCard_checkIfItThrowsTheExceptionIfThePlaceIsNotAvailable(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        assertThrows(PlaceNotAvailableException.class, ()->desk.addCard(cardToAdd, new Point(1,1)));
    }
    @Test
    void addCard_checkIfItDoesNotThrowTheExceptionIfThePlaceIsAvailable(){
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for(int i=0;i< corners.length;i++){
            corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 0, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        assertDoesNotThrow(()->desk.addCard(cardToAdd, new Point(0,0)));
    }
    @Test
    void addCard_checkIfItReturnsTheCorrectPointsToAdd() throws PlaceNotAvailableException {
        PlayerDesk desk = new PlayerDesk();
        Corner[] corners = new Corner[8];
        for (int i = 0; i < corners.length; i++) {
            if (i == 1)
                corners[i] = new Corner(CornerObject.INKWELL);
            else if (i == 2)
                corners[i] = new Corner(CornerObject.QUILL);
            else
                corners[i] = new Corner(true);
        }
        GameCard cardToAdd = new ResourceCard(null, null, null, 1, PointType.INKWELL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        int pointsToAdd = desk.addCard(cardToAdd, new Point(0, 0));
        assertEquals(1, pointsToAdd);


        for (int i = 0; i < corners.length; i++) {
            if (i == 1)
                corners[i] = new Corner(CornerObject.MANUSCRIPT);
            else if (i == 2)
                corners[i] = new Corner(CornerObject.QUILL);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 1, PointType.QUILL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        pointsToAdd = desk.addCard(cardToAdd, new Point(1, -1));
        assertEquals(1, pointsToAdd);

        for (int i = 0; i < corners.length; i++) {
            if (i == 1 || i == 3)
                corners[i] = new Corner(CornerObject.MANUSCRIPT);
            else if (i == 2)
                corners[i] = new Corner(CornerObject.QUILL);
            else
                corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 1, PointType.MANUSCRIPT, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        pointsToAdd = desk.addCard(cardToAdd, new Point(1, 1));
        assertEquals(3, pointsToAdd);

        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 2, PointType.CORNER, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        pointsToAdd = desk.addCard(cardToAdd, new Point(2, 0));
        assertEquals(4, pointsToAdd);

        for (int i = 0; i < corners.length; i++) {
            corners[i] = new Corner(true);
        }
        cardToAdd = new ResourceCard(null, null, null, 2, PointType.GENERAL, null, corners);
        cardToAdd.setPlayedFaceDown(false);
        pointsToAdd = desk.addCard(cardToAdd, new Point(2, -2));
        assertEquals(2, pointsToAdd);
    }
}