package com.vnpt.system.generator;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class EntityGenerator {
    
    public static void main(String[] args) {
        System.out.println("Starting entity generation from database...");
        
        try {
            // Tạo service registry
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            
            // Tạo metadata từ database
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .buildMetadata();
            
            System.out.println("Database metadata loaded successfully!");
            System.out.println("Found " + metadata.getEntityBindings().size() + " entities");
            
            // In ra thông tin về các entities
            metadata.getEntityBindings().forEach(entityBinding -> {
                System.out.println("Entity: " + entityBinding.getEntityName());
                System.out.println("Table: " + entityBinding.getTable().getName());
            });
            
            System.out.println("Entity generation completed!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
