#include <iostream>
#include <unistd.h>
#include <cstdlib>
#include <sstream>
#include <fstream>
#include <vector>
#include <deque>
#include <sys/wait.h>

void listdir(pid_t pid);        // declaration of listdir method
void mycomputername(pid_t pid);     // declaration of mycomputername method
void whatsmyip(pid_t pid);      // declaration of whatsmyip method
void printtoanotherfile(pid_t pid, std::vector<std::string> tokens);        // declaration of printtoanotherfile method
void printfile(std::vector<std::string> tokens);        // declaration of printfile method
void dididothat(std::deque<std::string> history, std::string my_str);       // declaration of dididothat method
void textedit(pid_t pid);       // declaration of textedit method
std::vector<std::string> split(std::string str, char delim);        // declaration of split method

int main(int argc, char const* argv[])
{
    std::string my_str;     // my_str is the string that will be entered as an input
    pid_t pid;      // pid is the process id
    std::string my_user = getenv("USER");       // getenv("USER") method returns user's name
    std::deque<std::string> history;        // history deque holds last 15 commands that is entered.
    int exit = 0;
    while(exit == 0) {
        std::cout << my_user << " >>> ";        // prints user name and >>> in front of every line
        getline(std::cin, my_str);      // takes line input from user and save it to my_str
        if (my_str.empty()) {       // if user enters empty string program does not anything.
            continue;
        }
        std::vector<std::string> tokens = split(my_str, ' ');       // splits input line from spaces and stores in a vector
        if (tokens[0] == "listdir") {       // if user enters listdir program calls listdir(pid) method
            listdir(pid);
        }
        else if (tokens[0] == "exit") {     // if user enters exit, program assign 1 to exit and while loop terminates.
            exit = 1;
        }
        else if (tokens[0] == "mycomputername") {       // if user enters mycomputername, program calls mycomputername(pid) method
            mycomputername(pid);
        }
        else if (tokens[0] == "whatsmyip") {        // if user enters whatsmyip, program calls whatsmyip(pid) method
            whatsmyip(pid);
        }
        else if (tokens[0] == "printfile" && tokens.size() > 2) {       // if user enters printfile and two files, program calls printtoanotherfile(pid, tokens) method
            printtoanotherfile(pid, tokens);
        }
        else if (tokens[0] == "dididothat") {       // if user enters dididothat, program calls dididothat(history, my_str) method
            dididothat(history, my_str);
        }
        else if (tokens[0] == "printfile" && tokens.size() == 2) {      // if user enters printfile and one file, program calls printfile(tokens) method
            printfile(tokens);
        }
        else if(tokens[0] == "hellotext") {     // if user enters hellotext, program calls textedit(pid) method
            textedit(pid);
        }
        if (tokens[0] != "dididothat") {
            history.push_front(my_str);     // pushes entered command to in front of history deque
        }
        if (history.size() == 16) {     // if size of history deque exceeds 15 this if block pops the last element of the history deque
            history.pop_back();
        }
    }
    return 0;
}

void listdir(pid_t pid)     // definition of the listdir(pid_t pid) method. It lists the current directory
{
    pid = fork();       // creates a new child process and assigns its id to pid variable
    if (pid < 0) {      // if pid is below zero, there occurs some error in fork() process
        perror("Error");
    }
    else if (pid == 0) {        // if pid is zero it means that it is child process and ready to execute command
        execlp("ls", "ls", NULL);       // execlp command takes "ls" input and arguments, executes ls command
    }
    else {      // if pid is above zero it means that it is parent process and it waits child process
        wait(NULL);
    }
}

void mycomputername(pid_t pid)      // definition of the mycomputername(pid_t pid) method. It prints computer name.
{
    pid = fork();       // creates a new child process and assigns its id to pid variable
    if (pid < 0) {      // if pid is below zero, there occurs some error in fork() process
        perror("Error");
    }
    else if (pid == 0) {        // if pid is zero it means that it is child process and ready to execute command
        execlp("hostname", "hostname", NULL);       // execlp command takes "hostname" input and arguments, executes hostname command
    }
    else {      // if pid is above zero it means that it is parent process and it waits child process
        wait(NULL);
    }
}

