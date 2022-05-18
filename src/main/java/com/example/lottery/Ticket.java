package com.example.lottery;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Ticket implements fillNumber{

    private double price;
    private ArrayList<Integer> number = new ArrayList<>(7);
    private ArrayList<Integer> consequences = new ArrayList<>();;
    private double prize;

    public Ticket() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Integer> getNumber() {
        return number;
    }

    public void setNumber(ArrayList<Integer> number) {
        this.number = number;
    }

    public ArrayList<Integer> getConsequences() {
        return consequences;
    }

    public void setConsequences(ArrayList<Integer> consequences) {
        this.consequences = consequences;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

//    public Ticket ticketNumber(){
//
//        this.numberFill(7);
//
//        return this;
//    }

    @Override
    public void numberFill(int times){
        for(int i=0; i<times; i++){
            this.number.add(Tech.getRandom(0,9));
        }
        this.number = (ArrayList<Integer>) this.number.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public String toString(){
        return "#"+ this.number.toString()+"("+this.consequences.size()+")"+" price: "+this.price+" prize: "+this.prize;
    }



    /////?????????????????????
    public void isLucky(ArrayList<Integer> winNumber){

        for(int i=0, k=0; i<winNumber.size() && k<this.getNumber().size(); i++, k++){
            if(winNumber.get(i).equals(this.getNumber().get(k))){
                this.consequences.add(i);
            }
        }
    }

    public void calculateTicketPrize(double mainPrize, int conquerors){

        if(this.consequences.size()>2){
            this.prize = (mainPrize * this.consequences.size() / this.number.size()) / conquerors;
        }else{
            this.prize = 0.0;
        }

    }

}
