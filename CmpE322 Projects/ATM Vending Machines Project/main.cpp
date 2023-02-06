#include <iostream>
#include <pthread.h>
#include <vector>
#include <fstream>
#include <numeric>
#include <cmath>
#include <sstream>
#include <unistd.h>
#include <queue>


void *customer(void *param);
void *ticket(void *param);

// Customer struct that keeps the customer values
struct Customer {
    int customerId;
    int sleepTime;
    int ticketInstance;
    std::string companyName;
    int amount;
};
// Machine struct that keeps the machine index
struct Machine {
    int index;
};

int customer_number; // total number of customers
int customer_counter = 0; // count the customers that is completed

std::queue<struct Customer> machine_queues[10]; // queues that keeps customers for every ticket vending machine

// Company accounts
int Kevin = 0;
int Bob = 0;
int Stuart = 0;
int Otto = 0;
int Dave = 0;

pthread_mutex_t machine_locks[10]; // mutexes for ticket vending machines

// mutexes for companies
pthread_mutex_t Kevin_lock;
pthread_mutex_t Bob_lock;
pthread_mutex_t Stuart_lock;
pthread_mutex_t Otto_lock;
pthread_mutex_t Dave_lock;

pthread_mutex_t write_lock; // mutex for writing to the file
pthread_mutex_t counter_lock; // mutex for incrementing customer_counter

Machine machine_structs[10]; // machine struct for every ticket vending machine

std::ofstream output; // output file


// Takes input from the input file, writes output to the output file, creates threads and waits for them to join
int main(int argc, char* argv[])
{
    std::string output_file = argv[1];
    std::string first_line;
    std::ifstream file(argv[1]);
    output.open(output_file.substr(0, output_file.find('.')) + "_log.txt");

    // reads input file
    std::getline(file, first_line);
    customer_number = atoi(first_line.c_str());
    std::string customer_lines[customer_number];
    for (int i = 0; i < customer_number; ++i)
    {
        std::getline(file, customer_lines[i]);
    }
    // creates customer structs
    Customer customer_structs[customer_number];

    // parses  the input lines and fills the customer structs
    for (int i = 0; i < customer_number; ++i)
    {
        std::stringstream test(customer_lines[i]);
        std::string segment;
        std::vector<std::string> seglist;
        while(std::getline(test, segment, ','))
        {
            seglist.push_back(segment);
        }
        customer_structs[i].customerId = i + 1;
        customer_structs[i].sleepTime = atoi(seglist[0].c_str());
        customer_structs[i].ticketInstance = atoi(seglist[1].c_str());
        customer_structs[i].companyName = seglist[2];
        customer_structs[i].amount = atoi(seglist[3].c_str());
    }

    for (int i = 0; i < 10; ++i)
    {
        machine_structs[i].index = i + 1;
    }

    pthread_t machines[10];
    pthread_t customers[customer_number];
    // creates customer and machine threads
    for (int i = 0; i < 10; ++i)
    {
        pthread_create(&machines[i], NULL, ticket, &machine_structs[i]);
    }
    for (int i = 0; i < customer_number; ++i)
    {
        pthread_create(&customers[i], NULL, customer, &customer_structs[i]);
    }

    // waits for threads to join
    for (int i = 0; i < customer_number; ++i)
    {
        pthread_join(customers[i], NULL);
    }
    for (int i = 0; i < 10; ++i)
    {
        pthread_join(machines[i], NULL);
    }

    // writes last part of the output
    pthread_mutex_lock(&write_lock);
    output << "[Main]: All payments are completed" << std::endl;
    output << "[Main]: Kevin: " << Kevin << std::endl;
    output << "[Main]: Bob: " << Bob << std::endl;
    output << "[Main]: Stuart: " << Stuart << std::endl;
    output << "[Main]: Otto: " << Otto << std::endl;
    output << "[Main]: Dave: " << Dave << std::endl;
    pthread_mutex_unlock(&write_lock);

    return 0;
}

// Runner function for customer threads
// firstly thread sleeps for a given time then, send information to the machine threads
void *customer(void *param)
{
    auto *customer = (struct Customer *) param;
    usleep(customer->sleepTime * 1000);
    pthread_mutex_lock(&machine_locks[customer->ticketInstance - 1]);
    machine_queues[customer->ticketInstance - 1].push(*customer);
    pthread_mutex_unlock(&machine_locks[customer->ticketInstance - 1]);
    pthread_exit(0);
}

// Runner function for ticket vending machine threads
void *ticket(void *param)
{
    auto *machine = (struct Machine *) param;
    while (customer_number != customer_counter) { // while checks the equality of counter and the number of customers if they are equal end of the while loop.
        while (!machine_queues[machine->index - 1].empty()) { // while checks queue of ticket vending machine is empty or not
            pthread_mutex_lock(&machine_locks[machine->index - 1]); // locks machine
            struct Customer customer = machine_queues[machine->index - 1].front();

            // program decide which company takes money and then locks it
            if (customer.companyName == "Kevin") {
                pthread_mutex_lock(&Kevin_lock);
                Kevin += customer.amount; // add to the company account
                pthread_mutex_unlock(&Kevin_lock);
            }
            else if (customer.companyName == "Bob") {
                pthread_mutex_lock(&Bob_lock);
                Bob += customer.amount; // add to the company account
                pthread_mutex_unlock(&Bob_lock);
            }
            else if (customer.companyName == "Stuart") {
                pthread_mutex_lock(&Stuart_lock);
                Stuart += customer.amount; // add to the company account
                pthread_mutex_unlock(&Stuart_lock);
            }
            else if (customer.companyName == "Otto") {
                pthread_mutex_lock(&Otto_lock);
                Otto += customer.amount; // add to the company account
                pthread_mutex_unlock(&Otto_lock);
            }
            else {
                pthread_mutex_lock(&Dave_lock);
                Dave += customer.amount; // add to the company account
                pthread_mutex_unlock(&Dave_lock);
            }

            // writing to the output file operation
            pthread_mutex_lock(&write_lock);
            output << "[VTM" << machine->index << "]: Customer" << customer.customerId << "," << customer.amount << "TL," << customer.companyName << std::endl;
            pthread_mutex_unlock(&write_lock);

            // incrementing customer_counter
            pthread_mutex_lock(&counter_lock);
            customer_counter ++;
            pthread_mutex_unlock(&counter_lock);

            // remove the completed operation from queue
            machine_queues[machine->index -1].pop();

            pthread_mutex_unlock(&machine_locks[machine->index - 1]); // unlocks machine
        }
    }
    pthread_exit(0);
}

