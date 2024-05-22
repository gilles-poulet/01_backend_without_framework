package ue1104.iramps.be;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.io.*;
import ue1104.iramps.be.Model.IModel;
import ue1104.iramps.be.Model.PrimaryModel;

@WebListener
public class ContextCleaner implements ServletContextListener {
    private static IModel model;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ContextCleaner.model.close();
        System.out.println("Arrêt de l'application demo_api");
        ServletContextListener.super.contextDestroyed(sce);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploiement de l'application demo_api");
         try {
            ContextCleaner.model = PrimaryModel.getInstance();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ServletContextListener.super.contextInitialized(sce);
    }
    
}
