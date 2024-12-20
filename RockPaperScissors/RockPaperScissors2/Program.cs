/* basic rock, paper, scissors, game */

namespace RockPaperScissors2;

class Program
{
    static void Main(string[] args)
    {
        string[] shapes = { "rock", "paper", "scissors" };

        Console.WriteLine("Today we are playing Rock, Paper, Scissors!");
        
        bool flag = true;

        while(flag){
            Console.WriteLine("Rock, paper, or scissors?");
            string userInput = Console.ReadLine();

            if (userInput != "rock" && userInput != "paper" && userInput != "scissors")
            {
                Console.WriteLine("You must pick either rock, paper, or scissors");
                continue;
            }

            Random myRandomNumber = new Random();
            string computerShape = shapes[myRandomNumber.Next(shapes.Length)];

            Console.WriteLine("I chose: " + computerShape); 

            string res = pickWinner(userInput, computerShape);

            Console.WriteLine(res);

            Console.WriteLine("Play again?");
            string playAgainInput = Console.ReadLine();

            if(playAgainInput != "yes"){
                flag = false;
                Console.WriteLine("See you later!");
            }

        }
    }

    static string pickWinner(string userInput, string computerShape){
        if (userInput == computerShape){
            Console.WriteLine("Tie!");
        }
        if(
            (userInput == "rock" && computerShape == "scissor") ||
            (userInput == "paper" && computerShape == "rock") ||
            (userInput == "scissor" && computerShape == "paper")
        ){
            return "Congrats, you won!";
        }
        
        return "You lost!";

    }
}
