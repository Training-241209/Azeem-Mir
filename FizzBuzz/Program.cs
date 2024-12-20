// See https://aka.ms/new-console-template for more information
//Console.WriteLine("Hello, World!");

/* FizzBuzz:

Print "FizzBuzz" if divisible by 3 and 5, 
Print "Fizz" if i/3
Print "Buzz" if i/5
Print "i" if non of it 

access modifiers: 

public: the most open access modifier, all classes in the project can access it
private: most closed access modifier, only available to the class itself
internal: limited to a class and its member, but not static member

static keyword: don't need an object/instance to call method or access static member, and static
member value are shared

*/

//public static void FizzBuzz(int num){
    int num = 20;
    int counter = 1;
    while(counter <= num){
        if(counter % 3 == 0 && counter % 5 == 0){
            Console.WriteLine("FizzBuzz");
        }
        else if(counter % 3 == 0){
            Console.WriteLine("Fizz");
        }
        else if(counter % 5 == 0){
            Console.WriteLine("Buzz");
        }
        else{
            Console.WriteLine(counter);
        }
        counter++;
    } 

//}
