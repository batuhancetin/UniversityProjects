#include <iostream>
#include <vector>
#include <cmath>
#include <fstream>
#include <numeric>
#include <chrono>
#include <algorithm>
#include <iomanip>

int range(std::vector<int> numbers);
int mode(std::vector<int> numbers);
double harmonicMean(std::vector<int> numbers);
double arithmeticMean(std::vector<int> numbers);
double standartDeviation(std::vector<int> numbers);
double median(std::vector<int> numbers);
double interquartileRange(std::vector<int> numbers);


int main(int argc, char** argv)
{
    std::vector<int> numbers;
    int size = atoi(argv[1]);
    srand(time(0));
    for (int i = 0; i < size; ++i) {
        numbers.push_back(rand()%(9001) + 1000);
    }
    std::sort(numbers.begin(), numbers.end());
    std::ofstream myfile;
    myfile.open ("output1.txt");
    auto start = std::chrono::high_resolution_clock::now();
    myfile << numbers.front() << "\n";
    myfile << numbers.back() << "\n";
    myfile << range(numbers) << "\n";
    myfile << mode(numbers) << "\n";
    myfile << std::fixed << std::setprecision(5) << median(numbers) << "\n";
    myfile << std::accumulate(numbers.begin(), numbers.end(), 0) << "\n";
    myfile << std::fixed << std::setprecision(5) << arithmeticMean(numbers) << "\n";
    myfile << std::fixed << std::setprecision(5) << harmonicMean(numbers) << "\n";
    myfile << std::fixed << std::setprecision(5) << standartDeviation(numbers) << "\n";
    myfile << std::fixed << std::setprecision(5) << interquartileRange(numbers) << "\n";
    auto stop = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
    myfile << duration.count() * 0.000001 << std::endl;
    myfile.close();
    return 0;
}

int range(std::vector<int> numbers) {
    return numbers.back() - numbers.front();
}

int mode(std::vector<int> numbers) {
    int currentCount = 1;
    int maxCount = 1;
    int currentNumber = numbers[0];
    int mode = numbers[0];
    for (int i = 1; i < numbers.size(); i++) {
        if (i == numbers.size() - 1) {
            ++currentCount;
            if (currentCount > maxCount) {
                mode = currentNumber;
            }
        }
        if (numbers[i] == currentNumber) {
            ++currentCount;
        }
        else {
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mode = currentNumber;
            }
            currentCount = 1;
            currentNumber = numbers[i];
        }
    }
    return mode;
}

double harmonicMean(std::vector<int> numbers) {
    double sum = 0;
    for (int number : numbers) {
        sum = sum + (double)1 /number;
    }
    return (double) numbers.size() / sum;
}

double arithmeticMean(std::vector<int> numbers) {
    double sum = 0;
    for (int number : numbers) {
        sum = sum + number;
    }
    return (double) sum / numbers.size();
}

double standartDeviation(std::vector<int> numbers) {
    double standartDeviation = 0;
    double mean = arithmeticMean(numbers);
    for (int number : numbers) {
        standartDeviation += pow(number - mean, 2);
    }
    return sqrt((double)standartDeviation / (numbers.size() - 1));
}

double median(std::vector<int> numbers) {
    if (numbers.size() % 2 == 0) {
        return (double)(numbers[numbers.size() / 2] + numbers[numbers.size() / 2 - 1]) / 2;
    }
    else {
        return numbers[numbers.size() / 2];
    }
}

double interquartileRange(std::vector<int> numbers) {
    std::vector<int> firstHalf;
    for (int i = 0; i < numbers.size() / 2; ++i) {
        firstHalf.push_back(numbers[i]);
    }
    std::vector<int> secondHalf;
    for (int i = (int)ceil((double)numbers.size() / 2); i < numbers.size(); ++i) {
        secondHalf.push_back(numbers[i]);
    }
    return median(secondHalf) - median(firstHalf);
}