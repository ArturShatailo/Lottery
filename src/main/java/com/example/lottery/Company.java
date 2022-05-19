package com.example.lottery;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Company implements fillNumber, buyTicket, getNames {

    private double capital;
    private final CopyOnWriteArrayList<Ticket> tickets = new CopyOnWriteArrayList<>();
    private ArrayList<Integer> winNumber = new ArrayList<>(7);
    private final ArrayList<Player> players = new ArrayList<>();
    private double mainPrize;
    private double ticketPrice;


    @Override
    public String toString(){
        return "capital: "+this.capital+", tickets: "+this.tickets+", winNumber: "+this.winNumber+", players: "+this.players+", mainPrize: "+this.mainPrize+", ticketPrice: "+ticketPrice;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public CopyOnWriteArrayList<Ticket> getTickets() {
        return tickets;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public ArrayList<Integer> getWinNumber() {
        return winNumber;
    }

    public double getMainPrize() {
        return mainPrize;
    }

    public void setMainPrize(double mainPrize) {
        this.mainPrize = mainPrize;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }


    ///////////////////

    //Creating tickets according to requested amount parameter
    public void printTickets(int amount) {
        for (int i = 0; i < amount; i++) {
            this.tickets.add(new Ticket());
        }
        this.tickets.forEach(n -> n.setPrice(this.getTicketPrice()));
    }

    //filling winNumber with 7 digits and sorting them by grow
    @Override
    public void numberFill(int times) {

        for (int i = 0; i < times; i++) {
            this.winNumber.add(Tech.getRandom(0, 9));
        }
        this.winNumber = (ArrayList<Integer>) this.winNumber.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
    }

    //Received Ticket will be removed from company's ArrayLList,
    // and it's price will be added to company's capital field
    @Override
    public void buyTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        this.capital += ticket.getPrice();
    }

    //Calculation of the main prise field
    public void mainPrizeCalculation() {
        this.setMainPrize((Math.pow((this.capital / this.tickets.size()), 1.5) * this.tickets.get(0).getPrice()));
    }

    //Find Players with the same amount of consequences.
    public int countConquerors(Ticket ticket) {

        return this.getPlayers().stream()
                .mapToInt(player -> player.getTickets()
                        .stream()
                        .filter(n -> n.getConsequences().size() == ticket.getConsequences().size())
                        .toList()
                        .size())
                .sum();
    }

    //Deduction from company's capital amount of funds that is a sum of all players' wins
    public void lotteryCalculation(){
        this.capital -= this.players.stream().mapToDouble(Player::calculateProfits).sum();

        System.out.println("\nCompany capital: $"+capital);
    }

    //Deleting all Players with funds less than 1 ticket price and all used in previous session tickets
    public void clearSession(){
        this.players.removeIf(player -> player.getMoney()<this.ticketPrice);
        this.players.forEach(Player::clearTickets);
        winNumber.clear();
    }



    /////?????????????????????
    //Creating players according to requested amount
    public void createPlayers(int amount) {

        for (int k = 0; k <= amount; k++) { //amount loop

            //check if there are available tickets in company
            if (!this.tickets.isEmpty()) {
                Player player = new Player(
                        names[Tech.getRandom(0, names.length - 1)],
                        (Tech.getRandom(1, 9)) * this.ticketPrice);

                //add random amount of tickets to created player
                this.addTickets(Tech.getRandom(1, (int) (player.getMoney() / this.ticketPrice)), player);

                //add new player to players collection
                this.players.add(player);
            } else {
                //Close creation in case of there are no more tickets in company's collection
                System.out.println("Sell session is over");
                break;
            }

        }
    }



    //Method adds requested amount of tickets to requested player and delete these tickets from company's collection
    public void addTickets(int amount, Player player) {

//        for (int i = 0; i < amount; i++) {
//            if (this.tickets.isEmpty()) {
//                break;
//            } else {
//                Collections.shuffle(this.tickets);
//                Ticket ticket = this.tickets.get(0);
//                player.buyTicket(ticket);
//                this.buyTicket(ticket);
//            }
//        }

        this.tickets.stream().limit(amount).forEachOrdered(n -> this.transferTicket(n, player));

    }

    public void transferTicket(Ticket ticket, Player player){
        if (!this.tickets.isEmpty()) {
            player.buyTicket(ticket);
            this.buyTicket(ticket);
        }
    }

}
