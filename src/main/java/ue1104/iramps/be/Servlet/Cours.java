package ue1104.iramps.be.Servlet;

import jakarta.servlet.http.*;
import ue1104.iramps.be.Model.IModel;
import ue1104.iramps.be.Model.BL.JointureComposee;
import jakarta.servlet.annotation.WebServlet;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@WebServlet("/Cours/*")
public class Cours extends Main {
    
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
        ue1104.iramps.be.Model.BL.Cours cours;
        JointureComposee<ue1104.iramps.be.Model.BL.Personne, ue1104.iramps.be.Model.BL.Cours> jointure;
        try {
            switch (this.validateStandardMatch(req.getPathInfo())) {   
                case GET_BY_ID:
                    cours = model.getCours(Integer.parseInt(req.getPathInfo().replace("/", "")));
                    if (cours.getId() == -1) throw new Exception("Cours non trouvé.") ;
                    json = gson.toJson(cours);
                    break;

                case GET_BY_PARAM: 
                if (req.getParameter("nom") == null) {
                    json = gson.toJson(model.getAllCours());
                } else {                    
                    cours = model.getCours(req.getParameter("nom"));
                    if (cours.getId() == -1) throw new Exception("Cours non trouvé.") ;
                    json = gson.toJson(cours);  
                }               
                    break;

                case GET_PERSONNE_COURS_MULTI:
                    jointure = model.getAllCoursPersonne();
                    json = gson.toJson(jointure);
                    break;

                case GET_PERSONNE_COURS_ONE:
                        jointure = model.getOneCoursPersonne(Integer.parseInt(req.getPathInfo().split("/")[1]));
                        json = gson.toJson(jointure);
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
        JointureComposee<ue1104.iramps.be.Model.BL.Personne, ue1104.iramps.be.Model.BL.Cours> jointure = new JointureComposee<>();
        try {
            if (jb.toString() == "") throw new Exception("Aucun fichier reçu.");   
            switch (this.validateStandardMatch(req.getPathInfo())) {     
                case UPDATE:  ue1104.iramps.be.Model.BL.Cours cours;
                    cours = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours.class);
                    json=gson.toJson(model.updateCours(String.valueOf(cours.getId()),cours.getNom()));           
                    break;    
                case UPDATE_MULTI:  ue1104.iramps.be.Model.BL.Cours[] coursArray;
                    ArrayList<Boolean> returns = new ArrayList<Boolean>();
                    coursArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours[].class);
                    for (ue1104.iramps.be.Model.BL.Cours sta : coursArray){
                        returns.add(model.updateCours(String.valueOf(sta.getId()),sta.getNom()));  
                    }      
                    json = gson.toJson(returns);       
                    break;  
                    
                case UPDATE_SECTION_COURS:
                    ue1104.iramps.be.Model.BL.Cours cours_section;
                    cours_section = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours.class);
                    json=gson.toJson(model.updateSectionCours(cours_section.geSectionID(), cours_section.getId()));           
                    break;

                case UPDATE_PERSONNE_COURS:
                    jointure.addFirstJoin(model.getPersonne(Integer.parseInt(req.getPathInfo().split("/")[4])));
                    if (jointure.getFirstJoinEntry(0).getId() == -1) throw new Exception("Personne non trouvé.");

                    jointure.addSecondJoin(model.getCours(Integer.parseInt(req.getPathInfo().split("/")[1])));
                    if (jointure.getSecondJoinEntry(0).getId() == -1) throw new Exception("Cours non trouvé.");

                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    jointure.addotherAttributes(gson.fromJson(jb.toString(),type));
                    if (jointure.getOtherAttributeEntry(0).containsKey("date_debut") &&
                        jointure.getOtherAttributeEntry(0).containsKey("date_fin") &&
                        jointure.getOtherAttributeEntry(0).containsKey("new_date_debut") &&
                        jointure.getOtherAttributeEntry(0).containsKey("new_date_fin") && 
                        jointure.getOtherAttributeEntry(0).containsKey("new_id_personne") && 
                        jointure.getOtherAttributeEntry(0).containsKey("new_id_cours")
                        ){
                            json = gson.toJson(model.updatePersonneCours(jointure.getFirstJoinEntry(0).getId(),
                                                                        jointure.getSecondJoinEntry(0).getId(), 
                                                                        jointure.getOtherAttributeEntry(0).get("date_debut"),
                                                                        jointure.getOtherAttributeEntry(0).get("date_fin"),
                                                                        Integer.parseInt(jointure.getOtherAttributeEntry(0).get("new_id_personne")),
                                                                        Integer.parseInt(jointure.getOtherAttributeEntry(0).get("new_id_cours")),
                                                                        jointure.getOtherAttributeEntry(0).get("new_date_debut"),
                                                                        jointure.getOtherAttributeEntry(0).get("new_date_fin")                                                                      
                                                                        
                                                                        ));
                        }
                    else {
                        throw new Exception("Dates de début et de fin nécessaires, ainsi que les nouvelles valeurs sous format new_date_debut, new_date_fin, new_id_personne, new_id_cours");
                    }                
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
        JointureComposee<ue1104.iramps.be.Model.BL.Personne, ue1104.iramps.be.Model.BL.Cours> jointure = new JointureComposee<>();
        try {
            if (jb.toString() == "") throw new Exception("Aucun fichier reçu.");            
            switch (this.validateStandardMatch(req.getPathInfo())) {    
                case CREATE:
                    if (jb.toString().startsWith("[")){
                        ue1104.iramps.be.Model.BL.Cours[] coursArray;
                        ArrayList<Boolean> returns = new ArrayList<Boolean>();
                        coursArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours[].class);
                        for (ue1104.iramps.be.Model.BL.Cours cours : coursArray){
                            returns.add(model.insertCours(cours.getNom()));
                        }
                        json = gson.toJson(returns);
                    } else {
                        ue1104.iramps.be.Model.BL.Cours cours;
                        cours = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours.class);
                        boolean ret = model.insertCours(cours.getNom());
                        if (ret){
                            json = gson.toJson(model.getCours(cours.getNom()));
                        } else {
                            json = gson.toJson(ret);
                        }   
                    }          
                    break; 
                case CREATE_PERSONNE_COURS:
                    jointure.addFirstJoin(model.getPersonne(Integer.parseInt(req.getPathInfo().split("/")[3])));
                    if (jointure.getFirstJoinEntry(0).getId() == -1) throw new Exception("Personne non trouvé.");

                    jointure.addSecondJoin(model.getCours(Integer.parseInt(req.getPathInfo().split("/")[1])));
                    if (jointure.getSecondJoinEntry(0).getId() == -1) throw new Exception("Cours non trouvé.");

                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    jointure.addotherAttributes(gson.fromJson(jb.toString(),type));
                    if (jointure.getOtherAttributeEntry(0).containsKey("date_debut") &&
                        jointure.getOtherAttributeEntry(0).containsKey("date_fin")){
                            json = gson.toJson(model.insertPersonneCours(jointure.getFirstJoinEntry(0).getId(),
                                                                         jointure.getSecondJoinEntry(0).getId(), 
                                                                         jointure.getOtherAttributeEntry(0).get("date_debut"),
                                                                         jointure.getOtherAttributeEntry(0).get("date_fin")));
                        }
                    else {
                        throw new Exception("Dates de début et de fin nécessaires");
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
        JointureComposee<ue1104.iramps.be.Model.BL.Personne, ue1104.iramps.be.Model.BL.Cours> jointure = new JointureComposee<>();
        try {
            switch (this.validateStandardMatch(req.getPathInfo())) {   
                case DELETE_ONE:  json=gson.toJson(model.deleteCours(req.getPathInfo().split("/")[1]));          
                    break;  
                
                case DELETE_MULTI:
                    // Formattage du document pour écriture en DB
                    ue1104.iramps.be.Model.BL.Cours[] coursArray;
                    try {
                        coursArray = gson.fromJson(jb.toString(), ue1104.iramps.be.Model.BL.Cours[].class);
                        ArrayList<Boolean> returns = new ArrayList<Boolean>();
                        for (ue1104.iramps.be.Model.BL.Cours cours: coursArray){
                            returns.add(model.deleteCours(String.valueOf(cours.getId())));
                        }                        
                        json = gson.toJson(returns);
                    } catch (JsonSyntaxException e) {
                        json=gson.toJson(e);
                        this.printToPage(res, json);
                        return;
                    }
                        break;  
                case DELETE_PERSONNE_COURS:
                    jointure.addFirstJoin(model.getPersonne(Integer.parseInt(req.getPathInfo().split("/")[4])));
                    if (jointure.getFirstJoinEntry(0).getId() == -1) throw new Exception("Personne non trouvé.");

                    jointure.addSecondJoin(model.getCours(Integer.parseInt(req.getPathInfo().split("/")[1])));
                    if (jointure.getSecondJoinEntry(0).getId() == -1) throw new Exception("Cours non trouvé.");

                    Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                    jointure.addotherAttributes(gson.fromJson(jb.toString(),type));
                    if (jointure.getOtherAttributeEntry(0).containsKey("date_debut") &&
                        jointure.getOtherAttributeEntry(0).containsKey("date_fin")){
                            json = gson.toJson(model.deletePersonneCours(jointure.getFirstJoinEntry(0).getId(),
                                                                        jointure.getSecondJoinEntry(0).getId(), 
                                                                        jointure.getOtherAttributeEntry(0).get("date_debut"),
                                                                        jointure.getOtherAttributeEntry(0).get("date_fin")));
                        }
                    else {
                        throw new Exception("Dates de début et de fin nécessaires");
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
     * Valide le type de d'opération API demandé.
     * @param valeur La valeur à vérifier.
     * @return Un objet de type EnumAPIOperation, indiquant le type d'opération API demandé.
     * @throws Exception
     */
    protected EnumAPIOperation validateStandardMatch(String valeur) throws Exception{
        EnumAPIOperation operation;
        if (valeur == null){
            valeur = "/";
        }
        if (validateMatch("^/[0-9]+/section$", valeur)) return EnumAPIOperation.UPDATE_SECTION_COURS;
        if (validateMatch("^/personne$", valeur)) return EnumAPIOperation.GET_PERSONNE_COURS_MULTI;
        if (validateMatch("^/[0-9]+/personne/?$", valeur)) return EnumAPIOperation.GET_PERSONNE_COURS_ONE;
        if (validateMatch("^/[0-9]+/personne/[0-9]+/?$", valeur)) return EnumAPIOperation.CREATE_PERSONNE_COURS;
        if (validateMatch("^/[0-9]+/update/personne/[0-9]+/?$", valeur)) return EnumAPIOperation.UPDATE_PERSONNE_COURS;
        if (validateMatch("^/[0-9]+/delete/personne/[0-9]+/?$", valeur)) return EnumAPIOperation.DELETE_PERSONNE_COURS;
        operation = super.validateStandardMatch(valeur);
        return operation;
    }
}