void whatsmyip(pid_t pid)       // definition of the whatsmyip(pid_t pid) method. It prints ip.
{
    pid = fork();       // creates a new child process and assigns its id to pid variable
    if (pid < 0) {      // if pid is below zero, there occurs some error in fork() process
        perror("Error");
    }
    else if (pid == 0) {        // if pid is zero it means that it is child process and ready to execute command
        execlp("hostname", "hostname", "-I", NULL);     // execlp command takes "hostname" input and -I argument, executes hostname -I command
    }
    else {      // if pid is above zero it means that it is parent process and it waits child process
        wait(NULL);
    }
}

void printtoanotherfile(pid_t pid, std::vector<std::string> tokens)     // definition of the printtoanotherfile(pid_t pid, std::vector<std::string> tokens) method. It prints one file to another file.
{
    pid = fork();       // creates a new child process and assigns its id to pid variable
    if (pid < 0) {      // if pid is below zero, there occurs some error in fork() process
        perror("Error");
    }
    else if (pid == 0) {        // if pid is zero it means that it is child process and ready to execute command
        execlp("cp", "cp", tokens[1].c_str(), tokens[3].c_str(), NULL);     // execlp commmand takes "cp" input and arguments, executes the command
    }
    else {      // if pid is above zero it means that it is parent process and it waits child process
        wait(NULL);
    }
}

void dididothat(std::deque<std::string> history, std::string my_str) {      // definition of the dididothat(std::deque<std::string> history, std::string my_str) method. It checks history.
    my_str = my_str.substr(my_str.find_first_of(" \t")+1);      // it remove first word that is dididothat of the input and assigns it to my_str
    if (!history.empty()) {     // it checks history is empty or not if it is empty it does nothing.
        for (int i = 0; i < history.size(); ++i)        // it checks every command in history
        {
            if (history[i] == my_str) {     // this if else blocks checks both dididothat command and dididothat "command" equal to our input.
                std::cout << "Yes" << std::endl;
                return;
            } else {        // if c
                std::string copy_str = history[i];
                copy_str.insert(0, 1, '"'); // insert " in front of our input
                copy_str.push_back('"'); // insert " end of our input
                if (copy_str == my_str) {
                    std::cout << "Yes" << std::endl;
                    return;
                }
            }
        }
        std::cout << "No" << std::endl;     // if input is not in history it prints no
    } else std::cout << "No" << std::endl;      // if history is empty it prints No
}

void printfile(std::vector<std::string> tokens) {       // definition of the printfile(std::vector<std::string> tokens) method. It prints file line by line.
    std::ifstream infile(tokens[1]);        // tokens[1] is the name of the file that is read. It reads file to a ifstream
    std::string line;
    while(getline(infile, line)) {      // this while loop reads file line by line and assigns line to line string
        std::cout << line;      // prints the line
        if (std::cin.get() == '\n') {       // takes input and if user enters enter it passes to next iteration of the while loop
            continue;
        }
    }
}

void textedit(pid_t pid) {      // definition of the textedit(pid_t pid) method. It opens default text editor.
    pid = fork();       // creates a new child process and assigns its id to pid variable
    if (pid < 0) {      // if pid is below zero, there occurs some error in fork() process
        perror("Error");
    }
    else if (pid == 0) {        // if pid is zero, it means child is created successfully and ready to execute a command
        execlp("gedit", "gedit", NULL);     // execlp method takes "gedit" input to open gedit
    }
    else {
        wait(NULL);     // if pid is above zero, it means it is parent process and it waits child process
    }
}

std::vector<std::string> split(std::string str, char delim) {       // definition of the split(std::string str, char delim) method. It splits line string from spaces to a vector.
    std::vector<std::string> tokens;        // The vector that holds tokens
    std::string token;      // One of the words that is splitted
    std::stringstream ss(str);      // stringstream of line string
    while (getline(ss,token,delim)) {       // it reads line and splits to tokens.
        tokens.push_back(token);        // append tokens to vector.
    }
    return tokens;      // return tokens vector.
}


