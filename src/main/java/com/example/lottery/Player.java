package com.example.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Player implements buyTicket{

        private String name;
        private ArrayList<Ticket> tickets = new ArrayList<>();
        private double money;

        public Player() {
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public ArrayList<Ticket> getTickets() {
                return tickets;
        }

        public void setTickets(ArrayList<Ticket> tickets) {
                this.tickets = tickets;
        }

        public double getMoney() {
                return money;
        }

        public void setMoney(double money) {
                this.money = money;
        }

        @Override
        public void buyTicket(Ticket ticket){
                this.tickets.add(ticket);
                this.money-=ticket.getPrice();
        }


        @Override
        public String toString(){
                return this.name+" "+this.money+" "+this.tickets.toString();
        }


        public void create(String name, double money){
                this.setName(name);
                this.setMoney(money);
        }

        public void completeTickets(){
                this.tickets.stream().filter(ticket -> ticket.getNumber().isEmpty()).forEach(n -> n.numberFill(7));
        }

}
