package ue1104.iramps.be.Model.BL;

import java.util.ArrayList;
import java.util.HashMap;

public class JointureComposee<T1,T2> {
    private ArrayList<T1> firstJoin;
    private ArrayList<T2> secondJoin;
    private ArrayList<HashMap<String, String>> otherAttributes;

    public JointureComposee(){
        firstJoin = new ArrayList<T1>();
        secondJoin = new ArrayList<T2>();
        otherAttributes = new ArrayList<>();
    }


    public T1 getFirstJoinEntry(int number ) {
        return firstJoin.get(number);
    }

    public void addFirstJoin(T1 firstJoin) {
        this.firstJoin.add(firstJoin);
    }

    public void deleteFirstJoin(T1 firstJoin) {
        this.firstJoin.remove(firstJoin);
    }

    public void deleteFirstJoin(int firstJoinNumber) {
        this.firstJoin.remove(firstJoinNumber);
    }
    
    public T2 getSecondJoinEntry(int number ) {
        return secondJoin.get(number);
    }

    public void addSecondJoin(T2 secondJoin) {
        this.secondJoin.add(secondJoin);
    }

    public void deleteSecondJoin(T2 secondJoin) {
        this.secondJoin.remove(secondJoin);
    }

    public void deleteSecondJoin(int secondJoinNumber) {
        this.secondJoin.remove(secondJoinNumber);
    }  

    public HashMap<String,String> getOtherAttributeEntry(int number ) {
        return otherAttributes.get(number);
    }

    public void addotherAttributes(HashMap<String,String> otherAttributes) {
        this.otherAttributes.add(otherAttributes);
    }

    public void deleteotherAttributes(HashMap<String,String> otherAttributes) {
        this.otherAttributes.remove(otherAttributes);
    }

    public void deleteotherAttributes(int otherAttributesNumber) {
        this.otherAttributes.remove(otherAttributesNumber);
    }
    
}
