package baarisovellus;

import java.util.Set;

public class BaariController {
    private BaariModel model;
    private BaariView view;
    private BaariDAO dao;
    
    public BaariController(BaariModel model, BaariView view, BaariDAO dao){
        this.model = model;
        this.view = view;
    }
    
    public static void main(String[] args){
        
        BaariDAO dao = new BaariDAO();
        BaariModel model = new BaariModel(dao);
        
        //dao.close();
        String[] drinkit = model.getDrinks();
        String[] ainekset = model.getIngredients();
        BaariView view = new BaariView(drinkit, ainekset);
        BaariController controller = new BaariController(model, view, dao);
        //model.printString(dao.getIngredientList(view.getDrink()));
        view.rekisteröiOhjain(controller, dao);
        //dao.close();
    }
    
    public void aseta(String d){
        Drink drink = model.getDrink(d);
        String[] ingreds = model.getDrinkIngredients(drink.getDrinkIngredients());
        String glass = drink.getGlass();
        String method = drink.getMethod();
        view.setIngredients(ingreds);
        view.setGlass(glass);
        view.setMethod(method);
    }
    
    public void printString(Drink drink){
        Set<DrinkIngredient> drinks = drink.getDrinkIngredients();
        for (DrinkIngredient s : drinks) {
            System.out.println(s.getIngredient().getName()+" "+s.getAmount());
        }
        /*for (Drink p: drinks){
            System.out.println(p.getName());
        }*/
    }
    
    public void addNewDrink(){
        String nimi = view.getNewDrinkName();
        String lasi = view.getNewGlass();
        String method = view.getNewMethod();
        String[] ainekset = view.getNewIngredients();
        int[] maarat = view.getNewAmounts();
        String[] mitat = view.getNewMeasurements();
        model.addNewDrink(nimi,lasi,method, ainekset, maarat, mitat);
        
        /*for(int i=0;i<ainekset.length;i++){
            System.out.println(ainekset[i]+" "+maarat[i]);
        }*/
    }
    
    public void deleteDrink(){
        String name = view.getDrink();
        model.deleteDring(name);
    }
    
    public void setVaihtoehdot(){
        String[] drinks = model.getDrinks();
        view.setVaihtoehdot(drinks);
    }
    
}
