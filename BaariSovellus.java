package baarisovellus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BaariSovellus {

    public static void main(String[] args) {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        
        Drink kalja = new Drink(0,"Kalja","Tuoppi","Hana");
        session.save(kalja);
        Ingredient kalia = new Ingredient(0,"kaliaa");
        session.save(kalia);
        
        
        DrinkIngredient di1 = new DrinkIngredient();
        
        di1.setDrink(kalja);
        di1.setIngredient(kalia);
        di1.setAmount(330);
        di1.setMeasurement("cl");
        
        session.saveOrUpdate(di1);
        
        Drink mojito = new Drink(0,"Mojito","High Ball","Ravistetaan");
        session.saveOrUpdate(mojito);
        
        Ingredient i1 = new Ingredient(0,"Soodavesi");
        session.saveOrUpdate(i1);
        Ingredient i2 = new Ingredient(0,"Sokeri");
        session.saveOrUpdate(i2);
        Ingredient i3 = new Ingredient(0,"Mintunlehti");
        session.saveOrUpdate(i3);
        Ingredient i4 = new Ingredient(0,"Limemehu");
        session.saveOrUpdate(i4);
        Ingredient i5 = new Ingredient(0,"Rommi");
        session.saveOrUpdate(i5);
        
        DrinkIngredient di2 = new DrinkIngredient();
        
        di2.setDrink(mojito);
        di2.setIngredient(i1);
        di2.setAmount(10);
        di2.setMeasurement("cl");
        mojito.getDrinkIngredients().add(di2);
        
        session.saveOrUpdate(di2);
        
        DrinkIngredient di3 = new DrinkIngredient();
        
        di3.setDrink(mojito);
        di3.setIngredient(i2);
        di3.setAmount(2);
        di3.setMeasurement("tl");
        mojito.getDrinkIngredients().add(di3);
        
        session.saveOrUpdate(di3);
        
        DrinkIngredient di4 = new DrinkIngredient();
        
        di4.setDrink(mojito);
        di4.setIngredient(i3);
        di4.setAmount(10);
        di4.setMeasurement("kpl");
        mojito.getDrinkIngredients().add(di4);
        
        session.saveOrUpdate(di4);
        
        DrinkIngredient di5 = new DrinkIngredient();
        
        di5.setDrink(mojito);
        di5.setIngredient(i4);
        di5.setAmount(2);
        di5.setMeasurement("cl");
        mojito.getDrinkIngredients().add(di5);
        
        session.saveOrUpdate(di5);
        
        DrinkIngredient di6 = new DrinkIngredient();
        
        di6.setDrink(mojito);
        di6.setIngredient(i5);
        di6.setAmount(4);
        di6.setMeasurement("cl");
        mojito.getDrinkIngredients().add(di6);
        
        session.saveOrUpdate(di6);
        
        t.commit();
        session.close();
        sessionFactory.close();
        
    }
    
}
