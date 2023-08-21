import java.util.Scanner;

public class solver {
    private static Boolean solve(){
        return false;
    }
    
    private static Boolean doMath(){
        return false;
    }
    
    private static Boolean findPart2(){
        return false;
    }
    
    private static Boolean addSub(){
        return false;
    }

    private static Boolean isValid(String[] check){
        return false;
    }
    
    public static void main(String[] args){
        Boolean execute = true;
        while(execute){
            Scanner scan  = new Scanner(System.in);
            System.out.println("Please input 4 numbers deliminated by spaces or q to quit");
                String input = scan.nextLine();
                System.out.println(input);
                String[] inputArr = input.split(" ", 0);
                if(isValid(inputArr) && inputArr.length ==4){

                }
                else if(input.replaceAll("\\s","").equals("q")){
                    scan.close();
                    System.exit(0);
                }
                else{
                }
        }
    }
}
