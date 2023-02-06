#include <iostream>
#include <pthread.h>
#include <vector>
#include <fstream>
#include <numeric>
#include <cmath>
#include <chrono>
#include <algorithm>
#include <iomanip>

void *min(void *param);
void *max(void *param);
void *range(void *param);
void *mode(void *param);
void *median(void *param);
void *sum(void *param);
void *arithmeticMean(void *param);
void *harmonicMean(void *param);
void *standartDeviation(void *param);
void *interquartileRange(void *param);

struct min_args {
    std::vector<int> numbers;
    int min;
};
struct max_args {
    std::vector<int> numbers;
    int max;
};
struct range_args {
    std::vector<int> numbers;
    int range;
};
struct mode_args {
    std::vector<int> numbers;
    int mode;
};
struct median_args {
    std::vector<int> numbers;
    double median;
};
struct sum_args {
    std::vector<int> numbers;
    int sum;
};
struct arithmeticMean_args {
    std::vector<int> numbers;
    double arithmeticMean;
};
struct harmonicMean_args {
    std::vector<int> numbers;
    double harmonicMean;
};
struct standartDeviation_args {
    std::vector<int> numbers;
    double standartDeviation;
};
struct interquartileRange_args {
    std::vector<int> numbers;
    double interquartileRange;
};


int main(int argc, char** argv)
{
    std::vector<int> numbers;
    int size = atoi(argv[1]);
    srand(time(0));
    for (int i = 0; i < size; ++i)
    {
        numbers.push_back(rand()%(9001) + 1000);
    }
    std::sort(numbers.begin(), numbers.end());
    struct min_args min_args;
    min_args.numbers = numbers;
    struct max_args max_args;
    max_args.numbers = numbers;
    struct range_args range_args;
    range_args.numbers = numbers;
    struct mode_args mode_args;
    mode_args.numbers = numbers;
    struct median_args median_args;
    median_args.numbers = numbers;
    struct sum_args sum_args;
    sum_args.numbers = numbers;
    struct arithmeticMean_args arithmeticMean_args;
    arithmeticMean_args.numbers = numbers;
    struct harmonicMean_args harmonicMean_args;
    harmonicMean_args.numbers = numbers;
    struct standartDeviation_args standartDeviation_args;
    standartDeviation_args.numbers = numbers;
    struct interquartileRange_args interquartileRange_args;
    interquartileRange_args.numbers = numbers;
    std::ofstream myfile;
    myfile.open("output2.txt");
    auto start = std::chrono::high_resolution_clock::now();
    pthread_t threads[10];
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_create(&threads[0], &attr, min, &min_args);
    pthread_create(&threads[1], &attr, max, &max_args);
    pthread_create(&threads[2], &attr, range, &range_args);
    pthread_create(&threads[3], &attr, mode, &mode_args);
    pthread_create(&threads[4], &attr, median, &median_args);
    pthread_create(&threads[5], &attr, sum, &sum_args);
    pthread_create(&threads[6], &attr, arithmeticMean, &arithmeticMean_args);
    pthread_create(&threads[7], &attr, harmonicMean, &harmonicMean_args);
    pthread_create(&threads[8], &attr, standartDeviation, &standartDeviation_args);
    pthread_create(&threads[9], &attr, interquartileRange, &interquartileRange_args);

    for (int i = 0; i < 10; ++i)
    {
        pthread_join(threads[i], NULL);
    }
    auto stop = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
    myfile << min_args.min << "\n";
    myfile << max_args.max << "\n";
    myfile << range_args.range << "\n";
    myfile << mode_args.mode << "\n";
    myfile << std::fixed << std::setprecision(5) << median_args.median << "\n";
    myfile << sum_args.sum << "\n";
    myfile << std::fixed << std::setprecision(5) << arithmeticMean_args.arithmeticMean << "\n";
    myfile << std::fixed << std::setprecision(5) << harmonicMean_args.harmonicMean << "\n";
    myfile << std::fixed << std::setprecision(5) << standartDeviation_args.standartDeviation << "\n";
    myfile << std::fixed << std::setprecision(5) << interquartileRange_args.interquartileRange << "\n";
    myfile << duration.count() * 0.000001 << std::endl;
    myfile.close();
    return 0;
}

