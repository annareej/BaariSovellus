package baarisovellus;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="INGREDIENT")
public class Ingredient {

    int id;
    String name;
    
    private Set<DrinkIngredient> drinkIngredients; //= new HashSet();
    
    public Ingredient(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public Ingredient(){
        
    }
    
    @Id
    @GeneratedValue
    @Column(name="INGREDIENT_ID")
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    @Column(name="NAME")
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    @OneToMany(mappedBy="ingredient")
    public Set<DrinkIngredient> getDrinkIngredients(){
        return drinkIngredients;
    }
    
    public void setDrinkIngredients(Set<DrinkIngredient> drinkIngredients){
        this.drinkIngredients = drinkIngredients;
    }
    
}
