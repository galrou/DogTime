package entities;

import java.io.Serializable;

/**
 * Created by gal on 03/05/2018.
 */

public class Dog implements Serializable {
    private int id;//the id of the the dog
    private String breedname;//the breed name of the dog
    private String image;//the image's name
    private String height;//the dog's height
    private String weight;//the dog's weight
    private String lifeSpan;//the dog's life span
    private String personality;//the dog's personality
    private String health;//the dog's health
    private String feeding;//recommended feeding for the dog
    private String childrenAndPets;//how is the dog with children and pets
    private String care;//care of the dog

    /**
     * a constructor that sets the id,breedname,image,height,weight,lifespan,personality,
     * health,feeding,childernandpets and care of the the dog
     * @param id
     * @param breedname
     * @param image
     * @param height
     * @param weight
     * @param lifeSpan
     * @param personality
     * @param health
     * @param feeding
     * @param childrenAndPets
     * @param care
     */
    public Dog(int id, String breedname, String image, String height, String weight, String lifeSpan,
               String personality, String health, String feeding, String childrenAndPets, String care) {

        this.id = id;
        this.breedname = breedname;
        this.image = image;
        this.height = height;
        this.weight = weight;
        this.lifeSpan = lifeSpan;
        this.personality = personality;
        this.health = health;
        this.feeding = feeding;
        this.childrenAndPets = childrenAndPets;
        this.care = care;

    }

    public String getHealth() {
        return health;
    }

    public String getFeeding() {
        return feeding;
    }

    public String getChildrenAndPets() {
        return childrenAndPets;
    }

    public String getCare() {
        return care;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }


    public String getPersonality() {
        return personality;
    }

    public String toString() {
        return breedname;
    }

    public String getBreedname() {
        return breedname;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}




