/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.model.data;

import java.time.Instant;
import java.util.UUID;
/**
 *
 * @author Agnieszka
 */
public class Activity {
    public final Type type;
    public final UUID objectId;
    public final Instant creationTime;

    public Activity(Type type, UUID objectId, Instant creationTime){

        this.type = type;
        this.objectId = objectId;
        this.creationTime = creationTime;
    }

    public Type getType() {
        return type;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

}
