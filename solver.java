import java.util.*;
//Notes: Treeset is always sorted via natural order unless given an overriding comparator

//Notes as of 8/22/23 8:55pm: solve should take an array to make the 2nd condition easier and doMath might be able to
//use a hashset to make remove faster. findPart2 might not need a treeset

//Notes as of 8/23/23 2:38pm: Can not use a treeset because we have to account for duplicates

//Notes as of 8/23/23 2:53pm: findPart2 addSub contingency uses an array of size 1 with goal 0 when duplicates are present
//since it constantly finds factors and removes them

//Notes as of 8/23/23 3:03pm: the 1st and 2nd ring of doMath assumes that every number is different. If all numbers are
//duplicates, then findPart2 infinitly loops

//Notes as of 8/23/23 7:52pm: when we eventually print out the solution, in the case of subtraction, we need to
//keep track of which number is being subtracted from which

//Notes as of 8/24/23 12:03am: doMath still needs its division part written and tested

//Notes as of 8/24/23 10:24am: doMath division still needs its addSub input to be finished and then test everything

//Notes as of 8/24/23 3:30pm: still need to write in a 4 * 3! condition. Idea: if goal = 24, if there is a 4, try searching
//for a 3

//Notes as of 8/24/23 7:31pm: doMath's purpose should now create factors through searching or adding

//Notes as of 8/24/23 9:47pm: doMath's algorithm should be similar to how addSub works, where 1 half is generated via 2
//for loops and the other half is generated based on that

//Notes as of 8/25/23 3:17pm: the 2nd part of doMath still needs to be done

public class solver {
    private static Boolean solve(Integer[] userInputChoice, int goal){
        ArrayList<Integer> userInputChoiceList = new ArrayList<Integer>(Arrays.asList(userInputChoice));
        //ensure that no matter how we enter in the 4 numbers, all the methods will recieve a constant
        Collections.sort(userInputChoiceList);
        if(doMath(userInputChoiceList, goal)){
            return true;
        }
        else if(addSub(userInputChoiceList, goal, goal)){
            return true;
        }
        return false;
    }
    
    private static Boolean doMathHelper(ArrayList<Integer> inputNumArr, int goal, HashSet<Integer> factorList){
        for(int i=0;i<inputNumArr.size();i++){
            //if i alone is a factor
            if(factorList.contains(inputNumArr.get(i))){
                //if i is a factor, there are 2 cases:
                //1) there is a factor of the other factor left in inputNumArr
                //2) the other factor needs to be made
                for(int o=0;o<inputNumArr.size();o++){
                    //if the number at o is a factor of the other factor
                    if ( (goal / inputNumArr.get(i) ) % inputNumArr.get(o) == 0){
                        ArrayList<Integer> inputNumArrShort = new ArrayList<>(inputNumArr);
                        inputNumArrShort.remove(i);
                        inputNumArrShort.remove(o);
                        //the value that is needed to create the other factor for the factor
                        int otherNum = (goal / inputNumArr.get(i) ) / inputNumArr.get(o);
                        //if we can just add/sub to the otherNum
                        if( addSub(inputNumArrShort, otherNum, otherNum) ){
                            //this is the ret statement for this case
                            return true;
                        }
                        //else try to see if we can multiply /  divide to the otherNum
                        String ans = createFactorOrDivisor(i, o, otherNum);
                        if(ans != "F"){
                            //if we can, we return
                            return true;
                        }
                    }
                }
                //see if we can make the other factor via addition or subtraction
                ArrayList<Integer> inputNumArrShort = new ArrayList<>(inputNumArr);
                inputNumArrShort.remove(i);
                if(addSub(inputNumArrShort, (goal / inputNumArr.get(i) ), (goal / inputNumArr.get(i) ) ) ){
                    return true;
                }
            }
            else{
                //else if o alone is not a factor  
                for(int o=0;o<inputNumArr.size();o++){
                    //make sure the indexes are not the same  
                    if(i != o){
                        ArrayList<Integer> inputNumArrShort = new ArrayList<>(inputNumArr);
                        inputNumArrShort.remove(i);
                        inputNumArrShort.remove(o);
                        //if o + i is a factor
                        if(factorList.contains(inputNumArr.get(o) + inputNumArr.get(i) ) ){
                            int p1 = inputNumArr.get(o) + inputNumArr.get(i);
                            int remainderAdd = inputNumArrShort.get(1) + inputNumArrShort.get(0);
                            int remainderSub = Math.abs(inputNumArrShort.get(1) - inputNumArrShort.get(0));

                            if(createFactorOrDivisor(p1, remainderAdd, goal) != "F"){
                                return true;
                            }
                            else if(createFactorOrDivisor(p1, remainderSub, goal) != "F"){
                                return true;
                            }
                        }
                        //if o - i is a factor
                        else if(factorList.contains(inputNumArr.get(o) - inputNumArr.get(i) ) ){
                            int p1 = inputNumArr.get(o) - inputNumArr.get(i);
                            int remainderAdd = inputNumArrShort.get(1) + inputNumArrShort.get(0);
                            int remainderSub = Math.abs(inputNumArrShort.get(1) - inputNumArrShort.get(0));

                            if(createFactorOrDivisor(p1, remainderAdd, goal) != "F"){
                                return true;
                            }
                            else if(createFactorOrDivisor(p1, remainderSub, goal) != "F"){
                                return true;
                            }
                        }
                        //if i - o is a factor
                        else if(factorList.contains(inputNumArr.get(i) - inputNumArr.get(o) ) ){
                            int p1 = inputNumArr.get(i) - inputNumArr.get(o);
                            int remainderAdd = inputNumArrShort.get(1) + inputNumArrShort.get(0);
                            int remainderSub = Math.abs(inputNumArrShort.get(1) - inputNumArrShort.get(0));

                            if(createFactorOrDivisor(p1, remainderAdd, goal) != "F"){
                                return true;
                            }
                            else if(createFactorOrDivisor(p1, remainderSub, goal) != "F"){
                                return true;
                            }
                        }
                    }
                }
            }    
        }
        return false;
    }
    private static Boolean doMath(ArrayList<Integer> inputNumArr, int goal){
        //The point of doMath is to search for factors or dividends. If a factor or dividend is found,
        //then we find its factor or dividend until we can no longer. If we can no longer, then we execute
        //addSub with what is left and the factor / divisor we have to make

        //problem lies in the fact that 1 is a factor of everything
        //factorials will also probably be dealt with in here as well

        HashSet<Integer> factorList = new HashSet<>();
        for(int i=1;i<14;i++){
            if(24 % i == 0){
                factorList.add(i);
            }
        }
        doMathHelper(inputNumArr, goal, factorList);
        return false;
    }
        
