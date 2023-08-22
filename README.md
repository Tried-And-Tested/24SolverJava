# 24SolverJava
24Solver but in Java instead of Kotlin

# Background
24 is a simple game where 4 cards are drawn and the player has to reach the number 24 using basic mathematical opertaions (+ ; - ; / ; * ). For added flavour, I will also be including factorial as one of the operations that can be used. The value of each card is the number of the card (not factoring suit value). For royals, the jack is assigned 11 and the king is assigned 13. The ace is assigned the number 1

# Rules
In the game 24, the player must reach the number 24 by using all 4 numbers given and any of the given mathematical opertaions. The player can NOT use a number if it has already been used once before (IE if the draw is [8, 6, 4, 4], the player can only use 8 once)

# Idea
My goal in this project is to write a program that can solve the game 24 with the added rules without simply brute forcing its way to the answer. To this end, I seeking to emulate how I, as a human, would play the game 24.

When I play the game 24, I tend to gravitate towards using 4! since it tends to be the easier approach. I achieve this through looking for factors or dividend of 4 and solving for the respective factor/divisor before adding and subtracting
#Methodology

The way I approached this problem is by first trying to arrive at 24 through achieving 4! since 4 can be found by dividing either 12 or 8, shares many factors with 24, and is generally an easier number to add and subtract up to.

To find 4, I first organize the 4 numbers in their natural order using a treeSet in order to take advantage of the guarantee that the first index always holds the smallest value. Once the set is sorted, I start searching for factors and dividends of 4. If a factor or dividend is found, then my code will start looking for factors or dividends of the factor/dividend found. If a factor or dividend is not found, then my code will try to create a factor through addition or subtraction

In the case that factors or dividends can not be found such as in the case we come across a prime number like 3 or 2, my code will then take the remaining numbers and try to create the other respective factor through addition and subtraction. if no factor can be made, my code then resorts to purely adding and subtracting

There is also the caveat where ending with a 4 and a 3 is a valid answer since 4 * 3! is 24

In the case my code can not reach 4, it starts searching for 24 using the same steps as above. If neither case works, then my code concludes that no solution can be found

# Limitations
There are several limitations to my code due to the method I go about solving the game, such as in the case the 4 drawn numbers are [6, 6, 6, 1]. In this case, my code doesnt see the answer 6 (6 - 1) - 6 because 5 is neither a valid factor of 4 or 24, which my code relies on.
