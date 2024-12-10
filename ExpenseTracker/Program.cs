namespace ExpenseTracker;

class Program
{
    static void Main(string[] args)
    {
        Greeting();
        DisplayOptions();
        int selectedOption = getUserOption();

        switch(selectedOption){

            case 1:
                Console.WriteLine
        }
    }

    public static void Greeting(){
        Console.WriteLine("Welcome to the ExpenseTracker!");
        Thread.Sleep(1500);
        Console.WriteLine("Select from one of the following options: \n");
        Thread.Sleep(1500);
        DisplayOptions();

    }

    public static void DisplayOptions(){
        Console.WriteLine("1. Add Expense");
        Console.WriteLine("2. View Expenses");
        Console.Writeline("3. Edit Expenses");
        Console.Writeline("4. Delete Expenses");
        Console.Writeline("5. Save to a file");
        Console.Writeline("6. Exit");


    }

    public int getUserOption(){
        Console.WriteLine("Select the option: \n");
        string? userInput = Console.ReadLine();

        try{
            return Int32.Parse(userInput);
        }
        catch(FormatException e){
            Console.WriteLine("Invalid input! Make sure you typed a number!");
            return getUserOption();
        }
    }
}