    private static String createFactorOrDivisor(int first, int second, int goal){
        //create goal using first and second via multiplcation or division
        String toReturn="";
        if(first > second && (first / second == goal) ){
            toReturn = "D";
            return toReturn;
        }
        else if(second > first && (second / first) == goal){
            toReturn = "DS";
            return toReturn;
        }
        else if(first * second == goal){
            toReturn = "M";
            return toReturn;
        }
        else{
            toReturn = "F";
            return toReturn;
        }
    }

    private static Boolean addSub(ArrayList<Integer> inputNumArr, int aim, int goal){
        //use the 1st index to hold the currVal, goal to hold what is given by solve, and aim to hold the number we are
        //currently aiming for. So if the 1st index is a 2, we are aiming for 2 and goal is 4
        //this falls through if given an arrayList with only 1 element
        if(inputNumArr.size()>2){
            if(inputNumArr.get(0) > goal){
                //in here, the 1st index should be getting bigger and aim should be getting smaller
                //Math.abs(goal - (Math.abs(inputNumArr[0]+inputNumArr[i]))) 
                //represents the distance between goal and the 1st index minus the ith value in inputNumArr
                int distanceFromAim = Math.abs(goal - (Math.abs(inputNumArr.get(0)+inputNumArr.get(1))));
                int smallNumIndex = 1;
                for(int i =2; i<inputNumArr.size(); i++){
                    //we always want the smallest possible distance from the goal
                    if( distanceFromAim > ( Math.abs( goal - ( Math.abs(inputNumArr.get(0)-inputNumArr.get(i)) ) ) ) ){
                        smallNumIndex = i;
                        distanceFromAim = ( Math.abs( goal - ( Math.abs(inputNumArr.get(0)-inputNumArr.get(i)) ) ) );
                    }
                }
                //when here, we've found the index of the value that when added, brings us closest to goal
                //update inputNumArr[0] to inputNumArr[0] - inputNumArr[smallNumIndex]
                inputNumArr.set(0,Math.abs(inputNumArr.get(0) - inputNumArr.get(smallNumIndex)));
                //subtract from aim the distance we gain from adding inputNumArr.get(smallIndex)
                aim=Math.abs(aim-inputNumArr.get(0));
                //remove the value at smallNumIndex in order to signify that we've used the number
                inputNumArr.remove(smallNumIndex);
            }

            else if(inputNumArr.get(0) < goal){
                //in here, the 1st index should be getting bigger and aim should be getting smaller
                //Math.abs(goal - (Math.abs(inputNumArr[0]+inputNumArr[i]))) 
                //represents the distance between goal and the 1st index plus the ith value in inputNumArr
                int distanceFromAim = Math.abs(goal - (Math.abs(inputNumArr.get(0)+inputNumArr.get(1))));
                int smallNumIndex = 1;
                for(int i =2; i<inputNumArr.size(); i++){
                    //we always want the smallest possible distance from the goal
                    if( distanceFromAim > ( Math.abs( goal - ( Math.abs(inputNumArr.get(0)+inputNumArr.get(i)) ) ) ) ){
                        smallNumIndex = i;
                        distanceFromAim = ( Math.abs( goal - ( Math.abs(inputNumArr.get(0)+inputNumArr.get(i)) ) ) );
                    }
                }
                //when here, we've found the index of the value that when added, brings us closest to goal
                //update inputNumArr[0] to inputNumArr[0] + inputNumArr[smallNumIndex]
                inputNumArr.set(0,inputNumArr.get(0) + inputNumArr.get(smallNumIndex));
                //subtract from aim the distance we gain from adding inputNumArr.get(smallIndex)
                aim=Math.abs(aim-inputNumArr.get(0));
                //remove the value at smallNumIndex in order to signify that we've used the number
                inputNumArr.remove(smallNumIndex);
            }
        }
        //since we will always have atleast 2 elements in the array
        int behindSub = Math.abs(inputNumArr.get(inputNumArr.size()-1) - inputNumArr.get(inputNumArr.size()-2));
        int behindAdd = inputNumArr.get(inputNumArr.size()-1) + inputNumArr.get(inputNumArr.size()-2);
        //the point here is to differentiat when we need to use x ± ( y ± z ) and x + y since there are 2 cases:
        //either we are here with 3 elements or with 2 elements
        //if we have 2 elements, we just check behindSub and behindAdd and see which may be goal
        //if we have 3, we need to find a way to add or subtract either behindSub or behindAdd
        if(inputNumArr.size()==2){
            //if here, we have 2 elements, most likely because an arrayList with 3 elements was given
            //we just need to check if either behindSub or behindAdd is the answer
            if(behindAdd == goal){
                return true;
            }
            else if(behindSub == goal){
                return true;
            }
        }
        else if(inputNumArr.size()==3){
            //if here, we have 3 elements, meaning we need to compute x ± ( y ± z )
            //aim is the remaining distance between what we currently have at inputNumArr[0] and goal
            if(aim == behindAdd){
                //if we are lower than the goal and behindAdd is the distance needed to be covered
                if(inputNumArr.get(0) > goal){
                    inputNumArr.set(0, (inputNumArr.get(0) - behindAdd) );
                }
                //if higher
                else{
                    inputNumArr.set(0, (inputNumArr.get(0) + behindAdd) );
                }
            }
            else if(aim == behindSub){
                //if we are lower than the goal and behindSub is the distance needed to be covered
                if(inputNumArr.get(0) > goal){
                    inputNumArr.set(0, (inputNumArr.get(0) - behindSub) );
                }
                //if higher
                else{
                    inputNumArr.set(0, (inputNumArr.get(0) + behindSub) );
                }
            }
        }
        return inputNumArr.get(0) == goal;
    }
    
    private static Boolean isValid(String check){
        //if there is a single non digit character, parseInt returns an exception
        try{
            check = check.replaceAll("\\s+","");
            int filter = Integer.parseInt(check);
            if(filter > 99999999){
                throw new Exception();
            }
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
            if(input.replaceAll("\\s+","").equals("q")){
                scan.close();
                System.exit(0);
            }
            //isValid takes the user input as a string, removes the white space, then parses the result into an int
            //7 is the smallest length allowed since [w x y z] is the smallest valid input
            else if(isValid(input) && input.length() >= 7){
                //create an array that consists only of non whitespace characters
                String[] inputArr = input.split("\\s+", 0);
                Integer[] inputArrNum = new Integer[inputArr.length];
                for(int i=0;i<inputArr.length;i++){
                    inputArrNum[i]=Integer.parseInt(inputArr[i]);
                }
                //if both tests fail, we declare no solution
                if(solve(inputArrNum,4) == true){
                    System.out.println("Solution Found");
                }
                else if(solve(inputArrNum,24) == true){
                    System.out.println("Solution Found");
                }
                else{
                    System.out.println("No Solution Found");
                }
            }
        }
    }
}
