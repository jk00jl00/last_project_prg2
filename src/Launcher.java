public class Launcher {


    public static void main(String[] args) {
        /**
         * Switch for checking the provided arguments
         * No argument launches the client with the console interface and the address localhost and port 8080. When made it will open the graphical interface
         * With one argument it checks if the argument was server and if so launches the server with the address localhost and port 8080
         * With two arguments
         */

        switch(args.length){
            case 0:
                Client c = new Client("localhost", 8080, 0 );
                break;
            case 1:
                if(args[0].matches("server")){
                    Server s = new Server("localhost",8080);
                }
                break;
        }

    }
}
