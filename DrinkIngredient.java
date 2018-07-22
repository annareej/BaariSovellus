package baarisovellus;

import javax.persistence.*;

@Entity
@Table(name="DRINK_INGREDIENTS")
public class DrinkIngredient {
    private int id;
    private Drink drink;
    private Ingredient ingredient;
    
    private int amount;
    private String measurement;
    
    @Id
    @GeneratedValue
    @Column(name="DRINK_INGREDIENT_ID")
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DRINK_ID")
    public Drink getDrink(){
        return drink;
    }
    
    public void setDrink(Drink drink){
        this.drink = drink;
    }
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INGREDIENT_ID")
    public Ingredient getIngredient(){
        return ingredient;
    }
    
    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
    }
    
    @Column(name = "AMOUNT")
    public int getAmount(){
        return amount;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }
    
    @Column(name = "MEASUREMENT")
    public String getMeasurement(){
        return measurement;
    }
    
    public void setMeasurement(String measurement){
        this.measurement = measurement;
    }
    
    public String toString(){
        return ingredient.getName()+" "+this.amount+" "+this.measurement;
    }
}
