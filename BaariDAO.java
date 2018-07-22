package baarisovellus;

import java.util.*;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;


public class BaariDAO {
    
    StandardServiceRegistry standardRegistry;
    Metadata metadata;
    SessionFactory sessionFactory;
    Session session;
    Transaction t;
    
    public BaariDAO(){
        
        standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();
        
        session = sessionFactory.openSession();
        t = null;
    }
    
    public List<Drink> getDrinkList(){
        String hql = "from Drink";
        List<Drink> drinks = session.createQuery(hql).list();
        return drinks;
    }
    
    public List<Ingredient> getIngredientList(){
        String hql = "from Ingredient";
        List<Ingredient> ingreds = session.createQuery(hql).list();
        return ingreds;
    }
    
    
    public Drink getDrink(String drink){
        
        String hql = "from Drink where name = \'"+drink+"\'";
        List<Drink> drinks = session.createQuery(hql).list();
        Drink d = (Drink) drinks.get(0);
        return d;
    }
    
    public Ingredient getIngredient(String ingredient){
        String hql = "from Ingredient where name = \'"+ingredient+"\'";
        List<Ingredient> ingredients = session.createQuery(hql).list();
        Ingredient i = (Ingredient) ingredients.get(0);
        return i;
    }
    
    public void addDrink(Drink d){
        t = session.beginTransaction();
        session.save(d);
        t.commit();
    }
    
    public void addIngredient(Ingredient i){
        t = session.beginTransaction();
        session.save(i);
        t.commit();
    }
    
    public void addDrinkIngredient(DrinkIngredient di){
        t = session.beginTransaction();
        session.save(di);
        t.commit();
    }
    
    public void deleteDrink(String name){
        t = session.beginTransaction();
        Drink d = getDrink(name);
        int id = d.getId();
        String hql1 = "delete from DrinkIngredient where drink_id="+id;
        Query query1 = session.createQuery(hql1);
        query1.executeUpdate();
        String hql = "delete from Drink where name = \'"+name+"\'";
        Query query = session.createQuery(hql);
        int kpl = query.executeUpdate();
        t.commit();
    }
    
    public Long countIngredients(String name){
        t = session.beginTransaction();
        String hql = "select count(*) from Ingredient where name=\'"+name+"\'";
        return ( (Number) session.createQuery(hql).iterate().next() ).longValue();
    }
    
    public void close(){
        session.close();
        sessionFactory.close();
    }
    
}
