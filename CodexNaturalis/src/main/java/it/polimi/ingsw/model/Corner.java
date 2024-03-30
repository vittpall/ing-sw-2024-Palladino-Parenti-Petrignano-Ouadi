package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;

/**
 * this class defines the card's corner
 * hidden is an attribute that is set as true if the corner can not be covered and is set as false otherwise
 * resource is an attribute that represents the corner's resource, if present
 * object is an attribute that represents the corner's object, if present
 */
public class Corner{

    private boolean hidden;
    private final Resource resource;
    private final CornerObject object;

    /**
     * constructor to call if the corner does not have resources or objects
     * @param hidden
     */
    public Corner(boolean hidden){
        this.hidden=hidden;
        this.resource=null;
        this.object=null;
    }

    /**
     * constructor to call if the corner has a resource
     * @param hidden
     * @param resource
     */
    public Corner(boolean hidden, Resource resource){
        this.hidden=hidden;
        this.resource=resource;
        this.object=null;
    }

    /**
     * constructor to call if the corner has an object
     * @param hidden
     * @param object
     */
    public Corner(boolean hidden, CornerObject object){
        this.hidden=hidden;
        this.resource=null;
        this.object=object;
    }

    /**
     * @return hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return resource
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @return object
     */
    public CornerObject getObject() {
        return object;
    }

    /**
     * sets hidden as true
     */
    private void coverCorner(){
        this.hidden=true;
    }

}