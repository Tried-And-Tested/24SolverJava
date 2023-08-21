import java.util.*;

public class solver {
    private static Boolean solve(TreeSet<Integer> inputArrNum, int goal){
        //first search for factors and dividends
        if(doMath(inputArrNum,goal)){
            return true;
        }
        //if no work, try to make factors or dividends

        //if all else fails, add and subtract to victory
        addSub(inputArrNum, goal);
        return false;
    }
    
    private static Boolean doMath(TreeSet<Integer> inputArrNum, int goal){
        Iterator<Integer> value= inputArrNum.iterator();
            while(value.hasNext()){
                int x = value.next();
                if((goal % x == 0) || (x % goal == 0)){
                    int factor = goal / x;
                    int divisor  = x /goal;
                //create a treeset without the factor / dividend
                TreeSet<Integer> snip = new TreeSet<Integer>();
                snip = (TreeSet<Integer>)inputArrNum.clone();
                snip.remove(x);

                if(findPart2(snip,factor)){
                    return true;
                }
                if(findPart2(snip,divisor)){
                    return true;
                }
            }
        }
        return false;
    }
    
    private static Boolean findPart2(TreeSet<Integer> snip, int part){
        if(snip.size()==1 && snip.first()==part){
            return true;
        }
        else if(snip.size()>1){
            Iterator<Integer> x = snip.iterator();
            while(x.hasNext()){
                
            }
        }
        return false;
    }
    
    private static Boolean addSub(TreeSet<Integer> inputArrNum, int goal){
        return false;
    }

    private static Boolean isValid(String check){
        //if there is a single non digit character, parseInt returns an exception
        try{
            check = check.replaceAll("\\s+","");
            int filter = Integer.parseInt(check);
            return true;
        }
        catch(Exception e){
            System.out.println("Error: 1 or more characters are not letters or whitespace");
            return false;
        }
    }
    
    public static void main(String[] args){
        while(true){

            Scanner scan  = new Scanner(System.in);
            System.out.println("Please input 4 numbers deliminated by spaces or q to quit");
                String input = scan.nextLine();

                if(isValid(input) && input.length() ==4){
                    //create an array that consists only of non whitespace characters
                    String[] inputArr = input.split("\\s+", 0);
                    TreeSet<Integer> inputArrNum = new TreeSet<Integer>();

                    for(String entry : inputArr){
                        inputArrNum.add(Integer.parseInt(entry));
                    }
                    //if both tests fail, we declare no solution
                    if(!solve(inputArrNum,4) && !solve(inputArrNum,24)){
                        System.out.println("No solution found");
                    }
                }

                else if(input.replaceAll("\\s+","").equals("q")){
                    scan.close();
                    System.exit(0);
                }

                else{
                }
        }
    }
}
