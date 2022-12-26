opening = open("crime_scene.txt", "r")
line_list = opening.read().split("\n")
total_weight = int(line_list[0].split()[0])
total_time = int(line_list[0].split()[1])
evidences = []
id_list = []
weight_list = []
time_list = []
value_list = []
first_probability_list = []
def evidence_list(i):
    if i == len(line_list):
        return
    if line_list[i] != "":
        evidences.append(line_list[i])
    evidence_list(i + 1)
def creating_list(i):
    if i == len(evidences):
        return
    id_list.append(int(evidences[i].split()[0]))
    weight_list.append(int(evidences[i].split()[1]))
    time_list.append(int(evidences[i].split()[2]))
    value_list.append(int(evidences[i].split()[3]))
    creating_list(i + 1)
evidence_list(2)
creating_list(0)
def firstsolution(weightlimit, i):
    if i == len(weight_list):
        return 0, []
    if weightlimit - weight_list[i] >= 0:
        total_value, ids = firstsolution(weightlimit - weight_list[i], i + 1)
        total_value += value_list[i]
        ids.append(id_list[i])
    else:
        total_value, ids = 0, []
    not_total_value, not_ids = firstsolution(weightlimit, i + 1)
    if total_value > not_total_value:
        return total_value, ids
    else:
        return not_total_value, not_ids
def secondsolution(timelimit, i):
    if i == len(time_list):
        return 0, []
    if timelimit - time_list[i] >= 0:
        total_value, ids = secondsolution(timelimit - time_list[i], i + 1)
        total_value += value_list[i]
        ids.append(id_list[i])
    else:
        total_value, ids = 0, []
    not_total_value, not_ids = secondsolution(timelimit, i + 1)
    if total_value > not_total_value:
        return total_value, ids
    else:
        return not_total_value, not_ids
def thirdsolution(weightlimit, timelimit, i):
    if i == len(time_list):
        return 0, []
    if timelimit - time_list[i] >= 0 and weightlimit - weight_list[i] >= 0:
        total_value, ids = thirdsolution(weightlimit - weight_list[i], timelimit - time_list[i], i + 1)
        total_value += value_list[i]
        ids.append(id_list[i])
    else:
        total_value, ids = 0, []
    not_total_value, not_ids = thirdsolution(weightlimit, timelimit, i + 1)
    if total_value > not_total_value:
        return total_value, ids
    else:
        return not_total_value, not_ids
def sorting_function(liste):
    for a in range(len(liste) - 1):
        for b in range(0, len(liste) - a - 1):
            if int(liste[b]) > int(liste[b + 1]):
                liste[b], liste[b + 1] = liste[b + 1], liste[b]
    return liste
def writing_function():
    s1 = str(firstsolution(total_weight, 0)[0]) + "\n"
    for i in sorting_function(firstsolution(total_weight, 0)[1]):
        s1 += str(i) + " "
    firstopen = open("solution_part1.txt", "w")
    firstopen.write(s1)
    firstopen.close()
    s2 = str(secondsolution(total_time, 0)[0]) + "\n"
    for i in sorting_function(secondsolution(total_time, 0)[1]):
        s2 += str(i) + " "
    secondopen = open("solution_part2.txt", "w")
    secondopen.write(s2)
    secondopen.close()
    s3 = str(thirdsolution(total_weight, total_time, 0)[0]) + "\n"
    for i in sorting_function(thirdsolution(total_weight, total_time, 0)[1]):
        s3 += str(i) + " "
    thirdopen = open("solution_part3.txt", "w")
    thirdopen.write(s3)
    thirdopen.close()
writing_function()
opening.close()
