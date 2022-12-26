

def hw2():
    str_input = input()
    # DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
    str_input = str_input.split()
    if len(str_input) == 0:
        print("No words")
    elif len(str_input) == 1:
        print("One word")
        print(str_input[0])
    elif len(str_input) == 2:
        print("Two words")
        print(str_input[0], str_input[1])
    elif len(str_input) > 2:
        print(str(len(str_input)) + " words!")
        print(str_input[0], str_input[-1])
    # DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
    return


if __name__ == "__main__":
    hw2()
