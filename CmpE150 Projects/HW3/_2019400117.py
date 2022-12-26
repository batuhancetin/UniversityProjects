def hw3():
    cumulative_sum = 0
    n = int(input())
    x = int(input())
    # DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
    liste = []
    b = ""
    for i in range(x):
        i = input()
        liste.append(i)
    if n <= x:
        for c in liste[-1:-n - 1:-1]:
            b = str(c) + b
            cumulative_sum = cumulative_sum + int(b)
    else:
        if x == 0:
            cumulative_sum = 0
        else:
            for d in range(n // x):
                for e in liste[::-1]:
                    b = str(e) + b
                    cumulative_sum = cumulative_sum + int(b)
            for f in liste[-1:-(n % x) - 1:-1]:
                b = str(f) + b
                cumulative_sum = cumulative_sum + int(b)
    # DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
    print(cumulative_sum)
    return cumulative_sum


if __name__ == "__main__":
    hw3()

