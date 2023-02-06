#include <iostream>
#include <pthread.h>
#include <vector>
#include <fstream>
#include <numeric>
#include <cmath>
#include <chrono>
#include <algorithm>
#include <iomanip>

void *min_max(void *param);
void *mode_median(void *param);
void *means(void *param);
void *standartDeviation_sum(void *param);
void *interquartileRange_range(void *param);

struct min_max_args {
    std::vector<int> numbers;
    int min;
    int max;
};
struct range_inter_args {
    std::vector<int> numbers;
    int range;
    double interquartileRange;
};
struct mode_median_args {
    std::vector<int> numbers;
    int mode;
    double median;
};
struct means_args {
    std::vector<int> numbers;
    double arithmeticMean;
    double harmonicMean;
};
struct standartDeviation_sum_args {
    std::vector<int> numbers;
    double standartDeviation;
    int sum;
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
    struct min_max_args min_max_args;
    min_max_args.numbers = numbers;
    struct range_inter_args range_inter_args;
    range_inter_args.numbers = numbers;
    struct mode_median_args mode_median_args;
    mode_median_args.numbers = numbers;
    struct means_args means_args;
    means_args.numbers = numbers;
    struct standartDeviation_sum_args standartDeviation_sum_args;
    standartDeviation_sum_args.numbers = numbers;
    std::ofstream myfile;
    myfile.open("output3.txt");
    auto start = std::chrono::high_resolution_clock::now();
    pthread_t threads[5];
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_create(&threads[0], &attr, min_max, &min_max_args);
    pthread_create(&threads[1], &attr, interquartileRange_range, &range_inter_args);
    pthread_create(&threads[2], &attr, mode_median, &mode_median_args);
    pthread_create(&threads[3], &attr, means, &means_args);
    pthread_create(&threads[4], &attr, standartDeviation_sum, &standartDeviation_sum_args);

    for (int i = 0; i < 5; ++i)
    {
        pthread_join(threads[i], NULL);
    }

    auto stop = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
    myfile << min_max_args.min << "\n";
    myfile << min_max_args.max << "\n";
    myfile << range_inter_args.range << "\n";
    myfile << mode_median_args.mode << "\n";
    myfile << std::fixed << std::setprecision(5) << mode_median_args.median << "\n";
    myfile << standartDeviation_sum_args.sum << "\n";
    myfile << std::fixed << std::setprecision(5) << means_args.arithmeticMean << "\n";
    myfile << std::fixed << std::setprecision(5) << means_args.harmonicMean << "\n";
    myfile << std::fixed << std::setprecision(5) << standartDeviation_sum_args.standartDeviation << "\n";
    myfile << std::fixed << std::setprecision(5) << range_inter_args.interquartileRange << "\n";
    myfile << duration.count() * 0.000001 << std::endl;
    myfile.close();
    return 0;
}

void *min_max(void *param) {
    auto *args = (struct min_max_args *) param;
    args->min = args->numbers.front();

    args->max = args->numbers.back();
    pthread_exit(0);
}


void *interquartileRange_range(void *param) {
    auto *args = (struct range_inter_args *) param;
    args->range = args->numbers.back() - args->numbers.front();

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
    pthread_exit(0);
}

void *mode_median(void *param) {
    auto *args = (struct mode_median_args *) param;
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

    if (args->numbers.size() % 2 == 0) {
        args->median = (double)(args->numbers[args->numbers.size() / 2] + args->numbers[args->numbers.size() / 2 - 1]) / 2;
    }
    else {
        args->median = args->numbers[args->numbers.size() / 2];
    }
    pthread_exit(0);
}



void *means(void *param) {
    auto *args = (struct means_args *) param;
    double sum = 0;
    for (int number : args->numbers) {
        sum = sum + number;
    }
    args->arithmeticMean = (double) sum/args->numbers.size();

    double sum2 = 0;
    for (int number : args->numbers) {
        sum2 = sum2 + (double)1 /number;
    }
    args->harmonicMean = (double) args->numbers.size() / sum2;
    pthread_exit(0);
}


void *standartDeviation_sum(void *param) {
    auto *args = (struct standartDeviation_sum_args *) param;
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

    args->sum = std::accumulate(args->numbers.begin(), args->numbers.end(), 0);
    pthread_exit(0);
}
