package com.example.lottery;

public class Main {

    static private Company company;
    static private Player player;

    public static void main(String[] args) {

//Company creation
        company = new Company();
        company.setTicketPrice(10.0); //Ticket Price
        company.setCapital(10000.0); //Capital
        company.numberFill(7); //Win Number
        company.printTickets(10); //Tickets
        company.mainPrizeCalculation(); //Main Prize
/////////////////

//User creation
        player = new Player();
        System.out.println("Enter your name: ");
        player.create(Tech.GetInputStringFunction(), 100);
        userInterface();
        company.getPlayers().add(player);
/////////////////

//Bots and their tickets creation
        company.createPlayers(10); //Players creation

        company.getPlayers().forEach(Player::completeTickets);

        company.getPlayers()
                .forEach(n -> n.getTickets()
                        .forEach(ticket -> ticket.isLucky(company.getWinNumber())));

        company.getPlayers()
                .forEach(n -> n.getTickets()
                        .forEach(ticket -> ticket.calculateTicketPrize(company.getMainPrize(), company.countConquerors(ticket))));

        System.out.println(company.toString());
        System.out.println(player.toString());

//////////////////////////////////
        startLottery();

    }

    static void userInterface(){

        System.out.println("Hello, "+player.getName()+". You have $"+player.getMoney()+" on your balance.");
        System.out.println("Press Enter to buy lottery tickets.");
        Tech.GetInputStringFunction();

        if(player.getMoney()<company.getTicketPrice()){
            System.out.println("You lost all your money");
        }else{
            System.out.println("Ticket price is: $"+company.getTicketPrice()+". Enter the amount of tickets you wish to buy (up to 10)");

            int amount = Tech.GetInputFunction();
            if(amount*company.getTicketPrice()>player.getMoney()){
                System.out.println("You have no money for this amount");
                userInterface();
            }else{
                company.addTickets(amount, player);

                for(Ticket ticket: player.getTickets()){
                    System.out.println("Enter 7 digits in a column that will be a lottery combination for your #"+(player.getTickets().indexOf(ticket)+1)+" ticket");
                    for(int i=0; i<7; i++){
                        ticket.getNumber().add(Tech.GetInputFunction());
                    }
                }
            }
        }
    }

    public static void startLottery(){

    }


}
