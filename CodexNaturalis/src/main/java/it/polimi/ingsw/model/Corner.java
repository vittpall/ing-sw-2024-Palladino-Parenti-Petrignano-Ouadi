package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumeration.CornerObject;
import it.polimi.ingsw.model.enumeration.Resource;

import java.io.Serializable;

/**
 * this class defines the card's corner
 * hidden is an attribute that is set as true if the corner can not be covered and is set as false otherwise
 * resource is an attribute that represents the corner's resource, if present
 * object is an attribute that represents the corner's object, if present
 */
public class Corner implements Serializable {

    private boolean hidden;
    private final Resource resource;
    private final CornerObject object;

    /**
     * Constructor for creating a corner without any resources or objects.
     *
     * @param hidden whether the corner is hidden or not
     */
    public Corner(boolean hidden) {
        this.hidden = hidden;
        this.resource = null;
        this.object = null;
    }

    /**
     * Constructor for creating a corner with a resource.
     *
     * @param resource the resource to be placed in the corner
     */
    public Corner(Resource resource) {
        this.hidden = false;
        this.resource = resource;
        this.object = null;
    }

    /**
     * Constructor for creating a corner with an object.
     *
     * @param object the object to be placed in the corner
     */
    public Corner(CornerObject object) {
        this.hidden = false;
        this.resource = null;
        this.object = object;
    }

    /**
     * Copy constructor for creating a new corner that is a copy of the given corner.
     *
     * @param cornerToCopy the corner to be copied
     */
    public Corner(Corner cornerToCopy) {
        this.hidden = cornerToCopy.hidden;
        this.resource = cornerToCopy.resource;
        this.object = cornerToCopy.object;
    }

    /**
     * @return whether the corner is hidden or not
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return the resource present in the corner, if any
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * @return the object present in the corner, if any
     */
    public CornerObject getObject() {
        return object;
    }

    /**
     * This method hides the corner.
     */
    public void coverCorner() {
        this.hidden = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corner corner = (Corner) o;
        return hidden == corner.hidden &&
                resource == corner.resource &&
                object == corner.object;
    }

}