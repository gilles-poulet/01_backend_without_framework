package ue1104.iramps.be.Servlet;

import jakarta.servlet.http.*;
import ue1104.iramps.be.Model.IModel;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;

@WebServlet("/Status/*")
public class Status extends Main {
    
    /**
     * Effectue les actions pour l'API Get.
     * @param gson L'objet gson pour le formattage en JSON.
     * @param req L'objet HttpServletRequest pour pouvoir récupérer le chemin si nécessaire.
     * @param res L'objet HttpServletResponse pour pouvoir imprimer le résultat ou  l'erreur.
     * @param model L'objet IModel permettant de récupérer les données.
     * @throws IOException
     */
    protected void doAPIGet(HttpServletRequest req, HttpServletResponse res, Gson gson, IModel model) throws IOException{
        String json = "";
        ue1104.iramps.be.Model.BL.Status status;
        try {
            switch (this.validateStandardMatch(req.getPathInfo())) {   
                case GET_BY_ID:
                    status = model.getStatus(Integer.parseInt(req.getPathInfo().replace("/", "")));
                    if (status.getId() == -1) throw new Exception("Status non trouvé.") ;
                    json = gson.toJson(status);
                    break;

                case GET_BY_PARAM: 
                if (req.getParameter("nom") == null) {
                    json = gson.toJson(model.getAllStatus());
                } else {                    
                    status = model.getStatus(req.getParameter("nom"));
                    if (status.getId() == -1) throw new Exception("Status non trouvé.") ;
                    json = gson.toJson(status);  
                }               
                    break;

                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }
        this.printToPage(res, json);    
    }

    /**
     * Effectue les actions pour l'API Update.
     * @param gson L'objet gson pour le formattage en JSON.
     * @param jb L'objet StringBuffer contenant le document à analyser.
     * @param req L'objet HttpServletRequest pour pouvoir récupérer le chemin si nécessaire.
     * @param res L'objet HttpServletResponse pour pouvoir imprimer le résultat ou  l'erreur.
     * @param model L'objet IModel permettant de récupérer les données.
     * @throws IOException
     */
    protected void doAPIUpdate(StringBuffer jb, HttpServletRequest req, HttpServletResponse res, Gson gson, IModel model) throws IOException{
        String json = "";
        try {
            if (jb.toString() == "") throw new Exception("Aucun fichier reçu.");   
            switch (this.validateStandardMatch(req.getPathInfo())) {     
                case UPDATE:  ue1104.iramps.be.Model.BL.Status status;
                    status = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Status.class);
                    json=gson.toJson(model.updateStatus(String.valueOf(status.getId()),status.getNom()));           
                    break;    
                case UPDATE_MULTI:  ue1104.iramps.be.Model.BL.Status[] statusArray;
                    ArrayList<Boolean> returns = new ArrayList<Boolean>();
                    statusArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Status[].class);
                    for (ue1104.iramps.be.Model.BL.Status sta : statusArray){
                        returns.add(model.updateStatus(String.valueOf(sta.getId()),sta.getNom()));  
                    }      
                    json = gson.toJson(returns);       
                    break;      
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: PUT, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }   
        this.printToPage(res, json);     
    }

    /**
     * Effectue les actions pour l'API Create.
     * @param gson L'objet gson pour le formattage en JSON.
     * @param jb L'objet StringBuffer contenant le document à analyser.
     * @param req L'objet HttpServletRequest pour pouvoir récupérer le chemin si nécessaire.
     * @param res L'objet HttpServletResponse pour pouvoir imprimer le résultat ou  l'erreur.
     * @param model L'objet IModel permettant de récupérer les données.
     * @throws IOException
     */
    protected void doAPICreate(StringBuffer jb, HttpServletRequest req, HttpServletResponse res, Gson gson, IModel model) throws IOException{
        String json = "";
        try {
            if (jb.toString() == "") throw new Exception("Aucun fichier reçu.");            
            switch (this.validateStandardMatch(req.getPathInfo())) {    
                case CREATE:
                    if (jb.toString().startsWith("[")){
                        ue1104.iramps.be.Model.BL.Status[] statusArray;
                        ArrayList<Boolean> returns = new ArrayList<Boolean>();
                        statusArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Status[].class);
                        for (ue1104.iramps.be.Model.BL.Status status : statusArray){
                            returns.add(model.insertStatus(status.getNom()));
                        }
                        json = gson.toJson(returns);
                    } else {
                        ue1104.iramps.be.Model.BL.Status status;
                        status = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Status.class);
                        boolean ret = model.insertStatus(status.getNom());
                        if (ret){
                            json = gson.toJson(model.getStatus(status.getNom()));
                        } else {
                            json = gson.toJson(ret);
                        }   
                    }          
                    break;             
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }  
        this.printToPage(res, json);      
    }

    /**
     * Effectue les actions pour l'API Delete.
     * @param gson L'objet gson pour le formattage en JSON.
     * @param jb L'objet StringBuffer contenant le document à analyser.
     * @param req L'objet HttpServletRequest pour pouvoir récupérer le chemin si nécessaire.
     * @param res L'objet HttpServletResponse pour pouvoir imprimer le résultat ou  l'erreur.
     * @param model L'objet IModel permettant de récupérer les données.
     * @throws IOException
     */ 
    protected void doAPIdelete(StringBuffer jb, HttpServletRequest req, HttpServletResponse res, Gson gson, IModel model) throws IOException{
        String json = "";
        try {
            switch (this.validateStandardMatch(req.getPathInfo())) {   
                case DELETE_ONE:  json=gson.toJson(model.deleteStatus(req.getPathInfo().split("/")[1]));          
                    break;  
                
                case DELETE_MULTI:
                    // Formattage du document pour écriture en DB
                    ue1104.iramps.be.Model.BL.Status[] statusArray;
                    try {
                        statusArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Status[].class);
                        ArrayList<Boolean> returns = new ArrayList<Boolean>();
                        for (ue1104.iramps.be.Model.BL.Status status: statusArray){
                            returns.add(model.deleteStatus(String.valueOf(status.getId())));
                        }                        
                        json = gson.toJson(returns);
                    } catch (JsonSyntaxException e) {
                        json=gson.toJson(e);
                        this.printToPage(res, json);
                        return;
                    }
                        break;        
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }   
        this.printToPage(res, json);     
    }
}