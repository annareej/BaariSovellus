package baarisovellus;

import java.util.List;
import java.util.*;

public class BaariModel {

    //String[] stringDrinks;
    BaariDAO dao;
    
    public BaariModel(BaariDAO dao){
        this.dao = dao;
    }
    
    public Drink getDrink(String drink){
        return dao.getDrink(drink);
    }
    
    public String[] getDrinks(){
        List<Drink> drinks = dao.getDrinkList();
        int i = 0;
           String[] stringDrinks = new String[drinks.size()];
           for(Drink d: drinks){
               stringDrinks[i] = d.getName();
               i++;
           }
           return stringDrinks;
       }
    
    public String[] getIngredients(){
        List<Ingredient> ingreds = dao.getIngredientList();
        int i = 0;
        String[] stringIngreds = new String[ingreds.size()];
        for(Ingredient ingred: ingreds){
            stringIngreds[i] = ingred.getName();
            i++;
        }
        return stringIngreds;
    }
    
    public String[] getDrinkIngredients(Set<DrinkIngredient> di){
        String[] ingreds = new String[di.size()];
        int i = 0;
        for(DrinkIngredient ingred: di){
            ingreds[i] = ingred.getIngredient().getName()+" "+ingred.getAmount()+" "+ingred.getMeasurement();
            i++;
        }
        return ingreds;
    }
    
    public void addNewDrink(String name, String glass, String method, String[] ingreds, int[] amount, String[] measurements){
        Drink d = new Drink(0, name, glass, method);
        dao.addDrink(d);
        Ingredient ing;
        
        for(int i=0;i<ingreds.length;i++){
            if(existing(ingreds[i])){
                ing = dao.getIngredient(ingreds[i]);                
            }else{
                ing = new Ingredient();
                ing.setName(ingreds[i]);
                dao.addIngredient(ing);
            }
            DrinkIngredient di = new DrinkIngredient();
            di.setAmount(amount[i]);
            di.setDrink(d);
            di.setIngredient(ing);
            di.setMeasurement(measurements[i]);
            dao.addDrinkIngredient(di);
            d.getDrinkIngredients().add(di);            
        }
    }
    
    public boolean existing(String name){
        Long i = dao.countIngredients(name);
        boolean exist;
        if(i!=0){
            exist = true;
        }else{
            exist = false;
        }
        return exist;
    }
    
    public void deleteDring(String name){
        dao.deleteDrink(name);
    }
}
