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

        //In this method Player spends money for Ticket according to its field price and then this Ticket will be added to
        //Players collection
        @Override
        public void buyTicket(Ticket ticket){
                this.tickets.add(ticket);
                this.money-=ticket.getPrice();
        }

        @Override
        public String toString(){
                return this.name+" "+this.money+" "+this.tickets;
        }

        //Fill out numbers in tickets that has no filled number (Bots)
        public void completeTickets(){
                this.tickets.stream().filter(ticket -> ticket.getNumber().isEmpty()).forEach(n -> n.numberFill(7));
        }

        //Clear collection of Tickets
        public void clearTickets(){
                this.tickets.clear();
        }

        //Sum all profits from all tickets that won. The mutual profit is adding to Player's money field. Results are printing.
        public double calculateProfits(){
                double profit = this.tickets.stream().mapToDouble(Ticket::getPrize).sum();
                this.money+=profit;

                if(profit>0){
                        System.out.println(this.name+" earned: "+profit+" (balance: "+this.money+")"+" ("+this.getTickets().toString()+")");
                }

                return profit;
        }

}