void *min(void *param) {
    auto *args = (struct min_args *) param;
    args->min = args->numbers.front();
    pthread_exit(0);
}

void *max(void *param) {
    auto *args = (struct max_args *) param;
    args->max = args->numbers.back();
    pthread_exit(0);
}

void *range(void *param) {
    auto *args = (struct range_args *) param;
    args->range = args->numbers.back() - args->numbers.front();
    pthread_exit(0);
}

void *mode(void *param) {
    auto *args = (struct mode_args *) param;
    int currentCount = 1;
    int maxCount = 1;
    int currentNumber = args->numbers[0];
    int mode = args->numbers[0];
    for (int i = 1; i < args->numbers.size(); i++) {
        if (i == args->numbers.size() - 1) {
            ++currentCount;
            if (currentCount > maxCount) {
                mode = currentNumber;
            }
        }
        if (args->numbers[i] == currentNumber) {
            ++currentCount;
        }
        else {
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mode = currentNumber;
            }
            currentCount = 1;
            currentNumber = args->numbers[i];
        }
    }
    args->mode = mode;
}

void *median(void *param) {
    auto *args = (struct median_args *) param;
    if (args->numbers.size() % 2 == 0) {
        args->median = (double)(args->numbers[args->numbers.size() / 2] + args->numbers[args->numbers.size() / 2 - 1]) / 2;
    }
    else {
        args->median = args->numbers[args->numbers.size() / 2];
    }
}

void *sum(void *param) {
    auto *args = (struct sum_args *) param;
    args->sum = std::accumulate(args->numbers.begin(), args->numbers.end(), 0);
}

void *arithmeticMean(void *param) {
    auto *args = (struct arithmeticMean_args *) param;
    double sum = 0;
    for (int number : args->numbers) {
        sum = sum + number;
    }
    args->arithmeticMean = (double) sum/args->numbers.size();
}

void *harmonicMean(void *param) {
    auto *args = (struct harmonicMean_args *) param;
    double sum = 0;
    for (int number : args->numbers) {
        sum = sum + (double)1 /number;
    }
    args->harmonicMean = (double) args->numbers.size() / sum;
}

void *standartDeviation(void *param) {
    auto *args = (struct standartDeviation_args *) param;
    double standartDeviation = 0;
    double sum = 0;
    for (int number : args->numbers) {
        sum = sum + number;
    }
    double mean = (double) sum/args->numbers.size();
    for (int number : args->numbers) {
        standartDeviation += pow(number - mean, 2);
    }
    args->standartDeviation = sqrt((double)standartDeviation / (args->numbers.size() - 1));
}

void *interquartileRange(void *param) {
    auto *args = (struct interquartileRange_args *) param;
    std::vector<int> firstHalf;
    for (int i = 0; i < args->numbers.size() / 2; ++i) {
        firstHalf.push_back(args->numbers[i]);
    }
    std::vector<int> secondHalf;
    for (int i = (int)ceil((double)args->numbers.size() / 2); i < args->numbers.size(); ++i) {
        secondHalf.push_back(args->numbers[i]);
    }
    double med_first;
    double med_second;
    if (firstHalf.size() % 2 == 0) {
        med_first = (double)(firstHalf[firstHalf.size() / 2] + firstHalf[firstHalf.size() / 2 - 1]) / 2;
    }
    else {
        med_first = firstHalf[firstHalf.size() / 2];
    }
    if (secondHalf.size() % 2 == 0) {
        med_second = (double)(secondHalf[secondHalf.size() / 2] + secondHalf[secondHalf.size() / 2 - 1]) / 2;
    }
    else {
        med_second = secondHalf[secondHalf.size() / 2];
    }
    args->interquartileRange = med_second - med_first;
}