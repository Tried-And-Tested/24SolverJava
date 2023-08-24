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
public class solver {
    private static Boolean solve(Integer[] userInputChoice, int goal){
        ArrayList<Integer> userInputChoiceList = new ArrayList<Integer>(Arrays.asList(userInputChoice));
        //ensure that no matter how we enter in the 4 numbers, all the methods will recieve a constant
        Collections.sort(userInputChoiceList);
        if(doMath(userInputChoiceList, goal)){
            return true;
        }

        else if(createFactorOrDivisor(userInputChoiceList, goal)){
            return true;
        }

        else if(addSub(userInputChoiceList, goal, goal)){
            return true;
        }
        return false;
    }

    
    private static Boolean doMath(ArrayList<Integer> inputNumArr, int goal){
        //The point of doMath is to search for factors or dividends. If a factor or dividend is found,
        //then we find its factor or dividend until we can no longer. If we can no longer, then we execute
        //addSub with what is left and the factor / divisor we have to make
        
        ArrayList<Integer> leftovers = new ArrayList<>();

        //do Multiplcation first
        //we want to do this inputNumArr.size() amount of times to ensure that no stone is left unturned
        for(Integer x : inputNumArr){
            ArrayList<Integer> factors = partFinder(inputNumArr, x, "M");
            for(Integer i : inputNumArr){
                if(!(factors.contains(i))){
                    leftovers.add(i);
                }
            }
            //at this point, all the numbers in leftovers can be multiplied by the product of 
            //the elements in factors to get goal
            int product = 1;
            for(Integer m : factors){
                product *= m;
            }
            if(product==goal || addSub(leftovers, (goal / product), (goal / product))){
                return true;
            }
        }
        leftovers.clear();
        //then division
        //we want to do this inputNumArr.size() amount of times to ensure that no stone is left unturned
        for(Integer x : inputNumArr){
            ArrayList<Integer> divi = partFinder(inputNumArr, goal, "D");
            for(Integer i : inputNumArr){
                if(!(divi.contains(i))){
                    leftovers.add(i);
                }
            }
            //at this point, all the numbers in leftovers can be divided by the quotient of 
            //the elements in factors to get goal
            if( (divi.size()==2) && (addSub(leftovers, (goal * (divi.get(0)/divi.get(1))), (goal * (divi.get(0) / divi.get(1) ) ) ) ) ){
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Integer> partFinder(ArrayList<Integer>inputNumArrList, int start, String type){
        //this is used to return an array list full of factors such that all that is left in the original
        //array list are numbers that can only be added or subtracted
        ArrayList<Integer> toReturn = new ArrayList<>();
        int search = start;
        while(toReturn.size() >=4){
            for(int i=0;i<inputNumArrList.size();i++){
                if(type.equals("M")){
                    if(i != 0 && search % i == 0){
                        toReturn.add(inputNumArrList.get(i));
                        search = inputNumArrList.get(i);
                    }
                }
                else if(type.equals("D")){
                    if(i !=0 && i % search == 0){
                        toReturn.add(inputNumArrList.get(i));
                        search = inputNumArrList.get(i);
                    }
                }
            }
        }
        return toReturn;
    }

    private static Boolean createFactorOrDivisor(ArrayList<Integer> inputNumArr, int goal){
        return false;
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
