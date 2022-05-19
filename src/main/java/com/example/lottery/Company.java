package com.example.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Company implements fillNumber, buyTicket, getNames {

    private double capital;
    private final ArrayList<Ticket> tickets = new ArrayList<>();
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

    public ArrayList<Ticket> getTickets() {
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


    public void printTickets(int amount) {
        for (int i = 0; i < amount; i++) {
            this.tickets.add(new Ticket());
        }
        this.tickets.forEach(n -> n.setPrice(this.getTicketPrice()));
    }

    @Override
    public void numberFill(int times) {
        for (int i = 0; i < times; i++) {
            this.winNumber.add(Tech.getRandom(0, 9));
        }
        this.winNumber = (ArrayList<Integer>) this.winNumber.stream()
                .sorted(Integer::compareTo)
                .collect(Collectors.toList());
    }

    @Override
    public void buyTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        this.capital += ticket.getPrice();
    }

    public void mainPrizeCalculation() {
        this.setMainPrize((Math.pow((this.capital / this.tickets.size()), 1.4) * this.tickets.get(0).getPrice()));
    }

    public int countConquerors(Ticket ticket) {

        return this.getPlayers().stream()
                .mapToInt(player -> player.getTickets()
                        .stream()
                        .filter(n -> n.getConsequences().size() == ticket.getConsequences().size())
                        .toList()
                        .size())
                .sum();
    }

    public void lotteryCalculation(){
        this.capital -= this.players.stream().mapToDouble(Player::calculateProfits).sum();

        System.out.println("\nCompany capital: $"+capital);

    }

    public void clearSession(){
        this.players.removeIf(player -> player.getMoney()<this.ticketPrice);
        this.players.forEach(Player::clearTickets);
        winNumber.clear();
    }

    /////?????????????????????
    public void createPlayers(int amount) {

        for (int k = 0; k <= amount; k++) {
            if (!this.tickets.isEmpty()) {

                Player player = new Player(
                        names[Tech.getRandom(0, names.length - 1)],
                        (Tech.getRandom(1, 9)) * this.ticketPrice);

                this.addTickets(Tech.getRandom(1, (int) (player.getMoney() / this.ticketPrice)), player);

                this.players.add(player);
            } else {
                System.out.println("Sell session is over");
                break;
            }
        }
    }
    public void addTickets(int amount, Player player) {
        for (int i = 0; i < amount; i++) {
            if (this.tickets.isEmpty()) {
                break;
            } else {
                Collections.shuffle(this.tickets);
                Ticket ticket = this.tickets.get(0);
                player.buyTicket(ticket);
                this.buyTicket(ticket);
            }
        }
    }
}
