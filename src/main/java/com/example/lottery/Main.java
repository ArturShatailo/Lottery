package com.example.lottery;

public class Main {

    static private Company company;
    static private Player player;

    public static void main(String[] args) {

        //User creation
        System.out.println("Enter your name: ");
        player = new Player(Tech.GetInputStringFunction(), 100);

        //Company creation
        company = new Company();
        company.setTicketPrice(10.0); //Ticket Price
        company.setCapital(10000.0); //Capital


        company.getPlayers().add(player);

        startLottery();
    }

    //User can buy tickets according to the amount of money
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
                //call method for adding bought tickets to players field "tickets"
                company.addTickets(amount, player);

                //Filling each ticket with numbers (7) that will be a lottery combination
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

        //Completing company with several fields
        company.numberFill(7); //Win Number
        company.printTickets((int) ((company.getCapital() / 2) / company.getTicketPrice())); //Tickets
        company.mainPrizeCalculation(); //Main Prize

        //user interface method call to complete user's (Player) data filling
        userInterface();

        //Creating bots (another lottery players)
        fillBots();

        //Print some information about the current lottery session
        System.out.println("The main prise for 7 lucky digits is: $"+company.getMainPrize());
        System.out.println("Players amount: "+company.getPlayers().size());
        System.out.println("Lucky number is : "+company.getWinNumber());

        //Lottery (profit, wins) calculation
        company.lotteryCalculation();

        System.out.println(player.toString());

        //New lottery session request
        newLotteryLoop();

    }

    public static void fillBots(){

        //Bots and their tickets creation
        company.createPlayers((company.getTickets().size()-company.getPlayers().size())); //Players creation

        company.getPlayers().forEach(Player::completeTickets); // fill number of each players' ticket

        company.getPlayers() // fill consequences of each players' ticket
                .forEach(n -> n.getTickets()
                        .forEach(ticket -> ticket.isLucky(company.getWinNumber())));

        company.getPlayers() // fill amount of win prise of each players' ticket
                .forEach(n -> n.getTickets()
                        .forEach(ticket -> ticket.calculateTicketPrize(company.getMainPrize(), company.countConquerors(ticket))));
    }

    public static void newLotteryLoop(){
        System.out.println("""
                Press:
                1) to start lottery again
                2) to Exit""");
        if(Tech.GetInputFunction()==1){

            //clear the current session from players with no money and used tickets and lottery new session start
            company.clearSession();
            startLottery();
        }else{
            System.out.println("You should be lucky next time. Bye");
        }
    }

}
