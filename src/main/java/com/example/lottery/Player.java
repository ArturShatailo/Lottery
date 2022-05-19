package com.example.lottery;

import java.util.ArrayList;

public class Player implements buyTicket{

        private String name;
        private final ArrayList<Ticket> tickets = new ArrayList<>();
        private double money;

        public Player(String name, double money) {
                this.name = name;
                this.money = money;
        }
        public Player() {
        }

        public String getName() {
                return name;
        }

        public ArrayList<Ticket> getTickets() {
                return tickets;
        }

        public double getMoney() {
                return money;
        }


        ///////

        @Override
        public void buyTicket(Ticket ticket){
                this.tickets.add(ticket);
                this.money-=ticket.getPrice();
        }

        @Override
        public String toString(){
                return this.name+" "+this.money+" "+this.tickets;
        }

        public void completeTickets(){
                this.tickets.stream().filter(ticket -> ticket.getNumber().isEmpty()).forEach(n -> n.numberFill(7));
        }

        public void clearTickets(){
                this.tickets.clear();
        }

        public double calculateProfits(){
                double profit = this.tickets.stream().mapToDouble(Ticket::getPrize).sum();
                this.money+=profit;

                if(profit>0){
                        System.out.println(this.name+" earned: "+profit+" (balance: "+this.money+")"+" ("+this.getTickets().toString()+")");
                }

                return profit;
        }

}
