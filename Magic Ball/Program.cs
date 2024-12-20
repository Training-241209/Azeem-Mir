namespace Magic_Ball;

class Program
{
    static void Main(string[] args)
    {
        Console.WriteLine("Welcome to the Magic Ball!");
        Console.WriteLine("Think about your question, and type something!");

        string? userInput = Console.ReadLine();

        if(!string.IsNullOrEmpty(userInput)){
            Console.WriteLine("The Magic Ball says:");
            Console.WriteLine(GetPrediction());

        } else{
            Console.WriteLine("Type Something!");
            Main(args);
        }
    }

    public static string GetPrediction(){

        string[] predictions = {"It is certain.",
    "It is decidedly so.",
    "Without a doubt.",
    "Yes – definitely.",
    "You may rely on it.",
    "As I see it, yes.",
    "Most likely.",
    "Outlook good.",
    "Yes.",
    "Signs point to yes.",
    "Reply hazy, try again.",
    "Ask again later.",
    "Better not tell you now.",
    "Cannot predict now.",
    "Concentrate and ask again.",
    "Don't count on it.",
    "My reply is no.",
    "My sources say no.",
    "Outlook not so good.",
    "Very doubtful."};

        Random myRandomNumber = new Random();
        int randomIndex = myRandomNumber.Next(0, predictions.Length);

        return predictions[randomIndex];

    }
}
