
using System; // we import our namespaces/libraries with USING statement

namespace HelloC_; // file cabinet, we store our stuff in it (our code)


// our main class, this is our program entry point
class Program
{   

    //This method is an entry point of execution
    static void Main(string[] args)
    {


        //Built-in data types in C#

        // integer/numeric
        int age = 45;
        long veryLargeNumber = 9223372036854775807; // larger than int, but less than double
        short myTinyNumber = -10;
        byte myByte = 255; 

        // floating/numeric
        double amountofMoney = 45.90; // general use, and more accurate
        float anotherFloating = 50.50f; // less precision, less memory usage/

        //characters and booleans
        char myChar = 'G';
        bool isTrue = true;

        // Strings 
        string username = "Alex";


        Console.WriteLine("Hello: " + username);
        Console.WriteLine($"I am {age} years old"); // string interpolation

        // Arithmetic

        int a = 5 + 9;
        int b = 5 - 10;
        int c = 10 * 5;
        int d = 10 / 5;
        int e = 10 % 5;


        //Comparison  and logical

        //a < b || a == b // OR operator
        //a > b && a == b  // AND operator
        //!a > b  // NOT operator
        // a<b ^  b==a // XOR operator

        //Assignment  operators

        int x = 10;
        x += 5; // x = x + 5
        x -= 5; // x = x - 5
        x *= 5; // x = x * 5
        x /= 5; // x = x / 5
        x %= 5; // x = x % 5


        //Get user Input


        Console.WriteLine("Enter your name: \n");
        string user = Console.ReadLine(); // read user input
        Console.WriteLine("You entered: " + user);



        //Conditional statements
        bool isPassword = true;
        if(isPassword){
            Console.WriteLine("Access granted");
        }
        else{
            Console.WriteLine("Access denied");
        }


        // Switch

        int day = 5;

        switch(day){
            case 1:
                Console.WriteLine("Monday");
                break;
            case 2:
                Console.WriteLine("Tuesday");
                break;
            case 3:
                Console.WriteLine("Wednesday");
                break;
            case 4:
                Console.WriteLine("Thursday");
                break;
            case 5:
                Console.WriteLine("Friday");
                break;
            case 6:
                Console.WriteLine("Saturday");
                break;
            case 7:
                Console.WriteLine("Sunday");
                break;
            default:
                Console.WriteLine("Invalid day");
                break;
        }


        // Loops

        //Do while loop

        int i = 4;
        do{
            Console.WriteLine("Number: " + i);
            i++;
        }while(i < 5);


        // while 
        int j = 10;
        while(j < 12){
            Console.WriteLine("Number: " + j);
            j++;
        }

        // for loop

        for(int y = 0; y < 10; y++){
            Console.WriteLine("Number: " + y);
        }



        // Type Conversion

        // Implicit conversion

        int myIntValue = 50;
        double myDoubleValue = myIntValue; // was converted to double, no data loss


        // Explicit conversion

        double doubleValue = 50.56;
        int intValue = (int)doubleValue; // was converted to int, data loss may occur
    }
}
