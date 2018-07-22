package baarisovellus;

import java.util.HashSet;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="DRINK")
public class Drink {
    
    private int id;
    private String name;
    private String glass;
    private String method;
    
    private Set<DrinkIngredient> drinkIngredients; //= new HashSet();
    
    public Drink(int id, String name, String glass, String method){
        this.id = id;
        this.name = name;
        this.glass = glass;
        this.method = method;
        drinkIngredients = new HashSet();
    }
    
    public Drink(){
        
    }
    
    @Id
    @GeneratedValue
    @Column(name = "DRINK_ID")
    public int getId() {
        return id;
    }
    
    @Column(name="NAME")
    public String getName(){
        return name;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    @Column(name="GLASS")
    public String getGlass(){
        return glass;
    }
    
    public void setGlass(String glass){
        this.glass = glass;
    }
    
    @Column(name="METHOD")
    public String getMethod(){
        return method;
    }
    
    public void setMethod(String method){
        this.method = method;
    }
    
    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    public Set<DrinkIngredient> getDrinkIngredients(){
        return drinkIngredients;
    }
    
    public void setDrinkIngredients(Set<DrinkIngredient> drinkIngredients){
        this.drinkIngredients = drinkIngredients;
    }
}
