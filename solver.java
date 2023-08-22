import java.util.*;
//Notes: Treeset is always sorted via natural order unless given an overriding comparator

public class solver {
    private static Boolean solve(TreeSet<Integer> inputArrNum, int goal){
        //first search for factors and dividends
        if(doMath(inputArrNum,goal)){
            return true;
        }
        //if no work, try to make factors or dividends

        //if all else fails, add and subtract to victory
        Integer intArr[] = new Integer[inputArrNum.size()];
        intArr = inputArrNum.toArray(intArr);
        addSub(intArr, goal);
        return false;
    }
    //this searches for factors or dividends and tries to make the respective factor/divisor
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
    //creates the other factor or dividend if found in doMath
    //part is the variable we are trying to make
    private static Boolean findPart2(TreeSet<Integer> inputArrNum, int part){
        if(inputArrNum.size()==1 && inputArrNum.first()==part){
            return true;
        }
        else if(inputArrNum.size()>1){
            Iterator<Integer> x = inputArrNum.iterator();
            while(x.hasNext()){
                    int i = x.next();
                    //find a factor or dividend of the factor/dividend
                    if((part % i == 0) || (i % part == 0)){
                        int factor = part / i;
                        int divisor  = i /part;
                    //create a treeset without the factor / dividend
                    TreeSet<Integer> snip = new TreeSet<Integer>();
                    snip = (TreeSet<Integer>)inputArrNum.clone();
                    snip.remove(i);
                    //if we can create the factor or dividend, then we try to make it with the rest of the numbers avaliable
                    if(findPart2(snip,factor)){
                        return true;
                    }
                    else if(findPart2(snip,divisor)){
                        return true;
                    }
                }
                //if we cant find a factor or dividend of the factor/dividend, we create it through addition or subtraction
                Integer intArr[] = new Integer[inputArrNum.size()];
                intArr = inputArrNum.toArray(intArr);
                addSub(intArr, part);
            }
        }
        return false;
    }
    //takes the numbers given in the treeset and then tries to create goal using addition and subtraction
    private static Boolean addSub(Integer[] inputArrNum, int goal){
        //if the first element is greater, we try to subtract downwards
        if(inputArrNum[0] > goal){
            //smallDist is supposed to represent the smallest distance possible from goal that can be made through subtraction
            int smallDist = Math.abs(goal - (Math.abs(inputArrNum[0]-inputArrNum[1])));
            //smallNumIndex is the index of the other value that yeilds the smallest difference with the goal
            int smallNumIndex = 1;
            for(int i =2; i<inputArrNum.length; i++){
                if( smallDist > ( Math.abs( goal - ( Math.abs(inputArrNum[0]-inputArrNum[i]) ) ) ) ){
                   smallNumIndex = i;
                   smallDist = ( Math.abs( goal - ( Math.abs(inputArrNum[0]-inputArrNum[i]) ) ) );
                }
            }
            //if here, we've found the value of the smallest distance and the index of the number that creates it
            
        }
        //if the first element is the goal and the array still has number to use, we try to make a 1 with the rest
        else if(inputArrNum[0] == goal){
            if(inputArrNum.length != 1){
                Integer[] smallerSnip = Arrays.copyOfRange(inputArrNum, 1, inputArrNum.length);
                addSub(smallerSnip, 1);
            }
            else{
                return true;
            }
        }
        //if the first element is less than the goal, we try to add upwards
        else{

        }
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
