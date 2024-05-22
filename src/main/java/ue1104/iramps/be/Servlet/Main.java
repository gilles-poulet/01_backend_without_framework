package ue1104.iramps.be.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ue1104.iramps.be.Model.IModel;
import ue1104.iramps.be.Model.PrimaryModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Main extends HttpServlet{

    /**
     * Récupère l'implémentataion du modèle ou null.
     * @param gson L'objet gson pour le formattage en JSON
     * @param res L'objet HttpServletResponse pour pouvoir imprimer l'erreur.
     * @return Un objet de type IModel
     * @throws IOException
     */
    protected IModel getModel(Gson gson, HttpServletResponse res) throws IOException{
        IModel model = null;
        try {
            model = PrimaryModel.getInstance();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            String json = gson.toJson(e);
            e.printStackTrace();
            this.printToPage(res,json);
            return null;
        }
        return model;
    }

    /**
     *  Imprime une chaine de caractère déjà formattée en JSON.
     * @param res L'objet HttpServletResponse qui servira pour l'envoi au client.
     * @param json La chaine de caractère déjà formattée en JSON.
     * @throws IOException
     */
    protected void printToPage(HttpServletResponse res, String json) throws IOException{
        res.setContentType("application/json");
        PrintWriter pw = res.getWriter();
        if ( ! (json.startsWith("{") || json.startsWith("["))){
            json = "{\"message\":"+ json +"}";
        }
        pw.print(json);
        pw.flush();
        pw.close();
    }

    /**
     * Analyse le document reçu et renvoie une chaine de caractère le représentant.
     * @param gson L'objet gson pour le formattage en JSON.
     * @param req L'objet HttpServletRequest pour pouvoir récupérer le contenu du document.
     * @param res L'objet HttpServletResponse pour pouvoir imprimer l'erreur.
     * @return Un objet de type StringBuffer contenant le document.
     * @throws IOException
     */
    protected StringBuffer getDocBuffer(Gson gson, HttpServletRequest req, HttpServletResponse res) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) { 
            e.printStackTrace();;
            String json=gson.toJson(e);
            this.printToPage(res, json);
            return null;
        }
        return jb;
    }

    /**
     * Valide ou non une valeur par rapport à un pattern.
     * @param sPattern Le pattern qui sert à vérifier la valeur.
     * @param valeur La valeur à vérifier.
     * @return Un boolean indiquant si le pattern existe dans la valeur.
     */
    protected boolean validateMatch(String sPattern, String valeur){
        Pattern pattern = Pattern.compile(sPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(valeur);
        boolean matchFound = matcher.find();
        return matchFound;
    }

    /**
     * Valide le type de d'opération API demandé.
     * @param valeur La valeur à vérifier.
     * @return Un objet de type EnumAPIOperation, indiquant le type d'opération API demandé.
     * @throws Exception
     */
    protected EnumAPIOperation validateStandardMatch(String valeur) throws Exception{
        if (valeur == null){
            valeur = "/";
        }
        if (validateMatch("^/$", valeur)) return EnumAPIOperation.GET_BY_PARAM;
        if (validateMatch("^/[0-9]+$", valeur)) return EnumAPIOperation.GET_BY_ID;
        if (validateMatch("^/create$", valeur)) return EnumAPIOperation.CREATE;
        if (validateMatch("^/[0-9]+/update$", valeur)) return EnumAPIOperation.UPDATE;
        if (validateMatch("^/update$", valeur)) return EnumAPIOperation.UPDATE_MULTI;
        if (validateMatch("^/[0-9]+/delete$", valeur)) return EnumAPIOperation.DELETE_ONE;
        if (validateMatch("^/delete$", valeur)) return EnumAPIOperation.DELETE_MULTI;
        throw new Exception("API non valide.");
    }

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
        try {
            switch (this.validateStandardMatch(req.getPathInfo())) {           
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }    
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
            switch (this.validateStandardMatch(req.getPathInfo())) {           
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }    
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
            switch (this.validateStandardMatch(req.getPathInfo())) {           
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }    
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
                default:
                    throw new Exception("La méthode ne permet pas de réaliser l'opération demandée. Méhtode: POST, opération: " + req.getPathInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            json=gson.toJson(e);
            this.printToPage(res, json);
            return;
        }    
    }

    /**
     * Implémentation de la méthode POST.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        Gson gson = new Gson();
        IModel model = this.getModel(gson, res);
        if ( model == null ){
            return;
        }      
        
        // Lecture du document renvoyé
        StringBuffer jb = this.getDocBuffer(gson, req, res);
        if (jb == null) return;

        this.doAPICreate(jb, req, res, gson, model);   
    }

    /**
     * Implémentation de la méthode PUT
     */
    public void doPut(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        Gson gson = new Gson();
        IModel model = this.getModel(gson, res);
        if ( model == null ){
            return;
        }      
        
        // Lecture du document renvoyé
        StringBuffer jb = this.getDocBuffer(gson, req, res);
        if (jb == null) return;

        this.doAPIUpdate(jb, req, res, gson, model);   
    }
    

    /**
     * Implémentation de méthode DELETE
     */
    public void doDelete(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        Gson gson = new Gson();
        IModel model = this.getModel(gson, res);
        if ( model == null ){
            return;
        }      
        
        // Lecture du document renvoyé
        StringBuffer jb = this.getDocBuffer(gson, req, res);
        if (jb == null) return;

        this.doAPIdelete(jb, req, res, gson, model);   
    }

    /**
     * Implémentation de la méthode GET
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        Gson gson = new Gson();
        IModel model = this.getModel(gson, res);
        if ( model == null ){
        return;
        }      
        this.doAPIGet(req, res, gson, model); 
    }
}
