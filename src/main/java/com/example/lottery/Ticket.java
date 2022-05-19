package com.example.lottery;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Ticket implements fillNumber{

    private double price;
    private ArrayList<Integer> number = new ArrayList<>(7);
    private final ArrayList<Integer> consequences = new ArrayList<>();
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

    public ArrayList<Integer> getConsequences() {
        return consequences;
    }

    public double getPrize() {
        return prize;
    }



//    @Override
//    public String toString(){
//        return "#"+ this.number.toString()+"("+this.consequences.size()+")"+" price: "+this.price+" prize: "+this.prize;
//    }


    //filling number of Ticket with 7 digits and sorting them by grow
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
        return "#"+ this.number.toString()+"("+this.consequences+")"+" prize: "+this.prize;
    }



    /////?????????????????????
    //The method checks if the Ticket is 'lucky' and its digits in number are equal to digits in WinNumber
    //taking into account an index of each digit. The result of search is filling out of consequences field of the Ticket
    public void isLucky(ArrayList<Integer> winNumber){

        for(int i=0, k=0; i<winNumber.size() && k<this.getNumber().size(); i++, k++){
            if(winNumber.get(i).equals(this.getNumber().get(k))){
                this.consequences.add(i);
            }
        }

    }

    //The method calculates amount of prize that is available with the Ticket
    public void calculateTicketPrize(double mainPrize, int conquerors){

        if(this.consequences.size()>2){
            this.prize = ((mainPrize * this.consequences.size() / this.number.size()) / conquerors);
        }else{
            this.prize = 0.0;
        }

    }

}
